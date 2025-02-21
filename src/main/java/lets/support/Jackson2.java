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

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class Jackson2.
 */
public class Jackson2 implements JsonSerialize,JsonService {

 /** The default. */
 public static Jackson2 DEFAULT = new Jackson2();

 /** The mapper. */
 private ObjectMapper mapper = new ObjectMapper();

 /**
  * Instantiates a new jackson 2.
  */
 public Jackson2() {
  this.onConfig(mapper);
 }

 /**
  * Instantiates a new jackson 2.
  *
  * @param mapper the mapper
  */
 public Jackson2(ObjectMapper mapper) {
  super();
  this.mapper = mapper;
 }

 /**
  * Gets the mapper.
  *
  * @return the mapper
  */
 public ObjectMapper getMapper() {
  return mapper;
 }

 /**
  * Sets the mapper.
  *
  * @param mapper the new mapper
  */
 public void setMapper(ObjectMapper mapper) {
  this.mapper = mapper;
 }

 /**
  * To json.
  *
  * @param obj the obj
  * @return the string
  */
 @Override
 public String toJson(Object obj) {
  try {
   return this.getMapper().writeValueAsString(obj);
  } catch (JsonProcessingException e) {
   throw new RuntimeException(e.getMessage());
  }
 }

 /**
  * From json.
  *
  * @param <T>      the generic type
  * @param json     the json
  * @param classOfT the class of T
  * @return the t
  */
 @Override
 public <T> T fromJson(String json, Class<T> classOfT) {
  try {
   return mapper.readValue(json, classOfT);
  } catch (Throwable e) {
   throw new RuntimeException(e.getMessage());
  }
 }

 /**
  * From json.
  *
  * @param <T>      the generic type
  * @param json     the json
  * @param javaType the java type
  * @return the t
  */
 public <T> T fromJson(String json, JavaType javaType) {
  try {
   return mapper.readValue(json, javaType);
  } catch (Throwable e) {
   throw new RuntimeException(e.getMessage());
  }
 }

 /**
  * List from json.
  *
  * @param <T>  the generic type
  * @param json the json
  * @param type the type
  * @return the list
  */
 public <T> List<T> listFromJson(String json, Class<T> type) {
  try {
   JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, type);
   return mapper.readValue(json, javaType);
  } catch (Throwable e) {
   throw new RuntimeException(e.getMessage());
  }
 }

 /**
  * On config.
  *
  * @param mapper the mapper
  */
 public void onConfig(ObjectMapper mapper) {
  mapper.setSerializationInclusion(Include.NON_NULL);
  mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
  mapper.setDateFormat(dateFormat);
 }

 /**
  * Type factory.
  *
  * @return the type factory
  */
 public TypeFactory typeFactory() {
  return this.getMapper().getTypeFactory();
 }

 /**
  * Construct parametric type.
  *
  * @param parametrized     the parametrized
  * @param parameterClasses the parameter classes
  * @return the java type
  */
 public JavaType constructParametricType(Class<?> parametrized, Class<?>... parameterClasses) {
  return this.typeFactory().constructParametricType(parametrized, parameterClasses);
 }

 /**
  * Construct parametric type.
  *
  * @param rawType        the raw type
  * @param parameterTypes the parameter types
  * @return the java type
  */
 public JavaType constructParametricType(Class<?> rawType, JavaType... parameterTypes) {
  return this.typeFactory().constructParametricType(rawType, parameterTypes);
 }
}