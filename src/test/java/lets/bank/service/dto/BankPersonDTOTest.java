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

class BankPersonDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankPersonDTO.class);
        BankPersonDTO bankPersonDTO1 = new BankPersonDTO();
        bankPersonDTO1.setId("id1");
        BankPersonDTO bankPersonDTO2 = new BankPersonDTO();
        assertThat(bankPersonDTO1).isNotEqualTo(bankPersonDTO2);
        bankPersonDTO2.setId(bankPersonDTO1.getId());
        assertThat(bankPersonDTO1).isEqualTo(bankPersonDTO2);
        bankPersonDTO2.setId("id2");
        assertThat(bankPersonDTO1).isNotEqualTo(bankPersonDTO2);
        bankPersonDTO1.setId(null);
        assertThat(bankPersonDTO1).isNotEqualTo(bankPersonDTO2);
    }
}
