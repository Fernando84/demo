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

/**
 * TxManager
 * 
 */
public interface TxManager {

  /**
   * Tx result.
   *
   * @param callback the callback
   * @return the result support
   */
  public ResultSupport tx(Runnable callback);

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
      Function<TInput, TOutput> func);

  /**
   * Tx.
   *
   * @param <TOutput> the generic type
   * @param func      the func
   * @return the data result support
   */
  public <TOutput> DataResultSupport<TOutput> tx(Supplier<TOutput> func);
}