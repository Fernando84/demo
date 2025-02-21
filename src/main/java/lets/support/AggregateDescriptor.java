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
 * The Class AggregateDescriptor.
 */
public class AggregateDescriptor {
 
 /** The field. */
 private String field;
 
 /** The aggregate. */
 private String aggregate;

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
  * Gets the aggregate.
  *
  * @return the aggregate
  */
 public String getAggregate() {
  return aggregate;
 }

 /**
  * Sets the aggregate.
  *
  * @param aggregate the new aggregate
  */
 public void setAggregate(String aggregate) {
  this.aggregate = aggregate;
 }
}