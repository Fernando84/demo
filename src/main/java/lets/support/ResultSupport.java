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
 * 操作结果.
 * 
 * @author zhoupan.
 * 
 */
public class ResultSupport implements java.io.Serializable {

 /** The Constant CODE_SUCCESS. */
 public static final int CODE_SUCCESS = 0;
 
 /** The Constant CODE_ERROR. */
 public static final int CODE_ERROR = 500;
 
 /** The Constant serialVersionUID. */
 private static final long serialVersionUID = 1L;

 /** The code. */
 private int code;

 /** The success. */
 private boolean success = true;

 /** The message. */
 private String message = "";

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
  * Checks if is success.
  *
  * @return true, if is success
  */
 public boolean isSuccess() {
  return success;
 }

 /**
  * Sets the success.
  *
  * @param success the new success
  */
 public void setSuccess(boolean success) {
  this.success = success;
 }

 /**
  * Gets the message.
  *
  * @return the message
  */
 public String getMessage() {
  return message;
 }

 /**
  * Sets the message.
  *
  * @param message the new message
  */
 public void setMessage(String message) {
  this.message = message;
 }

 /**
  * On exception.
  *
  * @param e the e
  */
 public void onException(Throwable e) {
  this.setSuccess(false);
  this.setMessage(e.getMessage());
  this.setCode(CODE_ERROR);
  LogSupport.error("{}", CommonSupport.getStackTrace(e));
 }

 /**
  * On exception.
  *
  * @param e         the e
  * @param errorCode the error code
  */
 public void onException(Throwable e, int errorCode) {
  this.setSuccess(false);
  this.setMessage(e.getMessage());
  this.setCode(errorCode);
  LogSupport.error("{}", CommonSupport.getStackTrace(e));
 }

 /**
  * On exception.
  *
  * @param message the message
  */
 public void onException(String message) {
  this.setSuccess(false);
  this.setMessage(message);
  this.setCode(CODE_ERROR);
 }

 /**
  * On exception.
  *
  * @param message   the message
  * @param errorCode the error code
  */
 public void onException(String message, int errorCode) {
  this.setSuccess(false);
  this.setMessage(message);
  this.setCode(errorCode);
 }

 /**
  * On exception.
  *
  * @param message the message
  * @param e the e
  */
 public void onException(String message, Throwable e) {
  this.setSuccess(false);
  this.setCode(CODE_ERROR);
  String cause = e.getMessage();
  if (CommonSupport.isBlank(cause)) {
   this.setMessage(message);
  } else {
   this.setMessage(String.format("%s %s", message, cause));
  }
  LogSupport.error("{}", CommonSupport.getStackTrace(e));
 }

 /**
  * On exception.
  *
  * @param message   the message
  * @param e         the e
  * @param errorCode the error code
  */
 public void onException(String message, Throwable e, int errorCode) {
  this.setSuccess(false);
  this.setCode(errorCode);
  String cause = e.getMessage();
  if (CommonSupport.isBlank(cause)) {
   this.setMessage(message);
  } else {
   this.setMessage(String.format("%s %s", message, cause));
  }
  LogSupport.error("{}", CommonSupport.getStackTrace(e));
 }

 /**
  * On success.
  *
  * @param message the message
  */
 public void onSuccess(String message) {
  this.setSuccess(true);
  this.setMessage(message);
  this.setCode(CODE_SUCCESS);
 }

 /**
  * On exception.
  *
  * @param e the e
  */
 public void onException(DataException e) {
  this.setSuccess(false);
  this.setCode(e.getCode());
  this.setMessage(e.getMessage());
 }

 /**
  * Check state.
  */
 public void checkState() {
  CommonSupport.checkState(this.isSuccess(), this.getMessage());
 }
}