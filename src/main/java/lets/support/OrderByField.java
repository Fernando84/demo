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

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 * The Class OrderByField.
 */
public class OrderByField {

 /** The table name. */
 private String tableName;

 /** The field name. */
 private String fieldName;

 /** The column name. */
 private String columnName;

 /** The is ascending. */
 private boolean isAscending = true;

 /** The name mapper. */
 INameMapper nameMapper;

 /**
  * Instantiates a new order by field.
  *
  * @param nameMapper the name mapper
  * @param tableName the table name
  * @param fieldName the field name
  * @param isAscending the is ascending
  */
 public OrderByField(INameMapper nameMapper, String tableName, String fieldName, boolean isAscending) {
  super();
  this.tableName = tableName;
  this.fieldName = nameMapper.getPropertyName(fieldName);
  this.columnName = nameMapper.getColumnName(fieldName);
  this.isAscending = isAscending;
 }

 /**
  * Instantiates a new order by field.
  *
  * @param nameMapper the name mapper
  * @param fieldName the field name
  * @param isAscending the is ascending
  */
 public OrderByField(INameMapper nameMapper, String fieldName, boolean isAscending) {
  this(nameMapper, "", fieldName, isAscending);
 }

 /**
  * Instantiates a new order by field.
  *
  * @param nameMapper the name mapper
  * @param fieldNameAndAscending the field name and ascending
  */
 public OrderByField(INameMapper nameMapper, String fieldNameAndAscending) {
  if (StringUtils.contains(fieldNameAndAscending, ".")) {
   this.tableName = StringUtils.substringBefore(fieldNameAndAscending, ".");
   fieldNameAndAscending = StringUtils.substringAfter(fieldNameAndAscending, ".");
  }
  String[] parts = StringUtils.split(fieldNameAndAscending, " ");
  if (parts == null) {
   return;
  }
  if (parts.length > 0) {
   fieldName = nameMapper.getPropertyName(parts[0]);
   this.columnName = nameMapper.getColumnName(fieldName);
  }
  if (parts.length > 1 && StringUtils.equalsIgnoreCase("DESC", parts[1])) {
   this.isAscending = false;
  }
 }

 /**
  * Gets the field name.
  *
  * @return the field name
  */
 public String getFieldName() {
  return fieldName;
 }

 /**
  * Sets the field name.
  *
  * @param fieldName the new field name
  */
 public void setFieldName(String fieldName) {
  this.fieldName = fieldName;
 }

 /**
  * Gets the column name.
  *
  * @return the column name
  */
 public String getColumnName() {
  return columnName;
 }

 /**
  * Sets the column name.
  *
  * @param columnName the new column name
  */
 public void setColumnName(String columnName) {
  this.columnName = columnName;
 }

 /**
  * Checks if is ascending.
  *
  * @return true, if is ascending
  */
 public boolean isAscending() {
  return isAscending;
 }

 /**
  * Sets the ascending.
  *
  * @param isAscending the new ascending
  */
 public void setAscending(boolean isAscending) {
  this.isAscending = isAscending;
 }

 /**
  * Gets the name mapper.
  *
  * @return the name mapper
  */
 public INameMapper getNameMapper() {
  return nameMapper;
 }

 /**
  * Sets the name mapper.
  *
  * @param nameMapper the new name mapper
  */
 public void setNameMapper(INameMapper nameMapper) {
  this.nameMapper = nameMapper;
 }

 /**
  * Gets the table name.
  *
  * @return the table name
  */
 public String getTableName() {
  return tableName;
 }

 /**
  * Sets the table name.
  *
  * @param tableName the new table name
  */
 public void setTableName(String tableName) {
  this.tableName = tableName;
 }

 /**
  * To sql.
  *
  * @return the string
  */
 public String toSql() {
  StringBuilder sb = new StringBuilder();
  if (StringUtils.isNotBlank(this.getColumnName())) {
   if (StringUtils.isNotBlank(this.getTableName())) {
    sb.append(this.getTableName()).append(".");
   }
   sb.append(this.getColumnName());
   if (!this.isAscending) {
    sb.append(" DESC");
   }
  }
  return sb.toString();
 }

