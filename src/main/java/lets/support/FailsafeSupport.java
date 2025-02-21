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

import java.time.Duration;
import java.util.concurrent.Callable;
import net.jodah.failsafe.Failsafe;
import net.jodah.failsafe.Timeout;
import net.jodah.failsafe.TimeoutExceededException;
import net.jodah.failsafe.function.CheckedSupplier;

/**
 * FailsafeSupport
 *
 */
public class FailsafeSupport {

  /** The interruptable of timeout. */
  public boolean interruptableOfTimeout = true;

  /** The duration of timeout. */
  public Duration durationOfTimeout = Duration.ofSeconds(5);

  /**
   * Checks if is interruptable of timeout.
   *
   * @return true, if is interruptable of timeout
   */
  public boolean isInterruptableOfTimeout() {
    return interruptableOfTimeout;
  }

  /**
   * Sets the interruptable of timeout.
   *
   * @param interruptableOfTimeout the new interruptable of timeout
   * @return the failsafeOnBackgroud support
   */
  public FailsafeSupport setInterruptableOfTimeout(boolean interruptableOfTimeout) {
    this.interruptableOfTimeout = interruptableOfTimeout;
    return this;
  }

  /**
   * Gets the duration of timeout.
   *
   * @return the duration of timeout
   */
  public Duration getDurationOfTimeout() {
    return durationOfTimeout;
  }

  /**
   * Sets the duration of timeout.
   *
   * @param durationOfTimeout the new duration of timeout
   * @return the failsafeOnBackgroud support
   */
  public FailsafeSupport setDurationOfTimeout(Duration durationOfTimeout) {
    this.durationOfTimeout = durationOfTimeout;
    return this;
  }

  /**
   * With timeout.
   *
   * @param checkedSupplier the checked supplier
   * @return the result support
   */
  public ResultSupport withTimeout(CheckedSupplier<ResultSupport> checkedSupplier) {
    return withTimeout(checkedSupplier, this.durationOfTimeout, this.interruptableOfTimeout);
  }

  /**
   * With timeout.
   *
   * @param runnable the runnable
   * @return the result support
   */
  public ResultSupport withTimeout(Runnable runnable) {
    return withTimeout(() -> {
      ResultSupport taskResult = new ResultSupport();
      try {
        runnable.run();
      } catch (Throwable e) {
        taskResult.onException(e);
      }
      return taskResult;
    }, this.durationOfTimeout, this.interruptableOfTimeout);
  }

  /**
   * With timeout.
   *
   * @param <T>      the generic type
   * @param callable the callable
   * @return the data result support
   */
  public <T> DataResultSupport<T> withTimeout(Callable<T> callable) {
    return this.withProcessTimeout(() -> callable.call());
  }

  /**
   * With process timeout.
   *
   * @param <T>             the generic type
   * @param checkedSupplier the checked supplier
   * @return the data result support
   */
  public <T> DataResultSupport<T> withProcessTimeout(CheckedSupplier<T> checkedSupplier) {
    return withProcessTimeout(checkedSupplier, this.durationOfTimeout, this.interruptableOfTimeout);
  }

  /**
   * With timeout.
   *
   * @param checkedSupplier the checked supplier
   * @param timeout         the timeout
   * @param interruptable   the interruptable
   * @return the result support
   */
  public static ResultSupport withTimeout(CheckedSupplier<ResultSupport> checkedSupplier,
      Duration timeout, boolean interruptable) {
    ResultSupport result = new ResultSupport();
    try {
      Timeout<ResultSupport> timeoutPolicy = Timeout.of(timeout);
      timeoutPolicy.withInterrupt(interruptable);
      return Failsafe.with(timeoutPolicy).get(checkedSupplier);
    } catch (TimeoutExceededException timeoutException) {
      result.onException(String.format("Timeout:%s", timeout));
    } catch (Throwable e) {
      result.onException(e);
    }
    return result;
  }

  /**
   * With process timeout.
   *
   * @param <T>             the generic type
   * @param checkedSupplier the checked supplier
   * @param timeout         the timeout
   * @param interruptable   the interruptable
   * @return the data result support
   */
  public static <T> DataResultSupport<T> withProcessTimeout(CheckedSupplier<T> checkedSupplier,
      Duration timeout, boolean interruptable) {
    DataResultSupport<T> result = new DataResultSupport<T>();
    try {
      Timeout<T> timeoutPolicy = Timeout.of(timeout);
      timeoutPolicy.withInterrupt(interruptable);
      result.setData(Failsafe.with(timeoutPolicy).get(checkedSupplier));
    } catch (TimeoutExceededException timeoutException) {
      result.onException(String.format("Timeout:%s", timeout));
    } catch (Throwable e) {
      result.onException(e);
    }
    return result;
  }
}
