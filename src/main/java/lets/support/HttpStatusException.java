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
 * HttpStatusException.
 */
public class HttpStatusException extends StatusException {

 /** The Constant serialVersionUID. */
 private static final long serialVersionUID = 1L;

 /**
  * Instantiates a new http status exception.
  *
  * @param status the status
  */
 public HttpStatusException(HttpStatus status) {
  super(status.value(), status.getReasonPhrase());
 }

 /**
  * Check success.
  *
  * @param status the status
  */
 public static void checkSuccess(HttpStatus status) {
  if (!status.is2xxSuccessful()) {
   throw new HttpStatusException(status);
  }
 }

 /**
  * Check success.
  *
  * @param statusCode the status code
  */
 public static void checkSuccess(int statusCode) {
  checkSuccess(HttpStatus.valueOf(statusCode));
 }
}
