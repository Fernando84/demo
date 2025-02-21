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

/**
 * The Interface ICounter.
 */
public interface ICounter {

 /**
  * Increment the counter by 1.
  *
  * @param key the key
  * @return the value after incrementing
  */
 long increment(String key);

 /**
  * Decrement the counter by 1.
  *
  * @param key the key
  * @return the value after decrementing
  */
 long decrement(String key);

 /**
  * Returns the value of the counter and sets it to the new value.
  *
  * @param key the key
  * @param newValue the new value
  * @return Returns the old value
  */
 long getAndSet(String key, long newValue);

 /**
  * Gets current value of the counter.
  *
  * @param key the key
  * @return current value of the counter
  */
 long getValue(String key);

 /**
  * Increment the counter by given amount.
  *
  * @param key the key
  * @param amount the amount
  * @return the value of the counter after incrementing
  */
 long increment(String key, long amount);

 /**
  * Decrement the counter by given amount.
  *
  * @param key the key
  * @param amount the amount
  * @return the value of the counter after decrementing
  */
 long decrement(String key, long amount);

 /**
  * Sets the value of the counter to the supplied value.
  *
  * @param key the key
  * @param newValue the new value
  */
 void setValue(String key, long newValue);
}