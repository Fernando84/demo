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

import java.util.concurrent.Callable;

/**
 * DataResultSupportUtils.
 */
public class DataResultSupportUtils {

 /**
  * Wrap.
  *
  * @param <T>      the generic type
  * @param callable the callable
  * @return the callable
  */
 public static <T> Callable<DataResultSupport<T>> wrap(Callable<T> callable) {
  return new Callable<DataResultSupport<T>>() {

   @Override
   public DataResultSupport<T> call() throws Exception {
    DataResultSupport<T> result = new DataResultSupport<T>();
    try {
     result.setData(callable.call());
    } catch (Throwable e) {
     result.onException(e);
    }
    return result;
   }
  };
 }

 /**
  * Of.
  *
  * @param <T>      the generic type
  * @param callable the callable
  * @return the data result support
  */
 public static <T> DataResultSupport<T> of(Callable<T> callable) {
  DataResultSupport<T> result = new DataResultSupport<T>();
  try {
   result.setData(callable.call());
  } catch (Throwable e) {
   result.onException(e);
  }
  return result;
 }
}
