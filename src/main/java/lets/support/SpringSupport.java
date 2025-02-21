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
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

// TODO: Auto-generated Javadoc
/**
 * The Class SpringSupport.
 */
public class SpringSupport implements ApplicationContextAware {

 /** The context. */
 protected ApplicationContext context;

 /**
  * Sets the application context.
  *
  * @param conext the new application context
  * @throws BeansException the beans exception
  */
 /*
  * (non-Javadoc)
  * 
  * @see org.springframework.context.ApplicationContextAware#setApplicationContext (org.springframework.context.ApplicationContext)
  */
 @Override
 public void setApplicationContext(ApplicationContext conext) throws BeansException {
  this.context = conext;
  try {
   onSetup();
  } catch (Exception ex) {
   throw new BeanInitializationException("Initialization", ex);
  }
 }

 /**
  * On setup.
  *
  * @throws Exception the exception
  */
 public void onSetup() throws Exception {
 }

 /**
  * Gets the context.
  *
  * @return the context
  */
 public ApplicationContext getContext() {
  return context;
 }

 /**
  * Gets the components.
  *
  * @param <Component> the generic type
  * @param key         the key
  * @return the components
  */
 public <Component> Map<String, Component> getComponents(Class<Component> key) {
  Map<String, Component> beans = this.context.getBeansOfType(key);
  return beans;
 }

 /**
  * Gets the services.
  *
  * @param <Service> the generic type
  * @param key       the key
  * @return the services
  */
 public <Service> List<Service> getServices(Class<Service> key) {
  List<Service> beans = new ArrayList<Service>();
  beans.addAll(this.getComponents(key).values());
  return beans;
 }

 /**
  * Gets the service.
  *
  * @param <Service> the generic type
  * @param key       the key
  * @return the service
  */
 public <Service> Service getService(Class<Service> key) {
  return this.context.getBean(key);
 }

 /**
  * Gets the bean by name or required type.
  *
  * @param <T>          the generic type
  * @param name         the name
  * @param requiredType the required type
  * @return the bean by name or required type
  */
 public <T> T getBeanByNameOrRequiredType(String name, Class<T> requiredType) {
  if (StringUtils.isBlank(name)) {
   return this.context.getBean(requiredType);
  } else {
   return this.context.getBean(name, requiredType);
  }
 }
}
