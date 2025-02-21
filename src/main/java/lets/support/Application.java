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

import java.util.ArrayList;
import java.util.List;

public class Application {
 private Container container;
 private static final Application instance = new Application();

 public static Application getInstance() {
  return instance;
 }

 public Container getContainer() {
  return this.container;
 }

 public void setContainer(Container container) {
  this.container = container;
 }

 /**
  * Gets the bean.
  * 
  * @param key the key
  * @return the bean
  */
 public Object getBean(Object key) {
  return getContainer().getComponent(key);
 }

 @SuppressWarnings("unchecked")
 public <T> T getService(Class<T> key) {
  return (T) getBean(key);
 }

 public <T> List<T> getServices(Class<T> key) {
  List<T> beans = new ArrayList<T>();
  beans.addAll(this.getContainer().getComponents(key).values());
  return beans;
 }

 /**
  * Checks if is container available.
  *
  * @return true, if checks if is container available
  */
 public static boolean isContainerAvailable() {
  return Application.getInstance().getContainer() != null;
 }
}