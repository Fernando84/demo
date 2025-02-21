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
package lets.support.concurrent;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

/**
 * The Class LazySupplierSupport.
 *
 * @param <V> the value type
 * @author zhoupan
 */
public class LazySupplierSupport<V> implements Supplier<V> {

 /** The ref. */
 private AtomicReference<V> ref = new AtomicReference<>();

 /** The supplier. */
 private Supplier<V> supplier;

 /**
  * The Constructor.
  *
  * @param supplier the supplier
  */
 public LazySupplierSupport(Supplier<V> supplier) {
  super();
  this.supplier = supplier;
 }

 /**
  * Gets the.
  *
  * @return the v
  */
 public V get() {
  if (ref.get() == null) {
   V v = this.supplier.get();
   ref.set(v);
   return v;
  } else {
   return ref.get();
  }
 }
}
