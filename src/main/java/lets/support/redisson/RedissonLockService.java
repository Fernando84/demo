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
package lets.support.redisson;

import java.util.concurrent.locks.Lock;
import lets.support.InputOutputResultSupport;
import lets.support.concurrent.locks.AbstractLockService;
import lets.support.concurrent.locks.LockService;


/**
 * RedissonLockService.
 */
public class RedissonLockService extends AbstractLockService implements LockService {

 /** The redisson. */
 private RedissonSupport redisson;

 /**
  * Instantiates a new redisson lock service.
  *
  * @param redisson the redisson
  */
 public RedissonLockService(RedissonSupport redisson) {
  super();
  this.redisson = redisson;
 }

 /**
  * Gets the lock.
  *
  * @param name the name
  * @return the lock
  */
 @Override
 public Lock getLock(String name) {
  InputOutputResultSupport<String, Lock> getLock = this.redisson.getLock(name);
  getLock.checkState();
  Lock lock = getLock.getOutput();
  return lock;
 }

}
