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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * The Interface FileStoreService.
 */
public interface FileStoreService {

 /**
  * Gets the store name.
  *
  * @return the store name
  */
 public String getStoreName();

 /**
  * Read file to byte array.
  * 
  * @param path the path
  * @return the byte[]
  * @throws IOException Signals that an I/O exception has occurred.
  */
 byte[] readToByteArray(String path) throws IOException;

 /**
  * Read file to input stream.
  * 
  * @param path the path
  * @return the input stream
  * @throws IOException Signals that an I/O exception has occurred.
  */
 InputStream readToInputStream(String path) throws IOException;

 /**
  * Existed.
  * 
  * @param path the path
  * @return true, if successful
  * @throws IOException Signals that an I/O exception has occurred.
  */
 boolean existed(String path) throws IOException;

 /**
  * Write.
  * 
  * @param path    the path
  * @param content the content
  * @throws IOException Signals that an I/O exception has occurred.
  */
 void write(String path, byte[] content) throws IOException;

 /**
  * Write.
  * 
  * @param path    the path
  * @param content the content
  * @throws IOException Signals that an I/O exception has occurred.
  */
 void write(String path, InputStream content) throws IOException;

 /**
  * Write.
  * 
  * @param path the path
  * @param file the file
  * @throws IOException Signals that an I/O exception has occurred.
  */
 void write(String path, File file) throws IOException;

 /**
  * Delete.
  * 
  * @param path the path
  * @throws IOException Signals that an I/O exception has occurred.
  */
 void delete(String path) throws IOException;

 /**
  * Gets the internet url.
  *
  * @param path the path
  * @return the internet url
  */
 public String getInternetUrl(String path);
}
