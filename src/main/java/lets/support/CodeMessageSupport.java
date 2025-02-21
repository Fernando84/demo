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
 * CodeMessageSupport.
 *
 * @param <TCode> the generic type
 */
public class CodeMessageSupport<TCode> {
 
 /** The code. */
 private TCode code;
 
 /** The message. */
 private String message;
 
 /** The description. */
 private String description;

 /**
  * Instantiates a new code message support.
  *
  * @param code the code
  * @param message the message
  * @param description the description
  */
 public CodeMessageSupport(TCode code, String message, String description) {
  super();
  this.code = code;
  this.message = message;
  this.description = description;
 }

 /**
  * Code.
  *
  * @return the t code
  */
 public TCode code() {
  return this.code;
 }

 /**
  * Message.
  *
  * @return the string
  */
 public String message() {
  return this.message;
 }

 /**
  * Description.
  *
  * @return the string
  */
 public String description() {
  return this.description;
 }

}
