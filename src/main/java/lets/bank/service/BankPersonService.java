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

import java.util.List;
import java.util.Optional;
import lets.bank.service.dto.BankPersonDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link lets.bank.domain.BankPerson}.
 */
public interface BankPersonService {
    /**
     * Save a bankPerson.
     *
     * @param bankPersonDTO the entity to save.
     * @return the persisted entity.
     */
    BankPersonDTO save(BankPersonDTO bankPersonDTO);

    /**
     * Updates a bankPerson.
     *
     * @param bankPersonDTO the entity to update.
     * @return the persisted entity.
     */
    BankPersonDTO update(BankPersonDTO bankPersonDTO);

    /**
     * Partially updates a bankPerson.
     *
     * @param bankPersonDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BankPersonDTO> partialUpdate(BankPersonDTO bankPersonDTO);

    /**
     * Get all the bankPeople.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BankPersonDTO> findAll(Pageable pageable);

    /**
     * Get all the BankPersonDTO where BankCustomer is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<BankPersonDTO> findAllWhereBankCustomerIsNull();
    /**
     * Get all the BankPersonDTO where BankAccount is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<BankPersonDTO> findAllWhereBankAccountIsNull();

    /**
     * Get the "id" bankPerson.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BankPersonDTO> findOne(String id);

    /**
     * Delete the "id" bankPerson.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
