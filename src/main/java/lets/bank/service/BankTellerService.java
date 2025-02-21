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
import lets.bank.service.dto.BankTellerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link lets.bank.domain.BankTeller}.
 */
public interface BankTellerService {
    /**
     * Save a bankTeller.
     *
     * @param bankTellerDTO the entity to save.
     * @return the persisted entity.
     */
    BankTellerDTO save(BankTellerDTO bankTellerDTO);

    /**
     * Updates a bankTeller.
     *
     * @param bankTellerDTO the entity to update.
     * @return the persisted entity.
     */
    BankTellerDTO update(BankTellerDTO bankTellerDTO);

    /**
     * Partially updates a bankTeller.
     *
     * @param bankTellerDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BankTellerDTO> partialUpdate(BankTellerDTO bankTellerDTO);

    /**
     * Get all the bankTellers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BankTellerDTO> findAll(Pageable pageable);

    /**
     * Get the "id" bankTeller.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BankTellerDTO> findOne(String id);

    /**
     * Delete the "id" bankTeller.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
