/* 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package lets.support.tx;

import java.util.function.Function;
import java.util.function.Supplier;
import lets.support.DataResultSupport;
import lets.support.InputOutputResultSupport;
import lets.support.ResultSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * The Class SpringTxManager.
 */
public class SpringTxManager implements TxManager {

  /** The transaction manager. */
  private PlatformTransactionManager transactionManager;

  /** The logger. */
  private Logger logger = LoggerFactory.getLogger(getClass());

  /**
   * Instantiates a new spring tx manager.
   *
   * @param transactionManager the transaction manager
   */
  public SpringTxManager(PlatformTransactionManager transactionManager) {
    super();
    this.transactionManager = transactionManager;
  }

  /**
   * Tx.
   *
   * @param callback the callback
   * @return the result support
   */
  @Override
  public ResultSupport tx(Runnable callback) {
    final ResultSupport result = new ResultSupport();
    try {
      TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
      transactionTemplate.execute(new TransactionCallbackWithoutResult() {

        /**
         * Do in transaction without result.
         *
         * @param status the status
         */
        @Override
        protected void doInTransactionWithoutResult(TransactionStatus status) {
          callback.run();
        }
      });
    } catch (Throwable e) {
      this.logger.error("Transaction Exception:{}", e.getMessage(), e);
      result.onException(e);
    }
    return result;

  }

  /**
   * Tx.
   *
   * @param <TInput>  the generic type
   * @param <TOutput> the generic type
   * @param input     the input
   * @param func      the func
   * @return the input output result support
   */
  public <TInput, TOutput> InputOutputResultSupport<TInput, TOutput> tx(TInput input,
      Function<TInput, TOutput> func) {
    InputOutputResultSupport<TInput, TOutput> result = new InputOutputResultSupport<TInput, TOutput>();
    try {
      TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
      result.setInput(input);
      result.setOutput(transactionTemplate.execute(status -> {
        return func.apply(input);
      }));
    } catch (Throwable e) {
      this.logger.error("Transaction Exception:{}", e.getMessage(), e);
      result.onException(e);
    }
    return result;
  }

  /**
   * Tx.
   *
   * @param <TOutput> the generic type
   * @param func      the func
   * @return the data result support
   */
  public <TOutput> DataResultSupport<TOutput> tx(Supplier<TOutput> func) {
    DataResultSupport<TOutput> result = new DataResultSupport<TOutput>();
    try {
      TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
      result.setData(transactionTemplate.execute(status -> {
        return func.get();
      }));
    } catch (Throwable e) {
      this.logger.error("Transaction Exception:{}", e.getMessage(), e);
      result.onException(e);
    }
    return result;
  }
}
