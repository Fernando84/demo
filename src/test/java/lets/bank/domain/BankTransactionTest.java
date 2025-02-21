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

import static lets.bank.domain.BankAccountTestSamples.*;
import static lets.bank.domain.BankTransactionTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import lets.bank.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BankTransactionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankTransaction.class);
        BankTransaction bankTransaction1 = getBankTransactionSample1();
        BankTransaction bankTransaction2 = new BankTransaction();
        assertThat(bankTransaction1).isNotEqualTo(bankTransaction2);

        bankTransaction2.setId(bankTransaction1.getId());
        assertThat(bankTransaction1).isEqualTo(bankTransaction2);

        bankTransaction2 = getBankTransactionSample2();
        assertThat(bankTransaction1).isNotEqualTo(bankTransaction2);
    }

    @Test
    void accountTest() {
        BankTransaction bankTransaction = getBankTransactionRandomSampleGenerator();
        BankAccount bankAccountBack = getBankAccountRandomSampleGenerator();

        bankTransaction.setAccount(bankAccountBack);
        assertThat(bankTransaction.getAccount()).isEqualTo(bankAccountBack);

        bankTransaction.account(null);
        assertThat(bankTransaction.getAccount()).isNull();
    }
}
