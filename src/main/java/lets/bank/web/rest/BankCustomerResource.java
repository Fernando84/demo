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
import lets.bank.repository.BankCustomerRepository;
import lets.bank.service.BankCustomerService;
import lets.bank.service.dto.BankCustomerDTO;
import lets.bank.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link lets.bank.domain.BankCustomer}.
 */
@RestController
@RequestMapping("/api/bank-customers")
public class BankCustomerResource {

    private static final Logger LOG = LoggerFactory.getLogger(BankCustomerResource.class);

    private static final String ENTITY_NAME = "bankCustomer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BankCustomerService bankCustomerService;

    private final BankCustomerRepository bankCustomerRepository;

    public BankCustomerResource(BankCustomerService bankCustomerService, BankCustomerRepository bankCustomerRepository) {
        this.bankCustomerService = bankCustomerService;
        this.bankCustomerRepository = bankCustomerRepository;
    }

    /**
     * {@code POST  /bank-customers} : Create a new bankCustomer.
     *
     * @param bankCustomerDTO the bankCustomerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bankCustomerDTO, or with status {@code 400 (Bad Request)} if the bankCustomer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<BankCustomerDTO> createBankCustomer(@Valid @RequestBody BankCustomerDTO bankCustomerDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save BankCustomer : {}", bankCustomerDTO);
        if (bankCustomerRepository.existsById(bankCustomerDTO.getId())) {
            throw new BadRequestAlertException("bankCustomer already exists", ENTITY_NAME, "idexists");
        }
        bankCustomerDTO = bankCustomerService.save(bankCustomerDTO);
        return ResponseEntity.created(new URI("/api/bank-customers/" + bankCustomerDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, bankCustomerDTO.getId()))
            .body(bankCustomerDTO);
    }

    /**
     * {@code PUT  /bank-customers/:id} : Updates an existing bankCustomer.
     *
     * @param id the id of the bankCustomerDTO to save.
     * @param bankCustomerDTO the bankCustomerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankCustomerDTO,
     * or with status {@code 400 (Bad Request)} if the bankCustomerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bankCustomerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BankCustomerDTO> updateBankCustomer(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody BankCustomerDTO bankCustomerDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update BankCustomer : {}, {}", id, bankCustomerDTO);
        if (bankCustomerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankCustomerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankCustomerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        bankCustomerDTO = bankCustomerService.update(bankCustomerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankCustomerDTO.getId()))
            .body(bankCustomerDTO);
    }

    /**
     * {@code PATCH  /bank-customers/:id} : Partial updates given fields of an existing bankCustomer, field will ignore if it is null
     *
     * @param id the id of the bankCustomerDTO to save.
     * @param bankCustomerDTO the bankCustomerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankCustomerDTO,
     * or with status {@code 400 (Bad Request)} if the bankCustomerDTO is not valid,
     * or with status {@code 404 (Not Found)} if the bankCustomerDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bankCustomerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BankCustomerDTO> partialUpdateBankCustomer(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody BankCustomerDTO bankCustomerDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update BankCustomer partially : {}, {}", id, bankCustomerDTO);
        if (bankCustomerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankCustomerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankCustomerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BankCustomerDTO> result = bankCustomerService.partialUpdate(bankCustomerDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankCustomerDTO.getId())
        );
    }

    /**
     * {@code GET  /bank-customers} : get all the bankCustomers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bankCustomers in body.
     */
    @GetMapping("")
    public ResponseEntity<List<BankCustomerDTO>> getAllBankCustomers(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of BankCustomers");
        Page<BankCustomerDTO> page = bankCustomerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bank-customers/:id} : get the "id" bankCustomer.
     *
     * @param id the id of the bankCustomerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bankCustomerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BankCustomerDTO> getBankCustomer(@PathVariable("id") String id) {
        LOG.debug("REST request to get BankCustomer : {}", id);
        Optional<BankCustomerDTO> bankCustomerDTO = bankCustomerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bankCustomerDTO);
    }

    /**
     * {@code DELETE  /bank-customers/:id} : delete the "id" bankCustomer.
     *
     * @param id the id of the bankCustomerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBankCustomer(@PathVariable("id") String id) {
        LOG.debug("REST request to delete BankCustomer : {}", id);
        bankCustomerService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
