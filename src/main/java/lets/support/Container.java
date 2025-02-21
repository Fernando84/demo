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

/**
 * The Interface Container.
 */
public abstract interface Container {
 
 /**
  * Inits the Container.
  *
  * @param params the params
  */
 public abstract void init(Object params);

 /**
  * Gets the component.
  *
  * @param key the key
  * @return the component
  */
 public abstract Object getComponent(Object key);

 /**
  * Gets the components.
  *
  * @param <T> the generic type
  * @param clasz the clasz
  * @return the components
  */
 public abstract <T> Map<String, T> getComponents(Class<T> clasz);

 /**
  * Reload.
  */
 public abstract void reload();

 /**
  * Destroy.
  */
 public abstract void destroy();
}