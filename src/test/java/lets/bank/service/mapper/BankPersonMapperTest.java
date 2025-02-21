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
package lets.bank.service.mapper;

import static lets.bank.domain.BankPersonAsserts.*;
import static lets.bank.domain.BankPersonTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BankPersonMapperTest {

    private BankPersonMapper bankPersonMapper;

    @BeforeEach
    void setUp() {
        bankPersonMapper = new BankPersonMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getBankPersonSample1();
        var actual = bankPersonMapper.toEntity(bankPersonMapper.toDto(expected));
        assertBankPersonAllPropertiesEquals(expected, actual);
    }
}
