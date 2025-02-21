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

import java.util.concurrent.locks.Lock;
import lets.support.CommonSupport;

/**
 * DefaultLockService.
 * 
 */
public class DefaultLockService extends AbstractLockService implements LockService {

  /**
   * Gets the lock.
   *
   * @param name the name
   * @return the lock
   */
  public Lock getLock(String name) {
    CommonSupport.checkState(CommonSupport.isNotBlank(name), "name not allow blank.");
    return Locks.JVM.writeLock(name);
  }
}
