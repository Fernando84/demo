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
import static lets.bank.domain.BankPersonTestSamples.*;
import static lets.bank.domain.BankTransactionTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import lets.bank.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BankAccountTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankAccount.class);
        BankAccount bankAccount1 = getBankAccountSample1();
        BankAccount bankAccount2 = new BankAccount();
        assertThat(bankAccount1).isNotEqualTo(bankAccount2);

        bankAccount2.setId(bankAccount1.getId());
        assertThat(bankAccount1).isEqualTo(bankAccount2);

        bankAccount2 = getBankAccountSample2();
        assertThat(bankAccount1).isNotEqualTo(bankAccount2);
    }

    @Test
    void personTest() {
        BankAccount bankAccount = getBankAccountRandomSampleGenerator();
        BankPerson bankPersonBack = getBankPersonRandomSampleGenerator();

        bankAccount.setPerson(bankPersonBack);
        assertThat(bankAccount.getPerson()).isEqualTo(bankPersonBack);

        bankAccount.person(null);
        assertThat(bankAccount.getPerson()).isNull();
    }

    @Test
    void bankTransactionTest() {
        BankAccount bankAccount = getBankAccountRandomSampleGenerator();
        BankTransaction bankTransactionBack = getBankTransactionRandomSampleGenerator();

        bankAccount.setBankTransaction(bankTransactionBack);
        assertThat(bankAccount.getBankTransaction()).isEqualTo(bankTransactionBack);
        assertThat(bankTransactionBack.getAccount()).isEqualTo(bankAccount);

        bankAccount.bankTransaction(null);
        assertThat(bankAccount.getBankTransaction()).isNull();
        assertThat(bankTransactionBack.getAccount()).isNull();
    }
}
