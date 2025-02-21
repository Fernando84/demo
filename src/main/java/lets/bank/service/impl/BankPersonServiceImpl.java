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
import lets.bank.domain.BankPerson;
import lets.bank.repository.BankPersonRepository;
import lets.bank.service.BankPersonService;
import lets.bank.service.dto.BankPersonDTO;
import lets.bank.service.mapper.BankPersonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link lets.bank.domain.BankPerson}.
 */
@Service
@Transactional
public class BankPersonServiceImpl implements BankPersonService {

    private static final Logger LOG = LoggerFactory.getLogger(BankPersonServiceImpl.class);

    private final BankPersonRepository bankPersonRepository;

    private final BankPersonMapper bankPersonMapper;

    public BankPersonServiceImpl(BankPersonRepository bankPersonRepository, BankPersonMapper bankPersonMapper) {
        this.bankPersonRepository = bankPersonRepository;
        this.bankPersonMapper = bankPersonMapper;
    }

    @Override
    public BankPersonDTO save(BankPersonDTO bankPersonDTO) {
        LOG.debug("Request to save BankPerson : {}", bankPersonDTO);
        BankPerson bankPerson = bankPersonMapper.toEntity(bankPersonDTO);
        bankPerson = bankPersonRepository.save(bankPerson);
        return bankPersonMapper.toDto(bankPerson);
    }

    @Override
    public BankPersonDTO update(BankPersonDTO bankPersonDTO) {
        LOG.debug("Request to update BankPerson : {}", bankPersonDTO);
        BankPerson bankPerson = bankPersonMapper.toEntity(bankPersonDTO);
        bankPerson.setIsPersisted();
        bankPerson = bankPersonRepository.save(bankPerson);
        return bankPersonMapper.toDto(bankPerson);
    }

    @Override
    public Optional<BankPersonDTO> partialUpdate(BankPersonDTO bankPersonDTO) {
        LOG.debug("Request to partially update BankPerson : {}", bankPersonDTO);

        return bankPersonRepository
            .findById(bankPersonDTO.getId())
            .map(existingBankPerson -> {
                bankPersonMapper.partialUpdate(existingBankPerson, bankPersonDTO);

                return existingBankPerson;
            })
            .map(bankPersonRepository::save)
            .map(bankPersonMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BankPersonDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all BankPeople");
        return bankPersonRepository.findAll(pageable).map(bankPersonMapper::toDto);
    }

    /**
     *  Get all the bankPeople where BankCustomer is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<BankPersonDTO> findAllWhereBankCustomerIsNull() {
        LOG.debug("Request to get all bankPeople where BankCustomer is null");
        return StreamSupport.stream(bankPersonRepository.findAll().spliterator(), false)
            .filter(bankPerson -> bankPerson.getBankCustomer() == null)
            .map(bankPersonMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the bankPeople where BankAccount is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<BankPersonDTO> findAllWhereBankAccountIsNull() {
        LOG.debug("Request to get all bankPeople where BankAccount is null");
        return StreamSupport.stream(bankPersonRepository.findAll().spliterator(), false)
            .filter(bankPerson -> bankPerson.getBankAccount() == null)
            .map(bankPersonMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BankPersonDTO> findOne(String id) {
        LOG.debug("Request to get BankPerson : {}", id);
        return bankPersonRepository.findById(id).map(bankPersonMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete BankPerson : {}", id);
        bankPersonRepository.deleteById(id);
    }
}
