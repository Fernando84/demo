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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.zip.CRC32;
import java.util.zip.Checksum;
import org.apache.commons.lang3.StringUtils;

/**
 * GuidUtils.
 *
 */
public class GuidUtils {

  /** The Constant CHARSET_NAME. */
  private static final String CHARSET_NAME = "UTF-8";

  /**
   * Generate default guid.
   *
   * @return the string
   */
  public static String generateDefaultGuid() {
    return java.util.UUID.randomUUID().toString();
  }

  /**
   * Generate simple guid.
   *
   * @return the string
   */
  public static String generateSimpleGuid() {
    return java.util.UUID.randomUUID().toString().replaceAll("-", "");
  }

  /**
   * Generate default long guid.
   *
   * @return the long
   */
  public static long generateDefaultLongGuid() {
    return generateCheckSumCRC32(generateDefaultGuid().getBytes(Charset.forName(CHARSET_NAME)));
  }

  /**
   * Generate simple long guid.
   *
   * @return the long
   */
  public static long generateSimpleLongGuid() {
    return generateCheckSumCRC32(generateSimpleGuid().getBytes(Charset.forName(CHARSET_NAME)));
  }

  /**
   * Generate default long guid hex.
   *
   * @return the string
   */
  public static String generateDefaultLongGuidHex() {
    return generateCheckSumCRC32Hex(generateDefaultGuid().getBytes(Charset.forName(CHARSET_NAME)));
  }

  /**
   * Generate simple long guid hex.
   *
   * @return the string
   */
  public static String generateSimpleLongGuidHex() {
    return generateCheckSumCRC32Hex(generateSimpleGuid().getBytes(Charset.forName(CHARSET_NAME)));
  }

  /**
   * Generate check sum cr c32.
   *
   * @param bytes the bytes
   * @return the long
   */
  public static long generateCheckSumCRC32(byte[] bytes) {
    Checksum checksum = new CRC32();
    checksum.update(bytes, 0, bytes.length);
    return checksum.getValue();
  }

  /**
   * Generate check sum cr c32 hex.
   *
   * @param bytes the bytes
   * @return the string
   */
  public static String generateCheckSumCRC32Hex(byte[] bytes) {
    Checksum checksum = new CRC32();
    checksum.update(bytes, 0, bytes.length);
    long lngChecksum = checksum.getValue();
    return Long.toHexString(lngChecksum);
  }

  /**
   * Generate CRC 32.
   *
   * @param file the file
   * @return the string
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static String generateCRC32(File file) throws IOException {
    return generateCRC32(new FileInputStream(file));
  }

  /**
   * Generate CRC 32.
   *
   * @param stream the stream
   * @return the string
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static String generateCRC32(InputStream stream) throws IOException {
    byte[] buff = new byte[64 * 1024];
    int len;
    CRC32 crc32 = new CRC32();
    try {
      while ((len = stream.read(buff)) != -1) {
        crc32.update(buff, 0, len);
      }
    } catch (Throwable e) {
      LogSupport.error("{}", CommonSupport.getStackTrace(e));
    } finally {
      CommonSupport.closeQuietly(stream);
    }
    return Long.toHexString(crc32.getValue());
  }

  /**
   * Generate.
   *
   * @param fields the fields
   * @return the string
   */
  public static String generate(String... fields) {
    if (fields == null || fields.length == 0) {
      throw new RuntimeException("generate need fields.");
    }
    StringBuilder sb = new StringBuilder();
    for (String field : fields) {
      if (StringUtils.isBlank(field)) {
        throw new RuntimeException("generate need fields.");
      }
      sb.append(generateCheckSumCRC32Hex(field.getBytes(Charset.forName(CHARSET_NAME))));
    }
    return sb.toString();
  }

  /**
   * Generate long.
   *
   * @param fields the fields
   * @return the long
   */
  public static long generateLong(String... fields) {
    if (fields == null || fields.length == 0) {
      throw new RuntimeException("generate need fields.");
    }
    String stringId = generate(fields);
    return GuidUtils.generateCheckSumCRC32(stringId.getBytes(Charset.forName(CHARSET_NAME)));
  }

}
