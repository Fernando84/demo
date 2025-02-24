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
package lets.bank.service.dto;

import java.util.Date;

/**
 * 5.Bank Statement
 * <p>
 * Only CUSTOMER can ask for his/her bank statement of a specific month after
 * login to the system with PIN confirmation. The list of transaction must be
 * displayed from past to present.
 * 
 */
public class BankStatementListDTO {
  private String pin;
  private int pageSize = 10;
  private int pageNum = 1;
  private Date dateGE;
  private Date dateLE;

  public String getPin() {
    return pin;
  }

  public void setPin(String pin) {
    this.pin = pin;
  }

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public int getPageNum() {
    return pageNum;
  }

  public void setPageNum(int pageNum) {
    this.pageNum = pageNum;
  }

  public Date getDateGE() {
    return dateGE;
  }

  public void setDateGE(Date dateGE) {
    this.dateGE = dateGE;
  }

  public Date getDateLE() {
    return dateLE;
  }

  public void setDateLE(Date dateLE) {
    this.dateLE = dateLE;
  }

}
