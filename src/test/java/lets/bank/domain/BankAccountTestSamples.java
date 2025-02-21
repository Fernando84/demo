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

public class BankAccountTestSamples {

    public static BankAccount getBankAccountSample1() {
        return new BankAccount().id("id1").accountNumber("accountNumber1").createdBy("createdBy1").lastModifiedBy("lastModifiedBy1");
    }

    public static BankAccount getBankAccountSample2() {
        return new BankAccount().id("id2").accountNumber("accountNumber2").createdBy("createdBy2").lastModifiedBy("lastModifiedBy2");
    }

    public static BankAccount getBankAccountRandomSampleGenerator() {
        return new BankAccount()
            .id(UUID.randomUUID().toString())
            .accountNumber(UUID.randomUUID().toString())
            .createdBy(UUID.randomUUID().toString())
            .lastModifiedBy(UUID.randomUUID().toString());
    }
}
