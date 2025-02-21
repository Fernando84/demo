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

import org.apache.commons.lang3.StringUtils;

/**
 * The Enum FilterOperatorEnum.
 */
public enum FilterOperatorEnum {

 // The filter operator (comparison).
 // The supported operators are: "eq" (equal to), "neq" (not equal to), "isnull"
 // (is equal to null), "isnotnull" (is not equal to null), "lt" (less than),
 // "lte" (less than or equal to), "gt" (greater than),
 // "gte" (greater than or equal to), "startswith", "endswith", "contains",
 // "isempty", "isnotempty".
 // The last five are supported only for string fields.

 /** The null. */
 NULL("", "(none)"), 
 /** The eq. */
 EQ("eq", "(equal to)"), 
 /** The neq. */
 NEQ("neq", "(not equal to)"), 
 /** The is null. */
 IS_NULL("isnull", "(is equal to null)"),
 
 /** The is not null. */
 IS_NOT_NULL("isnotnull", "(is not equal to null)"), 
 /** The lt. */
 LT("lt", "(less than)"), 
 /** The lte. */
 LTE("lte", "(less than or equal to)"),
 
 /** The gt. */
 GT("gt", "(greater than)"), 
 /** The gte. */
 GTE("gte", "(greater than or equal to)"), 
 /** The startswith. */
 STARTSWITH("startswith", "startswith"),
 
 /** The endwith. */
 ENDWITH("endswith", "endswith"), 
 /** The contains. */
 CONTAINS("contains", "contains"), 
 /** The is empty. */
 IS_EMPTY("isempty", "isempty"),
 
 /** The is not empty. */
 IS_NOT_EMPTY("isnotempty", "isnotempty");

 /** The message. */
 private String message;
 
 /** The value. */
 private String value;

 /**
  * Instantiates a new filter operator enum.
  *
  * @param value the value
  * @param message the message
  */
 private FilterOperatorEnum(String value, String message) {
  this.value = value;
  this.message = message;
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
  * Gets the value.
  *
  * @return the value
  */
 public String getValue() {
  return value;
 }

 /**
  * In.
  *
  * @param value the value
  * @param values the values
  * @return true, if successful
  */
 public static boolean in(String value, FilterOperatorEnum... values) {
  if (values == null) {
   return false;
  }
  for (FilterOperatorEnum v : values) {
   if (StringUtils.equalsIgnoreCase(v.getValue(), value)) {
    return true;
   }
  }
  return false;
 }

 /**
  * No in.
  *
  * @param value the value
  * @param values the values
  * @return true, if successful
  */
 public static boolean noIn(String value, FilterOperatorEnum... values) {
  if (values == null) {
   return true;
  }
  for (FilterOperatorEnum v : values) {
   if (StringUtils.equalsIgnoreCase(v.getValue(), value)) {
    return false;
   }
  }
  return true;
 }

 /**
  * From.
  *
  * @param value the value
  * @return the filter operator enum
  */
 public static FilterOperatorEnum from(String value) {
  FilterOperatorEnum[] values = FilterOperatorEnum.values();
  for (FilterOperatorEnum v : values) {
   if (StringUtils.equalsIgnoreCase(v.getValue(), value)) {
    return v;
   }
  }
  return FilterOperatorEnum.NULL;
 }
}
