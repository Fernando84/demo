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

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * GenericUtils.
 */
@SuppressWarnings("rawtypes")
public class GenericUtils {

 /**
  * Gets the super class generic type.
  *
  * @param <T> the generic type
  * @param clazz the clazz
  * @return the super class generic type
  */
 @SuppressWarnings("unchecked")
 public static <T> Class<T> getSuperClassGenericType(Class clazz) {
  return getSuperClassGenericType(clazz, 0);
 }

 /**
  * Gets the super class generic type.
  *
  * @param clazz the clazz
  * @param index the index
  * @return the super class generic type
  */
 public static Class getSuperClassGenericType(Class clazz, int index) {
  Type genType = null;
  Class superclass = clazz;
  while (superclass != null) {
   genType = superclass.getGenericSuperclass();
   if ((genType instanceof ParameterizedType)) {
    break;
   }
   superclass = superclass.getSuperclass();
  }
  if (!(genType instanceof ParameterizedType)) {

   return Object.class;
  }

  Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

  if ((index >= params.length) || (index < 0)) {
   return Object.class;
  }
  if (!(params[index] instanceof Class)) {

   return Object.class;
  }
  return (Class) params[index];
 }

}
