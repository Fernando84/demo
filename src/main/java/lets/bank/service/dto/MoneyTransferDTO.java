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

import java.math.BigDecimal;

/**
 * Money Transfer<br>
 * Only CUSTOMER can transfer money from his/her own account to any other
 * existing account after login to system with PIN confirmation.
 * 
 */
public class MoneyTransferDTO {
  private String accountId;
  private BigDecimal amount;
  private String targetAccountId;
  private String pin;

  public String getAccountId() {
    return accountId;
  }

  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public String getTargetAccountId() {
    return targetAccountId;
  }

  public void setTargetAccountId(String targetAccountId) {
    this.targetAccountId = targetAccountId;
  }

  public String getPin() {
    return pin;
  }

  public void setPin(String pin) {
    this.pin = pin;
  }

}
