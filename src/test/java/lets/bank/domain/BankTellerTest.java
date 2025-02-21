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

import static lets.bank.domain.BankTellerTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import lets.bank.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BankTellerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankTeller.class);
        BankTeller bankTeller1 = getBankTellerSample1();
        BankTeller bankTeller2 = new BankTeller();
        assertThat(bankTeller1).isNotEqualTo(bankTeller2);

        bankTeller2.setId(bankTeller1.getId());
        assertThat(bankTeller1).isEqualTo(bankTeller2);

        bankTeller2 = getBankTellerSample2();
        assertThat(bankTeller1).isNotEqualTo(bankTeller2);
    }
}
