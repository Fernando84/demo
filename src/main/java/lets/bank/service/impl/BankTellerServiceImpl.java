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
import lets.bank.domain.BankTeller;
import lets.bank.repository.BankTellerRepository;
import lets.bank.service.BankTellerService;
import lets.bank.service.dto.BankTellerDTO;
import lets.bank.service.mapper.BankTellerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link lets.bank.domain.BankTeller}.
 */
@Service
@Transactional
public class BankTellerServiceImpl implements BankTellerService {

    private static final Logger LOG = LoggerFactory.getLogger(BankTellerServiceImpl.class);

    private final BankTellerRepository bankTellerRepository;

    private final BankTellerMapper bankTellerMapper;

    public BankTellerServiceImpl(BankTellerRepository bankTellerRepository, BankTellerMapper bankTellerMapper) {
        this.bankTellerRepository = bankTellerRepository;
        this.bankTellerMapper = bankTellerMapper;
    }

    @Override
    public BankTellerDTO save(BankTellerDTO bankTellerDTO) {
        LOG.debug("Request to save BankTeller : {}", bankTellerDTO);
        BankTeller bankTeller = bankTellerMapper.toEntity(bankTellerDTO);
        bankTeller = bankTellerRepository.save(bankTeller);
        return bankTellerMapper.toDto(bankTeller);
    }

    @Override
    public BankTellerDTO update(BankTellerDTO bankTellerDTO) {
        LOG.debug("Request to update BankTeller : {}", bankTellerDTO);
        BankTeller bankTeller = bankTellerMapper.toEntity(bankTellerDTO);
        bankTeller.setIsPersisted();
        bankTeller = bankTellerRepository.save(bankTeller);
        return bankTellerMapper.toDto(bankTeller);
    }

    @Override
    public Optional<BankTellerDTO> partialUpdate(BankTellerDTO bankTellerDTO) {
        LOG.debug("Request to partially update BankTeller : {}", bankTellerDTO);

        return bankTellerRepository
            .findById(bankTellerDTO.getId())
            .map(existingBankTeller -> {
                bankTellerMapper.partialUpdate(existingBankTeller, bankTellerDTO);

                return existingBankTeller;
            })
            .map(bankTellerRepository::save)
            .map(bankTellerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BankTellerDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all BankTellers");
        return bankTellerRepository.findAll(pageable).map(bankTellerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BankTellerDTO> findOne(String id) {
        LOG.debug("Request to get BankTeller : {}", id);
        return bankTellerRepository.findById(id).map(bankTellerMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete BankTeller : {}", id);
        bankTellerRepository.deleteById(id);
    }
}
