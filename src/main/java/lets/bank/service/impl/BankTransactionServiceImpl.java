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
import lets.bank.domain.BankTransaction;
import lets.bank.repository.BankTransactionRepository;
import lets.bank.service.BankTransactionService;
import lets.bank.service.dto.BankTransactionDTO;
import lets.bank.service.mapper.BankTransactionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link lets.bank.domain.BankTransaction}.
 */
@Service
@Transactional
public class BankTransactionServiceImpl implements BankTransactionService {

    private static final Logger LOG = LoggerFactory.getLogger(BankTransactionServiceImpl.class);

    private final BankTransactionRepository bankTransactionRepository;

    private final BankTransactionMapper bankTransactionMapper;

    public BankTransactionServiceImpl(BankTransactionRepository bankTransactionRepository, BankTransactionMapper bankTransactionMapper) {
        this.bankTransactionRepository = bankTransactionRepository;
        this.bankTransactionMapper = bankTransactionMapper;
    }

    @Override
    public BankTransactionDTO save(BankTransactionDTO bankTransactionDTO) {
        LOG.debug("Request to save BankTransaction : {}", bankTransactionDTO);
        BankTransaction bankTransaction = bankTransactionMapper.toEntity(bankTransactionDTO);
        bankTransaction = bankTransactionRepository.save(bankTransaction);
        return bankTransactionMapper.toDto(bankTransaction);
    }

    @Override
    public BankTransactionDTO update(BankTransactionDTO bankTransactionDTO) {
        LOG.debug("Request to update BankTransaction : {}", bankTransactionDTO);
        BankTransaction bankTransaction = bankTransactionMapper.toEntity(bankTransactionDTO);
        bankTransaction.setIsPersisted();
        bankTransaction = bankTransactionRepository.save(bankTransaction);
        return bankTransactionMapper.toDto(bankTransaction);
    }

    @Override
    public Optional<BankTransactionDTO> partialUpdate(BankTransactionDTO bankTransactionDTO) {
        LOG.debug("Request to partially update BankTransaction : {}", bankTransactionDTO);

        return bankTransactionRepository
            .findById(bankTransactionDTO.getId())
            .map(existingBankTransaction -> {
                bankTransactionMapper.partialUpdate(existingBankTransaction, bankTransactionDTO);

                return existingBankTransaction;
            })
            .map(bankTransactionRepository::save)
            .map(bankTransactionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BankTransactionDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all BankTransactions");
        return bankTransactionRepository.findAll(pageable).map(bankTransactionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BankTransactionDTO> findOne(String id) {
        LOG.debug("Request to get BankTransaction : {}", id);
        return bankTransactionRepository.findById(id).map(bankTransactionMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete BankTransaction : {}", id);
        bankTransactionRepository.deleteById(id);
    }
}
