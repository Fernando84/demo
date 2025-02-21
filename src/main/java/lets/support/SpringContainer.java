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
import java.util.Properties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

@SuppressWarnings("unchecked")
public class SpringContainer implements Container {
 private ApplicationContext applicationContext;

 @Override
 @SuppressWarnings("rawtypes")
 public Object getComponent(Object key) {
  check(key);

  if ((key instanceof Class)) {
   Map<String, Object> beans = this.applicationContext.getBeansOfType((Class) key);
   if (beans == null) {
    throw new NoSuchBeanDefinitionException(
     "The container is unable to resolve single instance of " + ((Class) key).getName() + ", none instances found");
   }
   if ((beans.size() == 0) || (beans.size() > 1)) {
    throw new NoSuchBeanDefinitionException("The container is unable to resolve single instance of " + ((Class) key).getName()
     + ", number of instances found was: " + beans.size());
   }
   key = beans.keySet().iterator().next();
  }
  return this.applicationContext.getBean(key.toString());
 }

 @Override
 public <T> Map<String, T> getComponents(Class<T> key) {
  check(key);
  Map<String, T> beans = this.applicationContext.getBeansOfType(key);
  return beans;
 }

 private void check(Object key) {
  if (this.applicationContext == null) {
   throw new IllegalStateException("Spring Application context has not been set");
  }
  if (key == null)
   throw new NoSuchBeanDefinitionException("The component key can not be null");
 }

 @Override
 public void reload() {
  AbstractApplicationContext aac = (AbstractApplicationContext) this.applicationContext;
  aac.close();
  aac.refresh();
 }

 @Override
 public void init(Object obj) {
  if (obj == null) {
   throw new RuntimeException("initialize parameter object cann't is null");
  }
  if ((obj instanceof ApplicationContext)) {
   this.applicationContext = ((ApplicationContext) obj);
   ProfileUtils.setActiveProfiles(this.applicationContext.getEnvironment().getActiveProfiles());
   ProfileUtils.setDefaultProfiles(this.applicationContext.getEnvironment().getDefaultProfiles());
   return;
  }
  if ((obj instanceof Properties)) {
   Properties props = (Properties) obj;
   String userContext = props.getProperty("userContext", "");
   String contextConfigLocation = props.getProperty("contextConfigLocation", "");
   String[] contexts = StringUtils.split(contextConfigLocation, ",");
   GenericXmlApplicationContext xmlContext = new GenericXmlApplicationContext();
   String defaultProfiles = props.getProperty("defaultProfiles", "");
   if (StringUtils.isNotBlank(defaultProfiles)) {
    LogSupport.info("{} defaultProfiles {}", userContext, defaultProfiles);
    xmlContext.getEnvironment().setDefaultProfiles(StringUtils.split(defaultProfiles));
   }
   if (ProfileUtils.getActiveProfiles() != null) {
    LogSupport.info("{} activeProfiles {}", userContext, ProfileUtils.getActiveProfiles());
    xmlContext.getEnvironment().setActiveProfiles(ProfileUtils.getActiveProfiles());
   }
   if (ProfileUtils.getDefaultProfiles() != null) {
    LogSupport.info("{} defaultProfiles {}", userContext, ProfileUtils.getDefaultProfiles());
    xmlContext.getEnvironment().setDefaultProfiles(ProfileUtils.getDefaultProfiles());
   }
   xmlContext.load(contexts);
   xmlContext.refresh();
   this.applicationContext = xmlContext;
   return;
  }
  throw new RuntimeException("initialize parameter object must's instance of ApplicationContext or Properties");
 }

 @Override
 public void destroy() {
  ((AbstractApplicationContext) this.applicationContext).close();
 }
}