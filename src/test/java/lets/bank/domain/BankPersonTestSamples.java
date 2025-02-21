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
package lets.bank.domain;

import java.util.UUID;

public class BankPersonTestSamples {

    public static BankPerson getBankPersonSample1() {
        return new BankPerson()
            .id("id1")
            .citizenId("citizenId1")
            .thaiName("thaiName1")
            .englishName("englishName1")
            .createdBy("createdBy1")
            .lastModifiedBy("lastModifiedBy1");
    }

    public static BankPerson getBankPersonSample2() {
        return new BankPerson()
            .id("id2")
            .citizenId("citizenId2")
            .thaiName("thaiName2")
            .englishName("englishName2")
            .createdBy("createdBy2")
            .lastModifiedBy("lastModifiedBy2");
    }

    public static BankPerson getBankPersonRandomSampleGenerator() {
        return new BankPerson()
            .id(UUID.randomUUID().toString())
            .citizenId(UUID.randomUUID().toString())
            .thaiName(UUID.randomUUID().toString())
            .englishName(UUID.randomUUID().toString())
            .createdBy(UUID.randomUUID().toString())
            .lastModifiedBy(UUID.randomUUID().toString());
    }
}
