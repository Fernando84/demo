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
package lets.bank.service;

import java.util.Optional;
import lets.bank.service.dto.BankTransactionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link lets.bank.domain.BankTransaction}.
 */
public interface BankTransactionService {
    /**
     * Save a bankTransaction.
     *
     * @param bankTransactionDTO the entity to save.
     * @return the persisted entity.
     */
    BankTransactionDTO save(BankTransactionDTO bankTransactionDTO);

    /**
     * Updates a bankTransaction.
     *
     * @param bankTransactionDTO the entity to update.
     * @return the persisted entity.
     */
    BankTransactionDTO update(BankTransactionDTO bankTransactionDTO);

    /**
     * Partially updates a bankTransaction.
     *
     * @param bankTransactionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BankTransactionDTO> partialUpdate(BankTransactionDTO bankTransactionDTO);

    /**
     * Get all the bankTransactions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BankTransactionDTO> findAll(Pageable pageable);

    /**
     * Get the "id" bankTransaction.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BankTransactionDTO> findOne(String id);

    /**
     * Delete the "id" bankTransaction.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
