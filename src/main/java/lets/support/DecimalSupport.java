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
package lets.support;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Map;

/**
 * DecimalSupport.
 * 
 * <pre>
 * <code>
 * DecimalSupport decimal = new DecimalSupport();
 * BigDecimal round = decimal.round(new BigDecimal("3.1415167"));
 * LogSupport.info("round:{}->{}", new BigDecimal("3.1415167"), round);
 * LogSupport.info("multiple:{}*3->{}", new BigDecimal("3.1415167"),decimal.multiple(new BigDecimal("3.1415167"), new BigDecimal("3")));
 * LogSupport.info("divide:{}/3->{}", new BigDecimal("3.1415167"),decimal.divide(new BigDecimal("3.1415167"), new BigDecimal("3")));
 * LogSupport.info("add:{}+{}->{}", new BigDecimal("3.1415167"), new BigDecimal("3.1415167"),decimal.add(new BigDecimal("3.1415167"), new BigDecimal("3.1415167")));
 * LogSupport.info("subtract:{}-{}->{}", new BigDecimal("3.1415167"), new BigDecimal("1.1815167"),decimal.subtract(new BigDecimal("3.1415167"), new BigDecimal("1.1815167")));
 * </code>
 * 
 * <pre>
 * round:3.1415167->3.14
 * multiple:3.1415167*3->9.42
 * divide:3.1415167/3->1.05
 * add:3.1415167+3.1415167->6.28
 * subtract:3.1415167-1.1815167->1.96
 * </pre>
 *
 */
public class DecimalSupport {

  /** The Constant SCALE_DEFAULT. */
  public static final int SCALE_DEFAULT = 2;

  /** The math context. */
  private MathContext mathContext = MathContext.DECIMAL32;

  /** The Constant caches. */
  private static final Map<Integer, DecimalSupport> caches = new java.util.concurrent.ConcurrentHashMap<Integer, DecimalSupport>();

  /** The scale. */
  private int scale = SCALE_DEFAULT;

  /** The rounding mode. */
  private int roundingMode = BigDecimal.ROUND_HALF_UP;

  /**
   * Instantiates a new decimal support.
   */
  public DecimalSupport() {
    this(SCALE_DEFAULT);
  }

  /**
   * Instantiates a new decimal support.
   *
   * @param scale the scale
   */
  public DecimalSupport(int scale) {
    super();
    this.scale = scale;
  }

  /**
   * Gets the math context.
   *
   * @return the math context
   */
  public MathContext getMathContext() {
    return mathContext;
  }

  /**
   * Sets the math context.
   *
   * @param mathContext the new math context
   */
  public void setMathContext(MathContext mathContext) {
    this.mathContext = mathContext;
  }

  /**
   * Multiple.
   *
   * @param items the items
   * @return the big decimal
   */
  public BigDecimal multiple(BigDecimal... items) {
    BigDecimal result = BigDecimal.ONE;
    for (BigDecimal item : items) {
      result = result.multiply(this.round(item), this.mathContext);
    }
    return this.round(result);
  }

  /**
   * Divide.
   *
   * @param items the items
   * @return the big decimal
   */
  public BigDecimal divide(BigDecimal... items) {
    CommonSupport.checkState(items != null && items.length > 1);
    BigDecimal result = null;
    for (BigDecimal item : items) {
      if (result == null) {
        result = item;
      } else {
        result = result.divide(this.round(item), this.mathContext);
      }
    }
    return this.round(result);
  }

  /**
   * Adds the.
   *
   * @param items the items
   * @return the big decimal
   */
  public BigDecimal add(BigDecimal... items) {
    BigDecimal result = BigDecimal.ZERO;
    for (BigDecimal item : items) {
      result = result.add(this.round(item));
    }
    return this.round(result);
  }

  /**
   * Subtract the.
   *
   * @param items the items
   * @return the big decimal
   */
  public BigDecimal subtract(BigDecimal... items) {
    CommonSupport.checkState(items != null && items.length > 1);
    BigDecimal result = null;
    for (BigDecimal item : items) {
      if (result == null) {
        result = item;
      } else {
        result = result.subtract(this.round(item));
      }
    }
    return this.round(result);
  }

  /**
   * Round.
   *
   * @param item the item
   * @return the big decimal
   */
  public BigDecimal round(BigDecimal item) {
    return item.divide(BigDecimal.ONE, this.scale, this.roundingMode);
  }

  /**
   * Gets the single instance of DecimalSupport.
   *
   * @param scale the scale
   * @return single instance of DecimalSupport
   */
  public synchronized static DecimalSupport getInstance(int scale) {
    if (caches.containsKey(scale)) {
      return caches.get(scale);
    }
    DecimalSupport instance = new DecimalSupport(scale);
    caches.put(scale, instance);
    return instance;
  }
}
