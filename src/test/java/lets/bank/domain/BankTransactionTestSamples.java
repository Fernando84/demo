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

public class BankTransactionTestSamples {

    public static BankTransaction getBankTransactionSample1() {
        return new BankTransaction()
            .id("id1")
            .code("code1")
            .channel("channel1")
            .targetAccountId("targetAccountId1")
            .remark("remark1")
            .createdBy("createdBy1")
            .lastModifiedBy("lastModifiedBy1");
    }

    public static BankTransaction getBankTransactionSample2() {
        return new BankTransaction()
            .id("id2")
            .code("code2")
            .channel("channel2")
            .targetAccountId("targetAccountId2")
            .remark("remark2")
            .createdBy("createdBy2")
            .lastModifiedBy("lastModifiedBy2");
    }

    public static BankTransaction getBankTransactionRandomSampleGenerator() {
        return new BankTransaction()
            .id(UUID.randomUUID().toString())
            .code(UUID.randomUUID().toString())
            .channel(UUID.randomUUID().toString())
            .targetAccountId(UUID.randomUUID().toString())
            .remark(UUID.randomUUID().toString())
            .createdBy(UUID.randomUUID().toString())
            .lastModifiedBy(UUID.randomUUID().toString());
    }
}
