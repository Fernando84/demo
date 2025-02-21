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

import static lets.bank.domain.BankCustomerTestSamples.*;
import static lets.bank.domain.BankPersonTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import lets.bank.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BankCustomerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankCustomer.class);
        BankCustomer bankCustomer1 = getBankCustomerSample1();
        BankCustomer bankCustomer2 = new BankCustomer();
        assertThat(bankCustomer1).isNotEqualTo(bankCustomer2);

        bankCustomer2.setId(bankCustomer1.getId());
        assertThat(bankCustomer1).isEqualTo(bankCustomer2);

        bankCustomer2 = getBankCustomerSample2();
        assertThat(bankCustomer1).isNotEqualTo(bankCustomer2);
    }

    @Test
    void personTest() {
        BankCustomer bankCustomer = getBankCustomerRandomSampleGenerator();
        BankPerson bankPersonBack = getBankPersonRandomSampleGenerator();

        bankCustomer.setPerson(bankPersonBack);
        assertThat(bankCustomer.getPerson()).isEqualTo(bankPersonBack);

        bankCustomer.person(null);
        assertThat(bankCustomer.getPerson()).isNull();
    }
}
