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

import java.util.Optional;
import lets.bank.domain.BankCustomer;
import lets.bank.repository.BankCustomerRepository;
import lets.bank.service.BankCustomerService;
import lets.bank.service.dto.BankCustomerDTO;
import lets.bank.service.mapper.BankCustomerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link lets.bank.domain.BankCustomer}.
 */
@Service
@Transactional
public class BankCustomerServiceImpl implements BankCustomerService {

    private static final Logger LOG = LoggerFactory.getLogger(BankCustomerServiceImpl.class);

    private final BankCustomerRepository bankCustomerRepository;

    private final BankCustomerMapper bankCustomerMapper;

    public BankCustomerServiceImpl(BankCustomerRepository bankCustomerRepository, BankCustomerMapper bankCustomerMapper) {
        this.bankCustomerRepository = bankCustomerRepository;
        this.bankCustomerMapper = bankCustomerMapper;
    }

    @Override
    public BankCustomerDTO save(BankCustomerDTO bankCustomerDTO) {
        LOG.debug("Request to save BankCustomer : {}", bankCustomerDTO);
        BankCustomer bankCustomer = bankCustomerMapper.toEntity(bankCustomerDTO);
        bankCustomer = bankCustomerRepository.save(bankCustomer);
        return bankCustomerMapper.toDto(bankCustomer);
    }

    @Override
    public BankCustomerDTO update(BankCustomerDTO bankCustomerDTO) {
        LOG.debug("Request to update BankCustomer : {}", bankCustomerDTO);
        BankCustomer bankCustomer = bankCustomerMapper.toEntity(bankCustomerDTO);
        bankCustomer.setIsPersisted();
        bankCustomer = bankCustomerRepository.save(bankCustomer);
        return bankCustomerMapper.toDto(bankCustomer);
    }

    @Override
    public Optional<BankCustomerDTO> partialUpdate(BankCustomerDTO bankCustomerDTO) {
        LOG.debug("Request to partially update BankCustomer : {}", bankCustomerDTO);

        return bankCustomerRepository
            .findById(bankCustomerDTO.getId())
            .map(existingBankCustomer -> {
                bankCustomerMapper.partialUpdate(existingBankCustomer, bankCustomerDTO);

                return existingBankCustomer;
            })
            .map(bankCustomerRepository::save)
            .map(bankCustomerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BankCustomerDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all BankCustomers");
        return bankCustomerRepository.findAll(pageable).map(bankCustomerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BankCustomerDTO> findOne(String id) {
        LOG.debug("Request to get BankCustomer : {}", id);
        return bankCustomerRepository.findById(id).map(bankCustomerMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete BankCustomer : {}", id);
        bankCustomerRepository.deleteById(id);
    }
}
