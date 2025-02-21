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

public class BankTellerTestSamples {

    public static BankTeller getBankTellerSample1() {
        return new BankTeller()
            .id("id1")
            .employeeId("employeeId1")
            .thaiName("thaiName1")
            .englishName("englishName1")
            .createdBy("createdBy1")
            .lastModifiedBy("lastModifiedBy1");
    }

    public static BankTeller getBankTellerSample2() {
        return new BankTeller()
            .id("id2")
            .employeeId("employeeId2")
            .thaiName("thaiName2")
            .englishName("englishName2")
            .createdBy("createdBy2")
            .lastModifiedBy("lastModifiedBy2");
    }

    public static BankTeller getBankTellerRandomSampleGenerator() {
        return new BankTeller()
            .id(UUID.randomUUID().toString())
            .employeeId(UUID.randomUUID().toString())
            .thaiName(UUID.randomUUID().toString())
            .englishName(UUID.randomUUID().toString())
            .createdBy(UUID.randomUUID().toString())
            .lastModifiedBy(UUID.randomUUID().toString());
    }
}
