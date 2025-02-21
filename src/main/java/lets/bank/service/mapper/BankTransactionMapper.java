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

import lets.bank.domain.BankAccount;
import lets.bank.domain.BankTransaction;
import lets.bank.service.dto.BankAccountDTO;
import lets.bank.service.dto.BankTransactionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BankTransaction} and its DTO {@link BankTransactionDTO}.
 */
@Mapper(componentModel = "spring")
public interface BankTransactionMapper extends EntityMapper<BankTransactionDTO, BankTransaction> {
    @Mapping(target = "account", source = "account", qualifiedByName = "bankAccountId")
    BankTransactionDTO toDto(BankTransaction s);

    @Named("bankAccountId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BankAccountDTO toDtoBankAccountId(BankAccount bankAccount);
}
