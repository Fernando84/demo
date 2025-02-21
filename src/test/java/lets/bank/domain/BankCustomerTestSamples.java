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

public class BankCustomerTestSamples {

    public static BankCustomer getBankCustomerSample1() {
        return new BankCustomer().id("id1").email("email1").pinCode("pinCode1").createdBy("createdBy1").lastModifiedBy("lastModifiedBy1");
    }

    public static BankCustomer getBankCustomerSample2() {
        return new BankCustomer().id("id2").email("email2").pinCode("pinCode2").createdBy("createdBy2").lastModifiedBy("lastModifiedBy2");
    }

    public static BankCustomer getBankCustomerRandomSampleGenerator() {
        return new BankCustomer()
            .id(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .pinCode(UUID.randomUUID().toString())
            .createdBy(UUID.randomUUID().toString())
            .lastModifiedBy(UUID.randomUUID().toString());
    }
}
