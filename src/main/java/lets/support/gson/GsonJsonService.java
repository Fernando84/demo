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
package lets.support.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import lets.support.JsonService;

/**
 * The Class GsonJsonService.
 */
public class GsonJsonService implements JsonService {

  /** The gson. */
  Gson gson;

  /**
   * Instantiates a new default json service.
   *
   * @param gsonBuilder the gson builder
   */
  public GsonJsonService(GsonBuilder gsonBuilder) {
    this(gsonBuilder.create());
  }

  /**
   * Instantiates a new default json service.
   *
   * @param gson the gson
   */
  public GsonJsonService(Gson gson) {
    super();
    this.gson = gson;
  }

  /**
   * To json.
   *
   * @param obj the obj
   * @return the string
   */
  @Override
  public String toJson(Object obj) {
    return gson.toJson(obj);
  }

  /**
   * From json.
   *
   * @param <T> the generic type
   * @param json the json
   * @param classOfT the class of T
   * @return the t
   */
  @Override
  public <T> T fromJson(String json, Class<T> classOfT) {
    return gson.fromJson(json, classOfT);
  }

  /**
   * From json.
   *
   * @param <T> the generic type
   * @param json the json
   * @param typeOfT the type of T
   * @return the t
   */
  public <T> T fromJson(String json, Type typeOfT) {
    return gson.fromJson(json, typeOfT);
  }

  /**
   * List from json.
   *
   * @param <T> the generic type
   * @param json the json
   * @param classOfElement the class of element
   * @return the list
   */
  public <T> List<T> listFromJson(String json, Class<T> classOfElement) {
    Type listType = new TypeToken<ArrayList<String>>() {
    }.getType();
    return gson.fromJson(json, listType);
  }
}
