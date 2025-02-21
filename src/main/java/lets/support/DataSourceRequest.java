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

import com.fasterxml.jackson.annotation.JsonAnySetter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * DataSourceRequest.
 */
public class DataSourceRequest {

 /** The no limit. */
 public static int NO_LIMIT = -1;

 /** The no offset. */
 public static int NO_OFFSET = -1;

 /** The page. */
 private int page;

 /** The page size. */
 private int pageSize;

 /** The take. */
 private int take;

 /** The skip. */
 private int skip;

 /** The sort. */
 private List<SortDescriptor> sort;

 /** The group. */
 private List<GroupDescriptor> group;

 /** The aggregate. */
 private List<AggregateDescriptor> aggregate;

 /** The data. */
 private HashMap<String, Object> data;

 /** The filter. */
 private FilterDescriptor filter;

 // jsonp support.
 /** The callback. */
 private String callback;

 /**
  * Instantiates a new data source request.
  */
 public DataSourceRequest() {
  filter = new FilterDescriptor();
  data = new HashMap<String, Object>();
 }

 /**
  * Gets the data.
  * 
  * @return the data
  */
 public HashMap<String, Object> getData() {
  return data;
 }

 /**
  * Handle unknown.
  * 
  * @param key   the key
  * @param value the value
  */
 @JsonAnySetter
 public void handleUnknown(String key, Object value) {
  data.put(key, value);
 }

 /**
  * Gets the page.
  * 
  * @return the page
  */
 public int getPage() {
  return page;
 }

 /**
  * Sets the page.
  * 
  * @param page the new page
  */
 public void setPage(int page) {
  this.page = page;
 }

 /**
  * Gets the page size.
  * 
  * @return the page size
  */
 public int getPageSize() {
  return pageSize;
 }

 /**
  * Sets the page size.
  * 
  * @param pageSize the new page size
  */
 public void setPageSize(int pageSize) {
  this.pageSize = pageSize;
 }

 /**
  * Gets the take.
  * 
  * @return the take
  */
 public int getTake() {
  return take;
 }

 /**
  * Sets the take.
  * 
  * @param take the new take
  */
 public void setTake(int take) {
  this.take = take;
 }

 /**
  * Gets the skip.
  * 
  * @return the skip
  */
 public int getSkip() {
  return skip;
 }

 /**
  * Sets the skip.
  * 
  * @param skip the new skip
  */
 public void setSkip(int skip) {
  this.skip = skip;
 }

 /**
  * Gets the sort.
  * 
  * @return the sort
  */
 public List<SortDescriptor> getSort() {
  return sort;
 }

 /**
  * Sets the sort.
  * 
  * @param sort the new sort
  */
 public void setSort(List<SortDescriptor> sort) {
  this.sort = sort;
 }

 /**
  * Gets the filter.
  * 
  * @return the filter
  */
 public FilterDescriptor getFilter() {
  return filter;
 }

 /**
  * Sets the filter.
  * 
  * @param filter the new filter
  */
 public void setFilter(FilterDescriptor filter) {
  this.filter = filter;
 }

 /**
  * Gets the callback.
  * 
  * @return the callback
  */
 public String getCallback() {
  return callback;
 }

 /**
  * Sets the callback.
  * 
  * @param callback the new callback
  */
 public void setCallback(String callback) {
  this.callback = callback;
 }

 /**
  * Sort descriptors.
  * 
  * @return the list
  */
 public List<SortDescriptor> sortDescriptors() {
  List<SortDescriptor> sort = new ArrayList<SortDescriptor>();
  List<GroupDescriptor> groups = getGroup();
  List<SortDescriptor> sorts = getSort();
  if (groups != null) {
   sort.addAll(groups);
  }
  if (sorts != null) {
   sort.addAll(sorts);
  }
  return sort;
 }

 /**
  * Gets the group.
  * 
  * @return the group
  */
 public List<GroupDescriptor> getGroup() {
  return group;
 }

 /**
  * Sets the group.
  * 
  * @param group the new group
  */
 public void setGroup(List<GroupDescriptor> group) {
  this.group = group;
 }

 /**
  * Gets the aggregate.
  * 
  * @return the aggregate
  */
 public List<AggregateDescriptor> getAggregate() {
  return aggregate;
 }

 /**
  * Sets the aggregate.
  * 
  * @param aggregate the new aggregate
  */
 public void setAggregate(List<AggregateDescriptor> aggregate) {
  this.aggregate = aggregate;
 }

 public DataSourceRequest and(String field, FilterOperatorEnum operatorEnum, Object value) {
  if (this.filter == null) {
   this.filter = new FilterDescriptor();
  }
  FilterDescriptor fieldFilter = new FilterDescriptor();
  fieldFilter.setLogic("and");
  fieldFilter.setField(field);
  fieldFilter.setOperator(operatorEnum.getValue());
  fieldFilter.setValue(value);
  this.filter.getFilters().add(fieldFilter);
  return this;
 }

 public DataSourceRequest sort(String field, boolean isAsc) {
  if (this.sort == null) {
   this.sort = new ArrayList<SortDescriptor>();
  }
  SortDescriptor sortDesc = new SortDescriptor();
  sortDesc.setField(field);
  sortDesc.setDir(isAsc ? "ASC" : "DESC");
  return this;
 }

 public DataSourceRequest asc(String field) {
  return sort(field, true);
 }

 public DataSourceRequest desc(String field) {
  return sort(field, false);
 }

 public DataSourceRequest limit(int limit) {
  resetPageAndSize();
  this.setTake(limit);
  return this;
 }

 public DataSourceRequest offset(int offset) {
  resetPageAndSize();
  this.setSkip(offset);
  return this;
 }

 public DataSourceRequest pageAndSize(int page, int pageSize) {
  this.setPage(page);
  this.setPageSize(pageSize);
  return this;
 }

 public void resetPageAndSize() {
  this.page = 0;
  this.pageSize = 0;
 }
}
