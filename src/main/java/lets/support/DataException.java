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
 * DataException.
 */
public class DataException extends RuntimeException {

 /** The Constant serialVersionUID. */
 private static final long serialVersionUID = 1L;

 /** The code. */
 private int code = 0;

 /** The msg. */
 private String msg = "";

 /**
  * Gets the code.
  *
  * @return the code
  */
 public int getCode() {
  return code;
 }

 /**
  * Sets the code.
  *
  * @param code the new code
  */
 public void setCode(int code) {
  this.code = code;
 }

 /**
  * Gets the msg.
  *
  * @return the msg
  */
 public String getMsg() {
  return msg;
 }

 /**
  * Sets the msg.
  *
  * @param msg the new msg
  */
 public void setMsg(String msg) {
  this.msg = msg;
 }

 /**
  * Instantiates a new data exception.
  *
  * @param status the status
  * @param msg the msg
  */
 public DataException(int status, String msg) {
  super(msg);
  this.code = status;
  this.msg = msg;
 }

 /**
  * Instantiates a new data exception.
  *
  * @param msg the msg
  */
 public DataException(String msg) {
  this(500, msg);
 }

}
