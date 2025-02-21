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

import java.util.List;
import java.util.Map;

/**
 * DataSourceResponse.
 */
public class DataSourceResponse {

 /** The total. */
 private long total;

 /** The data. */
 private List<?> data;

 /** The aggregates. */
 private Map<String, Object> aggregates;

 /**
  * Gets the total.
  *
  * @return the total
  */
 public long getTotal() {
  return total;
 }

 /**
  * Sets the total.
  *
  * @param total the total
  */
 public void setTotal(long total) {
  this.total = total;
 }

 /**
  * Gets the data.
  *
  * @return the data
  */
 public List<?> getData() {
  return data;
 }

 /**
  * Sets the data.
  *
  * @param data the data
  */
 public void setData(List<?> data) {
  this.data = data;
 }

 /**
  * Gets the aggregates.
  *
  * @return the aggregates
  */
 public Map<String, Object> getAggregates() {
  return aggregates;
 }

 /**
  * Sets the aggregates.
  *
  * @param aggregates the aggregates
  */
 public void setAggregates(Map<String, Object> aggregates) {
  this.aggregates = aggregates;
 }

}
