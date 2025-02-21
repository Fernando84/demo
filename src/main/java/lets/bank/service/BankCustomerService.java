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
import lets.bank.service.dto.BankCustomerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link lets.bank.domain.BankCustomer}.
 */
public interface BankCustomerService {
    /**
     * Save a bankCustomer.
     *
     * @param bankCustomerDTO the entity to save.
     * @return the persisted entity.
     */
    BankCustomerDTO save(BankCustomerDTO bankCustomerDTO);

    /**
     * Updates a bankCustomer.
     *
     * @param bankCustomerDTO the entity to update.
     * @return the persisted entity.
     */
    BankCustomerDTO update(BankCustomerDTO bankCustomerDTO);

    /**
     * Partially updates a bankCustomer.
     *
     * @param bankCustomerDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BankCustomerDTO> partialUpdate(BankCustomerDTO bankCustomerDTO);

    /**
     * Get all the bankCustomers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BankCustomerDTO> findAll(Pageable pageable);

    /**
     * Get the "id" bankCustomer.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BankCustomerDTO> findOne(String id);

    /**
     * Delete the "id" bankCustomer.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
