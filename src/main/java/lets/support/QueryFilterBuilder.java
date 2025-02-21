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

import java.util.Map;
import org.apache.commons.lang3.StringUtils;

/**
 * QueryFilterBuilder.
 * 
 * @author zhoupan.
 */
public class QueryFilterBuilder {

 /** The filter. */
 private QueryFilter filter = new QueryFilter();

 /**
  * Builds the.
  * 
  * @return the query filter
  */
 public QueryFilter build() {
  return filter;
 }

 /**
  * Params.
  * 
  * @param params the params
  * @return the query filter builder
  */
 public QueryFilterBuilder params(Map<String, Object> params) {
  filter.setParams(params);
  return this;
 }

 /**
  * Offset.
  * 
  * @param offset the offset
  * @return the query filter builder
  */
 public QueryFilterBuilder offset(int offset) {
  filter.setOffset(offset);
  return this;
 }

 /**
  * Limit.
  * 
  * @param limit the limit
  * @return the query filter builder
  */
 public QueryFilterBuilder limit(int limit) {
  filter.setLimit(limit);
  return this;
 }

 /**
  * Order by.
  * 
  * @param orderBy the order by
  * @return the query filter builder
  */
 public QueryFilterBuilder orderBy(String orderBy) {
  filter.setOrderBy(orderBy);
  return this;
 }

 /**
  * Put.
  *
  * @param key the key
  * @param value the value
  * @return the query filter builder
  */
 public QueryFilterBuilder put(String key, Object value) {
  LogSupport.debug("QueryFilterBuilder: put('{}','{}')", key, value);
  filter.getParams().put(key, value);
  return this;
 }

 /**
  * Put.
  *
  * @param key the key
  * @param operator the operator
  * @param value the value
  * @return the query filter builder
  */
 public QueryFilterBuilder put(String key, FilterOperatorEnum operator, Object value) {
  return this.put(key + operator.getValue().toUpperCase(), value);
 }

 /**
  * Put.
  *
  * @param key      the key
  * @param operator the operator
  * @return the query filter builder
  */
 public QueryFilterBuilder put(String key, FilterOperatorEnum operator) {
  return this.put(key + operator.getValue().toUpperCase(), operator.getValue());
 }

 /**
  * Removes the.
  *
  * @param key the key
  * @param operator the operator
  * @return the query filter builder
  */
 public QueryFilterBuilder remove(String key, FilterOperatorEnum operator) {
  return this.remove(key + operator.getValue().toUpperCase());
 }

 /**
  * Put all.
  *
  * @param params the params
  * @return the query filter builder
  */
 public QueryFilterBuilder putAll(Map<String, Object> params) {
  if (params == null) {
   return this;
  }
  filter.getParams().putAll(params);
  return this;
 }

 /**
  * Removes the.
  *
  * @param key the key
  * @return the query filter builder
  */
 public QueryFilterBuilder remove(String key) {
  filter.getParams().remove(key);
  return this;
 }

 /**
  * Sort and order.
  *
  * @param sort the sort
  * @param order the order
  * @return the query filter builder
  */
 public QueryFilterBuilder sortAndOrder(String sort, String order) {
  if (StringUtils.isBlank(sort)) {
   return this;
  }
  StringBuilder orderBy = new StringBuilder();
  orderBy.append(sort);
  if (StringUtils.equalsIgnoreCase("desc", order)) {
   orderBy.append(" desc");
  }
  filter.setOrderBy(orderBy.toString());
  return this;
 }

 /**
  * Sort and order.
  *
  * @param fieldName the field name
  * @param isAscending the is ascending
  * @return the query filter builder
  */
 public QueryFilterBuilder sortAndOrder(String fieldName, boolean isAscending) {
  return this.sortAndOrder(fieldName, isAscending ? "ASC" : "DESC");
 }
}
