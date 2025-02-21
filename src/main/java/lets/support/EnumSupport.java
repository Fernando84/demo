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

// TODO: Auto-generated Javadoc
/**
 * The Class EnumSupport.
 *
 * @param <TCode> the generic type
 */
public class EnumSupport<TCode> {
 
 /** The code. */
 private TCode code;
 
 /** The message. */
 private String message;
 
 /** The description. */
 private String description;

 /**
  * Instantiates a new enum support.
  *
  * @param code the code
  * @param message the message
  * @param description the description
  */
 public EnumSupport(TCode code, String message, String description) {
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

 /**
  * To string.
  *
  * @return the string
  */
 @Override
 public String toString() {
  return "EnumSupport [code=" + code + ", message=" + message + ", description=" + description + "]";
 }

 /**
  * Instantiates a new enum support.
  *
  * @param copyFrom the copy from
  * @param description the description
  */
 public EnumSupport(EnumSupport<TCode> copyFrom, String description) {
  this(copyFrom.code(), copyFrom.message(), description);
 }

 /**
  * Checks if is.
  *
  * @param that the that
  * @return true, if successful
  */
 public boolean is(EnumSupport<TCode> that) {
  return this.code().equals(that.code());
 }

}
