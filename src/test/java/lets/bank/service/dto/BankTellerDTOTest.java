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

class BankTellerDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankTellerDTO.class);
        BankTellerDTO bankTellerDTO1 = new BankTellerDTO();
        bankTellerDTO1.setId("id1");
        BankTellerDTO bankTellerDTO2 = new BankTellerDTO();
        assertThat(bankTellerDTO1).isNotEqualTo(bankTellerDTO2);
        bankTellerDTO2.setId(bankTellerDTO1.getId());
        assertThat(bankTellerDTO1).isEqualTo(bankTellerDTO2);
        bankTellerDTO2.setId("id2");
        assertThat(bankTellerDTO1).isNotEqualTo(bankTellerDTO2);
        bankTellerDTO1.setId(null);
        assertThat(bankTellerDTO1).isNotEqualTo(bankTellerDTO2);
    }
}
