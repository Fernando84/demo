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
package lets.bank.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lets.bank.repository.BankPersonRepository;
import lets.bank.service.BankPersonService;
import lets.bank.service.dto.BankPersonDTO;
import lets.bank.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link lets.bank.domain.BankPerson}.
 */
@RestController
@RequestMapping("/api/bank-people")
public class BankPersonResource {

    private static final Logger LOG = LoggerFactory.getLogger(BankPersonResource.class);

    private static final String ENTITY_NAME = "bankPerson";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BankPersonService bankPersonService;

    private final BankPersonRepository bankPersonRepository;

    public BankPersonResource(BankPersonService bankPersonService, BankPersonRepository bankPersonRepository) {
        this.bankPersonService = bankPersonService;
        this.bankPersonRepository = bankPersonRepository;
    }

    /**
     * {@code POST  /bank-people} : Create a new bankPerson.
     *
     * @param bankPersonDTO the bankPersonDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bankPersonDTO, or with status {@code 400 (Bad Request)} if the bankPerson has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<BankPersonDTO> createBankPerson(@Valid @RequestBody BankPersonDTO bankPersonDTO) throws URISyntaxException {
        LOG.debug("REST request to save BankPerson : {}", bankPersonDTO);
        if (bankPersonRepository.existsById(bankPersonDTO.getId())) {
            throw new BadRequestAlertException("bankPerson already exists", ENTITY_NAME, "idexists");
        }
        bankPersonDTO = bankPersonService.save(bankPersonDTO);
        return ResponseEntity.created(new URI("/api/bank-people/" + bankPersonDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, bankPersonDTO.getId()))
            .body(bankPersonDTO);
    }

    /**
     * {@code PUT  /bank-people/:id} : Updates an existing bankPerson.
     *
     * @param id the id of the bankPersonDTO to save.
     * @param bankPersonDTO the bankPersonDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankPersonDTO,
     * or with status {@code 400 (Bad Request)} if the bankPersonDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bankPersonDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BankPersonDTO> updateBankPerson(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody BankPersonDTO bankPersonDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update BankPerson : {}, {}", id, bankPersonDTO);
        if (bankPersonDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankPersonDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankPersonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        bankPersonDTO = bankPersonService.update(bankPersonDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankPersonDTO.getId()))
            .body(bankPersonDTO);
    }

    /**
     * {@code PATCH  /bank-people/:id} : Partial updates given fields of an existing bankPerson, field will ignore if it is null
     *
     * @param id the id of the bankPersonDTO to save.
     * @param bankPersonDTO the bankPersonDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankPersonDTO,
     * or with status {@code 400 (Bad Request)} if the bankPersonDTO is not valid,
     * or with status {@code 404 (Not Found)} if the bankPersonDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bankPersonDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BankPersonDTO> partialUpdateBankPerson(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody BankPersonDTO bankPersonDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update BankPerson partially : {}, {}", id, bankPersonDTO);
        if (bankPersonDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankPersonDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankPersonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BankPersonDTO> result = bankPersonService.partialUpdate(bankPersonDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankPersonDTO.getId())
        );
    }

    /**
     * {@code GET  /bank-people} : get all the bankPeople.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bankPeople in body.
     */
    @GetMapping("")
    public ResponseEntity<List<BankPersonDTO>> getAllBankPeople(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "filter", required = false) String filter
    ) {
        if ("bankcustomer-is-null".equals(filter)) {
            LOG.debug("REST request to get all BankPersons where bankCustomer is null");
            return new ResponseEntity<>(bankPersonService.findAllWhereBankCustomerIsNull(), HttpStatus.OK);
        }

        if ("bankaccount-is-null".equals(filter)) {
            LOG.debug("REST request to get all BankPersons where bankAccount is null");
            return new ResponseEntity<>(bankPersonService.findAllWhereBankAccountIsNull(), HttpStatus.OK);
        }
        LOG.debug("REST request to get a page of BankPeople");
        Page<BankPersonDTO> page = bankPersonService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bank-people/:id} : get the "id" bankPerson.
     *
     * @param id the id of the bankPersonDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bankPersonDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BankPersonDTO> getBankPerson(@PathVariable("id") String id) {
        LOG.debug("REST request to get BankPerson : {}", id);
        Optional<BankPersonDTO> bankPersonDTO = bankPersonService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bankPersonDTO);
    }

    /**
     * {@code DELETE  /bank-people/:id} : delete the "id" bankPerson.
     *
     * @param id the id of the bankPersonDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBankPerson(@PathVariable("id") String id) {
        LOG.debug("REST request to delete BankPerson : {}", id);
        bankPersonService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
