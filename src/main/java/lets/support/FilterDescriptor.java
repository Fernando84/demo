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

/**
 * The Class FilterDescriptor.
 */
public class FilterDescriptor {
 
 /** The logic. */
 private String logic;
 
 /** The filters. */
 private List<FilterDescriptor> filters;
 
 /** The field. */
 private String field;
 
 /** The value. */
 private Object value;
 
 /** The operator. */
 private String operator;
 
 /** The ignore case. */
 private boolean ignoreCase = true;

 /**
  * Instantiates a new filter descriptor.
  */
 public FilterDescriptor() {
  filters = new ArrayList<FilterDescriptor>();
 }

 /**
  * Gets the field.
  *
  * @return the field
  */
 public String getField() {
  return field;
 }

 /**
  * Sets the field.
  *
  * @param field the new field
  */
 public void setField(String field) {
  this.field = field;
 }

 /**
  * Gets the value.
  *
  * @return the value
  */
 public Object getValue() {
  return value;
 }

 /**
  * Sets the value.
  *
  * @param value the new value
  */
 public void setValue(Object value) {
  this.value = value;
 }

 /**
  * Gets the operator.
  *
  * @return the operator
  */
 public String getOperator() {
  return operator;
 }

 /**
  * Sets the operator.
  *
  * @param operator the new operator
  */
 public void setOperator(String operator) {
  this.operator = operator;
 }

 /**
  * Gets the logic.
  *
  * @return the logic
  */
 public String getLogic() {
  return logic;
 }

 /**
  * Sets the logic.
  *
  * @param logic the new logic
  */
 public void setLogic(String logic) {
  this.logic = logic;
 }

 /**
  * Checks if is ignore case.
  *
  * @return true, if is ignore case
  */
 public boolean isIgnoreCase() {
  return ignoreCase;
 }

 /**
  * Sets the ignore case.
  *
  * @param ignoreCase the new ignore case
  */
 public void setIgnoreCase(boolean ignoreCase) {
  this.ignoreCase = ignoreCase;
 }

 /**
  * Gets the filters.
  *
  * @return the filters
  */
 public List<FilterDescriptor> getFilters() {
  return filters;
 }
}