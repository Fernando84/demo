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
 * The Class StatusSupport.
 */
public class StatusSupport implements java.io.Serializable {

 /** The Constant STATUS_SUCCESS. */
 public static final int STATUS_SUCCESS = HttpStatus.OK.value();
 
 /** The Constant STATUS_ERROR. */
 public static final int STATUS_ERROR = HttpStatus.INTERNAL_SERVER_ERROR.value();

 /** The Constant serialVersionUID. */
 private static final long serialVersionUID = 1L;

 /** The status. */
 private int status;

 /** The success. */
 private boolean success = true;

 /** The message. */
 private String message = "";

 /**
  * Gets the status.
  *
  * @return the status
  */
 public int getStatus() {
  return status;
 }

 /**
  * Sets the status.
  *
  * @param status the new status
  */
 public void setStatus(int status) {
  this.status = status;
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
  this.setStatus(STATUS_ERROR);
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
  this.setStatus(STATUS_ERROR);
 }

 /**
  * On exception.
  *
  * @param message the message
  * @param e the e
  */
 public void onException(String message, Throwable e) {
  this.setSuccess(false);
  this.setStatus(STATUS_ERROR);
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
  this.setStatus(STATUS_SUCCESS);
 }

 /**
  * On exception.
  *
  * @param e the StatusException
  */
 public void onException(StatusException e) {
  this.setSuccess(false);
  this.setStatus(e.getStatus());
  this.setMessage(e.getMessage());
 }

 /**
  * On status.
  *
  * @param httpStatus the http status
  */
 public void onStatus(HttpStatus httpStatus) {
  this.setSuccess(httpStatus.is2xxSuccessful());
  this.setStatus(httpStatus.value());
  this.setMessage(httpStatus.getReasonPhrase());
 }
}
