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
import static lets.bank.domain.BankCustomerTestSamples.*;
import static lets.bank.domain.BankPersonTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import lets.bank.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BankPersonTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankPerson.class);
        BankPerson bankPerson1 = getBankPersonSample1();
        BankPerson bankPerson2 = new BankPerson();
        assertThat(bankPerson1).isNotEqualTo(bankPerson2);

        bankPerson2.setId(bankPerson1.getId());
        assertThat(bankPerson1).isEqualTo(bankPerson2);

        bankPerson2 = getBankPersonSample2();
        assertThat(bankPerson1).isNotEqualTo(bankPerson2);
    }

    @Test
    void bankCustomerTest() {
        BankPerson bankPerson = getBankPersonRandomSampleGenerator();
        BankCustomer bankCustomerBack = getBankCustomerRandomSampleGenerator();

        bankPerson.setBankCustomer(bankCustomerBack);
        assertThat(bankPerson.getBankCustomer()).isEqualTo(bankCustomerBack);
        assertThat(bankCustomerBack.getPerson()).isEqualTo(bankPerson);

        bankPerson.bankCustomer(null);
        assertThat(bankPerson.getBankCustomer()).isNull();
        assertThat(bankCustomerBack.getPerson()).isNull();
    }

    @Test
    void bankAccountTest() {
        BankPerson bankPerson = getBankPersonRandomSampleGenerator();
        BankAccount bankAccountBack = getBankAccountRandomSampleGenerator();

        bankPerson.setBankAccount(bankAccountBack);
        assertThat(bankPerson.getBankAccount()).isEqualTo(bankAccountBack);
        assertThat(bankAccountBack.getPerson()).isEqualTo(bankPerson);

        bankPerson.bankAccount(null);
        assertThat(bankPerson.getBankAccount()).isNull();
        assertThat(bankAccountBack.getPerson()).isNull();
    }
}
