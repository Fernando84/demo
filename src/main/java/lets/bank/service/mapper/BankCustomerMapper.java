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

import lets.bank.domain.BankCustomer;
import lets.bank.domain.BankPerson;
import lets.bank.service.dto.BankCustomerDTO;
import lets.bank.service.dto.BankPersonDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BankCustomer} and its DTO {@link BankCustomerDTO}.
 */
@Mapper(componentModel = "spring")
public interface BankCustomerMapper extends EntityMapper<BankCustomerDTO, BankCustomer> {
    @Mapping(target = "person", source = "person", qualifiedByName = "bankPersonId")
    BankCustomerDTO toDto(BankCustomer s);

    @Named("bankPersonId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BankPersonDTO toDtoBankPersonId(BankPerson bankPerson);
}
