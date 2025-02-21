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

import java.text.ParseException;
import java.util.Date;
import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * Extends DateUtils.
 */
public class DateSupport extends org.apache.commons.lang3.time.DateUtils {

  /** The parse patterns. */
  private static String[] parsePatterns = { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
      "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyyMMddHHmmss", "yyyyMMdd" };

  /**
   * Try parse date.
   *
   * @param date the date
   * @return the date
   */
  public static Date tryParseDate(Object date) {
    if (date == null) {
      return null;
    }
    if (date instanceof Date) {
      return (Date) date;
    }
    if (date instanceof Number) {
      return new Date(((Number) date).longValue());
    }
    try {
      return parseDate(date.toString(), parsePatterns);
    } catch (ParseException e) {
      LogSupport.error("tryParseDate {} with error {}", date, e);
      return null;
    }
  }

  /**
   * Try format.
   *
   * @param date   the date
   * @param format the format
   * @return the string
   */
  public static String tryFormat(Date date, String format) {
    try {
      return DateFormatUtils.format(date, format);
    } catch (Throwable e) {
      return "";
    }
  }

  /**
   * Try format date.
   *
   * @param date the date
   * @return the string
   */
  public static String tryFormatDate(Date date) {
    return tryFormat(date, "yyyy-MM-dd");
  }

  /**
   * Try format date time.
   *
   * @param date the date
   * @return the string
   */
  public static String tryFormatDateTime(Date date) {
    return tryFormat(date, "yyyy-MM-dd HH:mm");
  }

  /**
   * Try format date time long.
   *
   * @param date the date
   * @return the string
   */
  public static String tryFormatDateTimeLong(Date date) {
    return tryFormat(date, "yyyy-MM-dd HH:mm:ss");
  }
}
