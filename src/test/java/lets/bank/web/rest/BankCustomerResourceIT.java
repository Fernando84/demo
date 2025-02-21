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

import static lets.bank.domain.BankCustomerAsserts.*;
import static lets.bank.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import lets.bank.IntegrationTest;
import lets.bank.domain.BankCustomer;
import lets.bank.repository.BankCustomerRepository;
import lets.bank.service.dto.BankCustomerDTO;
import lets.bank.service.mapper.BankCustomerMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BankCustomerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BankCustomerResourceIT {

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PIN_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PIN_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/bank-customers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BankCustomerRepository bankCustomerRepository;

    @Autowired
    private BankCustomerMapper bankCustomerMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBankCustomerMockMvc;

    private BankCustomer bankCustomer;

    private BankCustomer insertedBankCustomer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankCustomer createEntity() {
        return new BankCustomer()
            .id(UUID.randomUUID().toString())
            .email(DEFAULT_EMAIL)
            .pinCode(DEFAULT_PIN_CODE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankCustomer createUpdatedEntity() {
        return new BankCustomer()
            .id(UUID.randomUUID().toString())
            .email(UPDATED_EMAIL)
            .pinCode(UPDATED_PIN_CODE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
    }

    @BeforeEach
    public void initTest() {
        bankCustomer = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedBankCustomer != null) {
            bankCustomerRepository.delete(insertedBankCustomer);
            insertedBankCustomer = null;
        }
    }

    @Test
    @Transactional
    void createBankCustomer() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the BankCustomer
        BankCustomerDTO bankCustomerDTO = bankCustomerMapper.toDto(bankCustomer);
        var returnedBankCustomerDTO = om.readValue(
            restBankCustomerMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bankCustomerDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            BankCustomerDTO.class
        );

        // Validate the BankCustomer in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedBankCustomer = bankCustomerMapper.toEntity(returnedBankCustomerDTO);
        assertBankCustomerUpdatableFieldsEquals(returnedBankCustomer, getPersistedBankCustomer(returnedBankCustomer));

        insertedBankCustomer = returnedBankCustomer;
    }

    @Test
    @Transactional
    void createBankCustomerWithExistingId() throws Exception {
        // Create the BankCustomer with an existing ID
        insertedBankCustomer = bankCustomerRepository.saveAndFlush(bankCustomer);
        BankCustomerDTO bankCustomerDTO = bankCustomerMapper.toDto(bankCustomer);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBankCustomerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bankCustomerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BankCustomer in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBankCustomers() throws Exception {
        // Initialize the database
        bankCustomer.setId(UUID.randomUUID().toString());
        insertedBankCustomer = bankCustomerRepository.saveAndFlush(bankCustomer);

        // Get all the bankCustomerList
        restBankCustomerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bankCustomer.getId())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].pinCode").value(hasItem(DEFAULT_PIN_CODE)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getBankCustomer() throws Exception {
        // Initialize the database
        bankCustomer.setId(UUID.randomUUID().toString());
        insertedBankCustomer = bankCustomerRepository.saveAndFlush(bankCustomer);

        // Get the bankCustomer
        restBankCustomerMockMvc
            .perform(get(ENTITY_API_URL_ID, bankCustomer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bankCustomer.getId()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.pinCode").value(DEFAULT_PIN_CODE))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingBankCustomer() throws Exception {
        // Get the bankCustomer
        restBankCustomerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBankCustomer() throws Exception {
        // Initialize the database
        bankCustomer.setId(UUID.randomUUID().toString());
        insertedBankCustomer = bankCustomerRepository.saveAndFlush(bankCustomer);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bankCustomer
        BankCustomer updatedBankCustomer = bankCustomerRepository.findById(bankCustomer.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBankCustomer are not directly saved in db
        em.detach(updatedBankCustomer);
        updatedBankCustomer
            .email(UPDATED_EMAIL)
            .pinCode(UPDATED_PIN_CODE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        BankCustomerDTO bankCustomerDTO = bankCustomerMapper.toDto(updatedBankCustomer);

        restBankCustomerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bankCustomerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bankCustomerDTO))
            )
            .andExpect(status().isOk());

        // Validate the BankCustomer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBankCustomerToMatchAllProperties(updatedBankCustomer);
    }

    @Test
    @Transactional
    void putNonExistingBankCustomer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bankCustomer.setId(UUID.randomUUID().toString());

        // Create the BankCustomer
        BankCustomerDTO bankCustomerDTO = bankCustomerMapper.toDto(bankCustomer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankCustomerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bankCustomerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bankCustomerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankCustomer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBankCustomer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bankCustomer.setId(UUID.randomUUID().toString());

        // Create the BankCustomer
        BankCustomerDTO bankCustomerDTO = bankCustomerMapper.toDto(bankCustomer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankCustomerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bankCustomerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankCustomer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBankCustomer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bankCustomer.setId(UUID.randomUUID().toString());

        // Create the BankCustomer
        BankCustomerDTO bankCustomerDTO = bankCustomerMapper.toDto(bankCustomer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankCustomerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bankCustomerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BankCustomer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBankCustomerWithPatch() throws Exception {
        // Initialize the database
        bankCustomer.setId(UUID.randomUUID().toString());
        insertedBankCustomer = bankCustomerRepository.saveAndFlush(bankCustomer);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bankCustomer using partial update
        BankCustomer partialUpdatedBankCustomer = new BankCustomer();
        partialUpdatedBankCustomer.setId(bankCustomer.getId());

        partialUpdatedBankCustomer
            .email(UPDATED_EMAIL)
            .pinCode(UPDATED_PIN_CODE)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restBankCustomerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankCustomer.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBankCustomer))
            )
            .andExpect(status().isOk());

        // Validate the BankCustomer in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBankCustomerUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBankCustomer, bankCustomer),
            getPersistedBankCustomer(bankCustomer)
        );
    }

    @Test
    @Transactional
    void fullUpdateBankCustomerWithPatch() throws Exception {
        // Initialize the database
        bankCustomer.setId(UUID.randomUUID().toString());
        insertedBankCustomer = bankCustomerRepository.saveAndFlush(bankCustomer);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bankCustomer using partial update
        BankCustomer partialUpdatedBankCustomer = new BankCustomer();
        partialUpdatedBankCustomer.setId(bankCustomer.getId());

        partialUpdatedBankCustomer
            .email(UPDATED_EMAIL)
            .pinCode(UPDATED_PIN_CODE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restBankCustomerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankCustomer.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBankCustomer))
            )
            .andExpect(status().isOk());

        // Validate the BankCustomer in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBankCustomerUpdatableFieldsEquals(partialUpdatedBankCustomer, getPersistedBankCustomer(partialUpdatedBankCustomer));
    }

    @Test
    @Transactional
    void patchNonExistingBankCustomer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bankCustomer.setId(UUID.randomUUID().toString());

        // Create the BankCustomer
        BankCustomerDTO bankCustomerDTO = bankCustomerMapper.toDto(bankCustomer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankCustomerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bankCustomerDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bankCustomerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankCustomer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBankCustomer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bankCustomer.setId(UUID.randomUUID().toString());

        // Create the BankCustomer
        BankCustomerDTO bankCustomerDTO = bankCustomerMapper.toDto(bankCustomer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankCustomerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bankCustomerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankCustomer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBankCustomer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bankCustomer.setId(UUID.randomUUID().toString());

        // Create the BankCustomer
        BankCustomerDTO bankCustomerDTO = bankCustomerMapper.toDto(bankCustomer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankCustomerMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(bankCustomerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BankCustomer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBankCustomer() throws Exception {
        // Initialize the database
        bankCustomer.setId(UUID.randomUUID().toString());
        insertedBankCustomer = bankCustomerRepository.saveAndFlush(bankCustomer);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the bankCustomer
        restBankCustomerMockMvc
            .perform(delete(ENTITY_API_URL_ID, bankCustomer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return bankCustomerRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected BankCustomer getPersistedBankCustomer(BankCustomer bankCustomer) {
        return bankCustomerRepository.findById(bankCustomer.getId()).orElseThrow();
    }

    protected void assertPersistedBankCustomerToMatchAllProperties(BankCustomer expectedBankCustomer) {
        assertBankCustomerAllPropertiesEquals(expectedBankCustomer, getPersistedBankCustomer(expectedBankCustomer));
    }

    protected void assertPersistedBankCustomerToMatchUpdatableProperties(BankCustomer expectedBankCustomer) {
        assertBankCustomerAllUpdatablePropertiesEquals(expectedBankCustomer, getPersistedBankCustomer(expectedBankCustomer));
    }
}
