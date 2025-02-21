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

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * CounterSupport.
 */
public class CounterSupport implements java.io.Serializable, ICounter {

 /** The Constant serialVersionUID. */
 private static final long serialVersionUID = 1L;
 
 /** The map. */
 Map<String, AtomicLong> map = new java.util.concurrent.ConcurrentHashMap<String, AtomicLong>();

 /**
  * Gets the.
  *
  * @param key the key
  * @return the atomic long
  */
 public AtomicLong get(String key) {
  if (!map.containsKey(key)) {
   map.put(key, new AtomicLong(0));
  }
  return map.get(key);
 }

 /**
  * Next.
  *
  * @param key the key
  * @return the long
  */
 public Long next(String key) {
  return this.get(key).incrementAndGet();
 }

 /**
  * Increment.
  *
  * @param key the key
  * @return the long
  */
 @Override
 public long increment(String key) {
  return this.get(key).incrementAndGet();
 }

 /**
  * Decrement.
  *
  * @param key the key
  * @return the long
  */
 @Override
 public long decrement(String key) {
  return this.get(key).decrementAndGet();
 }

 /**
  * Gets the and set.
  *
  * @param key the key
  * @param newValue the new value
  * @return the and set
  */
 @Override
 public long getAndSet(String key, long newValue) {
  return this.get(key).getAndSet(newValue);
 }

 /**
  * Gets the value.
  *
  * @param key the key
  * @return the value
  */
 @Override
 public long getValue(String key) {
  return this.get(key).longValue();
 }

 /**
  * Increment.
  *
  * @param key the key
  * @param amount the amount
  * @return the long
  */
 @Override
 public long increment(String key, long amount) {
  return this.get(key).addAndGet(amount);
 }

 /**
  * Decrement.
  *
  * @param key the key
  * @param amount the amount
  * @return the long
  */
 @Override
 public long decrement(String key, long amount) {
  return this.increment(key, -amount);
 }

 /**
  * Sets the value.
  *
  * @param key the key
  * @param newValue the new value
  */
 @Override
 public void setValue(String key, long newValue) {
  this.get(key).set(newValue);
 }

}
