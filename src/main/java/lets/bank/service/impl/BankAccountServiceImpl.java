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
package lets.bank.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lets.bank.domain.BankAccount;
import lets.bank.repository.BankAccountRepository;
import lets.bank.service.BankAccountService;
import lets.bank.service.dto.BankAccountDTO;
import lets.bank.service.mapper.BankAccountMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link lets.bank.domain.BankAccount}.
 */
@Service
@Transactional
public class BankAccountServiceImpl implements BankAccountService {

    private static final Logger LOG = LoggerFactory.getLogger(BankAccountServiceImpl.class);

    private final BankAccountRepository bankAccountRepository;

    private final BankAccountMapper bankAccountMapper;

    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository, BankAccountMapper bankAccountMapper) {
        this.bankAccountRepository = bankAccountRepository;
        this.bankAccountMapper = bankAccountMapper;
    }

    @Override
    public BankAccountDTO save(BankAccountDTO bankAccountDTO) {
        LOG.debug("Request to save BankAccount : {}", bankAccountDTO);
        BankAccount bankAccount = bankAccountMapper.toEntity(bankAccountDTO);
        bankAccount = bankAccountRepository.save(bankAccount);
        return bankAccountMapper.toDto(bankAccount);
    }

    @Override
    public BankAccountDTO update(BankAccountDTO bankAccountDTO) {
        LOG.debug("Request to update BankAccount : {}", bankAccountDTO);
        BankAccount bankAccount = bankAccountMapper.toEntity(bankAccountDTO);
        bankAccount.setIsPersisted();
        bankAccount = bankAccountRepository.save(bankAccount);
        return bankAccountMapper.toDto(bankAccount);
    }

    @Override
    public Optional<BankAccountDTO> partialUpdate(BankAccountDTO bankAccountDTO) {
        LOG.debug("Request to partially update BankAccount : {}", bankAccountDTO);

        return bankAccountRepository
            .findById(bankAccountDTO.getId())
            .map(existingBankAccount -> {
                bankAccountMapper.partialUpdate(existingBankAccount, bankAccountDTO);

                return existingBankAccount;
            })
            .map(bankAccountRepository::save)
            .map(bankAccountMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BankAccountDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all BankAccounts");
        return bankAccountRepository.findAll(pageable).map(bankAccountMapper::toDto);
    }

    /**
     *  Get all the bankAccounts where BankTransaction is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<BankAccountDTO> findAllWhereBankTransactionIsNull() {
        LOG.debug("Request to get all bankAccounts where BankTransaction is null");
        return StreamSupport.stream(bankAccountRepository.findAll().spliterator(), false)
            .filter(bankAccount -> bankAccount.getBankTransaction() == null)
            .map(bankAccountMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BankAccountDTO> findOne(String id) {
        LOG.debug("Request to get BankAccount : {}", id);
        return bankAccountRepository.findById(id).map(bankAccountMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete BankAccount : {}", id);
        bankAccountRepository.deleteById(id);
    }
}
