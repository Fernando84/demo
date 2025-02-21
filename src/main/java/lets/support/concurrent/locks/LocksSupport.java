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

import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;
import java.util.function.Consumer;

/**
 * LockSupport.
 * 
 * @author zhoupan.
 * @param <Key> the generic type
 */
public class LocksSupport<Key> {

 /** The locks. */
 private Map<Key, ReentrantReadWriteLock> locks = new java.util.concurrent.ConcurrentHashMap<>();

 /**
  * Gets the.
  *
  * @param key the key
  * @return the reentrant read write lock
  */
 public synchronized ReentrantReadWriteLock get(Key key) {
  if (!this.locks.containsKey(key)) {
   locks.put(key, new ReentrantReadWriteLock(true));
  }
  return locks.get(key);
 }

 /**
  * Read lock.
  *
  * @param key the key
  * @return the read lock
  */
 public synchronized ReadLock readLock(Key key) {
  return this.get(key).readLock();
 }

 /**
  * Write lock.
  *
  * @param key the key
  * @return the write lock
  */
 public synchronized WriteLock writeLock(Key key) {
  return this.get(key).writeLock();
 }

 /**
  * Read.
  *
  * @param key      the key
  * @param consumer the consumer
  */
 public synchronized void read(Key key, Consumer<Key> consumer) {
  ReadLock lock = this.readLock(key);
  try {
   lock.lock();
   consumer.accept(key);
  } finally {
   lock.unlock();
  }
 }

 /**
  * Write.
  *
  * @param key      the key
  * @param consumer the consumer
  */
 public synchronized void write(Key key, Consumer<Key> consumer) {
  WriteLock lock = this.writeLock(key);
  try {
   lock.lock();
   consumer.accept(key);
  } finally {
   lock.unlock();
  }
 }
}
