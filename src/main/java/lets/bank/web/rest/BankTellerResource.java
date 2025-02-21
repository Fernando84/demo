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
import lets.bank.repository.BankTellerRepository;
import lets.bank.service.BankTellerService;
import lets.bank.service.dto.BankTellerDTO;
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
 * REST controller for managing {@link lets.bank.domain.BankTeller}.
 */
@RestController
@RequestMapping("/api/bank-tellers")
public class BankTellerResource {

    private static final Logger LOG = LoggerFactory.getLogger(BankTellerResource.class);

    private static final String ENTITY_NAME = "bankTeller";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BankTellerService bankTellerService;

    private final BankTellerRepository bankTellerRepository;

    public BankTellerResource(BankTellerService bankTellerService, BankTellerRepository bankTellerRepository) {
        this.bankTellerService = bankTellerService;
        this.bankTellerRepository = bankTellerRepository;
    }

    /**
     * {@code POST  /bank-tellers} : Create a new bankTeller.
     *
     * @param bankTellerDTO the bankTellerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bankTellerDTO, or with status {@code 400 (Bad Request)} if the bankTeller has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<BankTellerDTO> createBankTeller(@Valid @RequestBody BankTellerDTO bankTellerDTO) throws URISyntaxException {
        LOG.debug("REST request to save BankTeller : {}", bankTellerDTO);
        if (bankTellerRepository.existsById(bankTellerDTO.getId())) {
            throw new BadRequestAlertException("bankTeller already exists", ENTITY_NAME, "idexists");
        }
        bankTellerDTO = bankTellerService.save(bankTellerDTO);
        return ResponseEntity.created(new URI("/api/bank-tellers/" + bankTellerDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, bankTellerDTO.getId()))
            .body(bankTellerDTO);
    }

    /**
     * {@code PUT  /bank-tellers/:id} : Updates an existing bankTeller.
     *
     * @param id the id of the bankTellerDTO to save.
     * @param bankTellerDTO the bankTellerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankTellerDTO,
     * or with status {@code 400 (Bad Request)} if the bankTellerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bankTellerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BankTellerDTO> updateBankTeller(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody BankTellerDTO bankTellerDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update BankTeller : {}, {}", id, bankTellerDTO);
        if (bankTellerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankTellerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankTellerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        bankTellerDTO = bankTellerService.update(bankTellerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankTellerDTO.getId()))
            .body(bankTellerDTO);
    }

    /**
     * {@code PATCH  /bank-tellers/:id} : Partial updates given fields of an existing bankTeller, field will ignore if it is null
     *
     * @param id the id of the bankTellerDTO to save.
     * @param bankTellerDTO the bankTellerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankTellerDTO,
     * or with status {@code 400 (Bad Request)} if the bankTellerDTO is not valid,
     * or with status {@code 404 (Not Found)} if the bankTellerDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bankTellerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BankTellerDTO> partialUpdateBankTeller(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody BankTellerDTO bankTellerDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update BankTeller partially : {}, {}", id, bankTellerDTO);
        if (bankTellerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankTellerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankTellerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BankTellerDTO> result = bankTellerService.partialUpdate(bankTellerDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankTellerDTO.getId())
        );
    }

    /**
     * {@code GET  /bank-tellers} : get all the bankTellers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bankTellers in body.
     */
    @GetMapping("")
    public ResponseEntity<List<BankTellerDTO>> getAllBankTellers(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of BankTellers");
        Page<BankTellerDTO> page = bankTellerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bank-tellers/:id} : get the "id" bankTeller.
     *
     * @param id the id of the bankTellerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bankTellerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BankTellerDTO> getBankTeller(@PathVariable("id") String id) {
        LOG.debug("REST request to get BankTeller : {}", id);
        Optional<BankTellerDTO> bankTellerDTO = bankTellerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bankTellerDTO);
    }

    /**
     * {@code DELETE  /bank-tellers/:id} : delete the "id" bankTeller.
     *
     * @param id the id of the bankTellerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBankTeller(@PathVariable("id") String id) {
        LOG.debug("REST request to delete BankTeller : {}", id);
        bankTellerService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
