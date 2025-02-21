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
package lets.bank.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import lets.bank.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BankCustomerDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankCustomerDTO.class);
        BankCustomerDTO bankCustomerDTO1 = new BankCustomerDTO();
        bankCustomerDTO1.setId("id1");
        BankCustomerDTO bankCustomerDTO2 = new BankCustomerDTO();
        assertThat(bankCustomerDTO1).isNotEqualTo(bankCustomerDTO2);
        bankCustomerDTO2.setId(bankCustomerDTO1.getId());
        assertThat(bankCustomerDTO1).isEqualTo(bankCustomerDTO2);
        bankCustomerDTO2.setId("id2");
        assertThat(bankCustomerDTO1).isNotEqualTo(bankCustomerDTO2);
        bankCustomerDTO1.setId(null);
        assertThat(bankCustomerDTO1).isNotEqualTo(bankCustomerDTO2);
    }
}
