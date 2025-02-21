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
package lets.support.concurrent.locks;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import lets.support.CommonSupport;
import lets.support.DataResultSupport;
import lets.support.InputOutputResultSupport;
import lets.support.ResultSupport;

/**
 * The Class AbstractLockService.
 */
public abstract class AbstractLockService {
  
  /** The Constant LOCK_EXCEPTION. */
  public static final String LOCK_EXCEPTION = "System Is Busy";

  /**
   * Builds the lock name.
   *
   * @param clasz the clasz
   * @param key   the key
   * @return the string
   */
  public String buildLockName(Class<?> clasz, Object key) {
    return String.format("[%s][%s]", clasz.getName(), key);
  }

  /**
   * 获取锁.
   *
   * @param name the name
   * @return the lock
   */
  public abstract Lock getLock(String name);

  /**
   * Lock.
   *
   * @param name     the name
   * @param runnable the runnable
   * @return the result support
   */
  public ResultSupport lock(String name, Runnable runnable) {
    ResultSupport result = new ResultSupport();
    try {
      Lock lock = this.getLock(name);
      if (lock.tryLock()) {
        try {
          runnable.run();
        } finally {
          lock.unlock();
        }
      } else {
        CommonSupport.checkState(false, LOCK_EXCEPTION);
      }
    } catch (Throwable e) {
      result.onException(e);
    }
    return result;
  }

  /**
   * Lock.
   *
   * @param <TInput> the generic type
   * @param name     the name
   * @param input    the input
   * @param consumer the consumer
   * @return the result support
   */
  public <TInput> ResultSupport lock(String name, TInput input, Consumer<TInput> consumer) {
    ResultSupport result = new ResultSupport();
    try {
      Lock lock = this.getLock(name);
      if (lock.tryLock()) {
        try {
          consumer.accept(input);
        } finally {
          lock.unlock();
        }
      } else {
        CommonSupport.checkState(false, LOCK_EXCEPTION);
      }
    } catch (Throwable e) {
      result.onException(e);
    }
    return result;
  }

  /**
   * Lock.
   *
   * @param <TInput>  the generic type
   * @param <TOutput> the generic type
   * @param name      the name
   * @param input     the input
   * @param func      the func
   * @return the input output result support
   */
  public <TInput, TOutput> InputOutputResultSupport<TInput, TOutput> lock(String name, TInput input,
      Function<TInput, TOutput> func) {
    InputOutputResultSupport<TInput, TOutput> result = new InputOutputResultSupport<TInput, TOutput>();
    try {
      Lock lock = this.getLock(name);
      if (lock.tryLock()) {
        try {
          result.setInput(input);
          result.setOutput(func.apply(input));
        } finally {
          lock.unlock();
        }
      } else {
        CommonSupport.checkState(false, LOCK_EXCEPTION);
      }
    } catch (Throwable e) {
      result.onException(e);
    }
    return result;
  }

  /**
   * Lock.
   *
   * @param time     the time
   * @param unit     the unit
   * @param name     the name
   * @param runnable the runnable
   * @return the result support
   */
  public ResultSupport lock(long time, TimeUnit unit, String name, Runnable runnable) {
    ResultSupport result = new ResultSupport();
    try {
      Lock lock = this.getLock(name);
      if (lock.tryLock(time, unit)) {
        try {
          runnable.run();
        } finally {
          lock.unlock();
        }
      } else {
        CommonSupport.checkState(false, LOCK_EXCEPTION);
      }
    } catch (Throwable e) {
      result.onException(e);
    }
    return result;
  }

  /**
   * Lock.
   *
   * @param <TInput> the generic type
   * @param time     the time
   * @param unit     the unit
   * @param name     the name
   * @param input    the input
   * @param consumer the consumer
   * @return the result support
   */
  public <TInput> ResultSupport lock(long time, TimeUnit unit, String name, TInput input,
      Consumer<TInput> consumer) {
    ResultSupport result = new ResultSupport();
    try {
      Lock lock = this.getLock(name);
      if (lock.tryLock(time, unit)) {
        try {
          consumer.accept(input);
        } finally {
          lock.unlock();
        }
      } else {
        CommonSupport.checkState(false, LOCK_EXCEPTION);
      }
    } catch (Throwable e) {
      result.onException(e);
    }
    return result;
  }

  /**
   * Lock.
   *
   * @param <TInput>  the generic type
   * @param <TOutput> the generic type
   * @param time      the time
   * @param unit      the unit
   * @param name      the name
   * @param input     the input
   * @param func      the func
   * @return the input output result support
   */
  public <TInput, TOutput> InputOutputResultSupport<TInput, TOutput> lock(long time, TimeUnit unit,
      String name, TInput input, Function<TInput, TOutput> func) {
    InputOutputResultSupport<TInput, TOutput> result = new InputOutputResultSupport<TInput, TOutput>();
    try {
      Lock lock = this.getLock(name);
      if (lock.tryLock(time, unit)) {
        try {
          result.setInput(input);
          result.setOutput(func.apply(input));
        } finally {
          lock.unlock();
        }
      } else {
        CommonSupport.checkState(false, LOCK_EXCEPTION);
      }
    } catch (Throwable e) {
      result.onException(e);
    }
    return result;
  }

  /**
   * Lock.
   *
   * @param clasz    the clasz
   * @param key      the key
   * @param runnable the runnable
   * @return the result support
   */
  public ResultSupport lock(Class<?> clasz, Object key, Runnable runnable) {
    return this.lock(buildLockName(clasz, key), runnable);
  }

  /**
   * Lock.
   *
   * @param <TOutput> the generic type
   * @param name      the name
   * @param func      the func
   * @return the data result support
   */
  public <TOutput> DataResultSupport<TOutput> lock(String name, Supplier<TOutput> func) {
    DataResultSupport<TOutput> result = new DataResultSupport<TOutput>();
    try {
      Lock lock = this.getLock(name);
      if (lock.tryLock()) {
        try {
          result.setData(func.get());
        } finally {
          lock.unlock();
        }
      } else {
        CommonSupport.checkState(false, LOCK_EXCEPTION);
      }
    } catch (Throwable e) {
      result.onException(e);
    }
    return result;
  }

  /**
   * Lock.
   *
   * @param <TOutput> the generic type
   * @param time      the time
   * @param unit      the unit
   * @param name      the name
   * @param func      the func
   * @return the data result support
   */
  public <TOutput> DataResultSupport<TOutput> lock(long time, TimeUnit unit, String name,
      Supplier<TOutput> func) {
    DataResultSupport<TOutput> result = new DataResultSupport<TOutput>();
    try {
      Lock lock = this.getLock(name);
      if (lock.tryLock(time, unit)) {
        try {
          result.setData(func.get());
        } finally {
          lock.unlock();
        }
      } else {
        CommonSupport.checkState(false, LOCK_EXCEPTION);
      }
    } catch (Throwable e) {
      result.onException(e);
    }
    return result;
  }
}
