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
 * The Class GroupDescriptor.
 */
public class GroupDescriptor extends SortDescriptor {
 
 /** The aggregates. */
 private List<AggregateDescriptor> aggregates;

 /**
  * Instantiates a new group descriptor.
  */
 public GroupDescriptor() {
  aggregates = new ArrayList<AggregateDescriptor>();
 }

 /**
  * Gets the aggregates.
  *
  * @return the aggregates
  */
 public List<AggregateDescriptor> getAggregates() {
  return aggregates;
 }
}
