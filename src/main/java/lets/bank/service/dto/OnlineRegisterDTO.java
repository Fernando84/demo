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
 * Online Registration
 * <p>
 * Only new PERSON can register by input both email and password. Then they need
 * to fill-in personal information including citizen id, Thai name, and English
 * name and PIN 6 digits number.
 */
public class OnlineRegisterDTO {
  private String citizenId;

  private String thaiName;

  private String englishName;
  private String pinCode;

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

  public String getPinCode() {
    return pinCode;
  }

  public void setPinCode(String pinCode) {
    this.pinCode = pinCode;
  }

}
