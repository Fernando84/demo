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

/**
 * Creating a new account
 * <p>
 * Both CUSTOMER and PERSON can go to the bank asking TELLER to create a new
 * account. Teller can create a new account using his/her citizen id, Thai name,
 * and English name with or without a deposit. System generates unique “account
 * number” with 7 numeric characters – for example “7777777” that will be use as
 * a reference for deposit/money transfer
 */
public class CreateAccountDTO {
  private String citizenId;

  private String thaiName;

  private String englishName;

  public String getCitizenId() {
    return citizenId;
  }

  public void setCitizenId(String citizenId) {
    this.citizenId = citizenId;
  }

  public String getThaiName() {
    return thaiName;
  }

  public void setThaiName(String thaiName) {
    this.thaiName = thaiName;
  }

  public String getEnglishName() {
    return englishName;
  }

  public void setEnglishName(String englishName) {
    this.englishName = englishName;
  }

}