 /**
  * To string.
  *
  * @return the string
  */
 @Override
 public String toString() {
  return "OrderByField [fieldName=" + fieldName + ", columnName=" + columnName + ", isAscending=" + isAscending + "]";
 }

 /**
  * Builds the.
  *
  * @param nameMapper the name mapper
  * @param orderByCause the order by cause
  * @return the list
  */
 public static List<OrderByField> build(INameMapper nameMapper, String orderByCause) {
  List<OrderByField> orderByFields = new ArrayList<OrderByField>();
  String[] parts = StringUtils.split(orderByCause, ",");
  if (parts != null && parts.length > 0) {
   for (String part : parts) {
    OrderByField orderByField = new OrderByField(nameMapper, part);
    if (StringUtils.isNotBlank(orderByField.getFieldName())) {
     orderByFields.add(orderByField);
    }
   }
  }
  return orderByFields;
 }

 /**
  * Builds the order by sql.
  *
  * @param nameMapper the name mapper
  * @param orderByCause the order by cause
  * @return the string
  */
 public static String buildOrderBySql(INameMapper nameMapper, String orderByCause) {
  return buildOrderBySql(nameMapper, orderByCause, "");
 }

 /**
  * Builds the order by sql.
  *
  * @param nameMapper the name mapper
  * @param orderByCause the order by cause
  * @param tableName the table name
  * @return the string
  */
 public static String buildOrderBySql(INameMapper nameMapper, String orderByCause, String tableName) {
  StringBuilder sb = new StringBuilder();
  List<OrderByField> orderByFields = build(nameMapper, orderByCause);
  for (int i = 0; i < orderByFields.size(); i++) {
   OrderByField byField = orderByFields.get(i);
   if (StringUtils.isBlank(byField.getTableName())) {
    byField.setTableName(tableName);
   }
   if (i > 0) {
    sb.append(",");
   }
   sb.append(byField.toSql());
  }
  return sb.toString();
 }

 /**
  * Builds the order by sql.
  *
  * @param nameMapper the name mapper
  * @param orderByCause the order by cause
  * @param isAscending the is ascending
  * @return the string
  */
 public static String buildOrderBySql(INameMapper nameMapper, String orderByCause, boolean isAscending) {
  return buildOrderBySql(nameMapper, orderByCause, "", isAscending);
 }

 /**
  * Builds the order by sql.
  *
  * @param nameMapper the name mapper
  * @param orderByCause the order by cause
  * @param tableName the table name
  * @param isAscending the is ascending
  * @return the string
  */
 public static String buildOrderBySql(INameMapper nameMapper, String orderByCause, String tableName, boolean isAscending) {
  StringBuilder sb = new StringBuilder();
  List<OrderByField> orderByFields = build(nameMapper, orderByCause);
  for (int i = 0; i < orderByFields.size(); i++) {
   OrderByField byField = orderByFields.get(i);
   if (StringUtils.isBlank(byField.getTableName())) {
    byField.setTableName(tableName);
   }
   if (i <= orderByFields.size() - 1) {
    byField.setAscending(isAscending);
   }
   if (i > 0) {
    sb.append(",");
   }
   sb.append(byField.toSql());
  }
  return sb.toString();
 }

 /**
  * Builds the order by sql.
  *
  * @param orderByFields the order by fields
  * @return the string
  */
 public static String buildOrderBySql(List<OrderByField> orderByFields) {
  StringBuilder sb = new StringBuilder();
  for (int i = 0; i < orderByFields.size(); i++) {
   OrderByField byField = orderByFields.get(i);
   if (i > 0) {
    sb.append(",");
   }
   sb.append(byField.toSql());
  }
  return sb.toString();
 }

}
