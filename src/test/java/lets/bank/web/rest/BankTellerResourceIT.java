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

import static lets.bank.domain.BankTellerAsserts.*;
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
import lets.bank.domain.BankTeller;
import lets.bank.repository.BankTellerRepository;
import lets.bank.service.dto.BankTellerDTO;
import lets.bank.service.mapper.BankTellerMapper;
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
 * Integration tests for the {@link BankTellerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BankTellerResourceIT {

    private static final String DEFAULT_EMPLOYEE_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYEE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_THAI_NAME = "AAAAAAAAAA";
    private static final String UPDATED_THAI_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ENGLISH_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ENGLISH_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/bank-tellers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BankTellerRepository bankTellerRepository;

    @Autowired
    private BankTellerMapper bankTellerMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBankTellerMockMvc;

    private BankTeller bankTeller;

    private BankTeller insertedBankTeller;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankTeller createEntity() {
        return new BankTeller()
            .id(UUID.randomUUID().toString())
            .employeeId(DEFAULT_EMPLOYEE_ID)
            .thaiName(DEFAULT_THAI_NAME)
            .englishName(DEFAULT_ENGLISH_NAME)
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
    public static BankTeller createUpdatedEntity() {
        return new BankTeller()
            .id(UUID.randomUUID().toString())
            .employeeId(UPDATED_EMPLOYEE_ID)
            .thaiName(UPDATED_THAI_NAME)
            .englishName(UPDATED_ENGLISH_NAME)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
    }

    @BeforeEach
    public void initTest() {
        bankTeller = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedBankTeller != null) {
            bankTellerRepository.delete(insertedBankTeller);
            insertedBankTeller = null;
        }
    }

    @Test
    @Transactional
    void createBankTeller() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the BankTeller
        BankTellerDTO bankTellerDTO = bankTellerMapper.toDto(bankTeller);
        var returnedBankTellerDTO = om.readValue(
            restBankTellerMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bankTellerDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            BankTellerDTO.class
        );

        // Validate the BankTeller in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedBankTeller = bankTellerMapper.toEntity(returnedBankTellerDTO);
        assertBankTellerUpdatableFieldsEquals(returnedBankTeller, getPersistedBankTeller(returnedBankTeller));

        insertedBankTeller = returnedBankTeller;
    }

    @Test
    @Transactional
    void createBankTellerWithExistingId() throws Exception {
        // Create the BankTeller with an existing ID
        insertedBankTeller = bankTellerRepository.saveAndFlush(bankTeller);
        BankTellerDTO bankTellerDTO = bankTellerMapper.toDto(bankTeller);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBankTellerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bankTellerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BankTeller in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBankTellers() throws Exception {
        // Initialize the database
        bankTeller.setId(UUID.randomUUID().toString());
        insertedBankTeller = bankTellerRepository.saveAndFlush(bankTeller);

        // Get all the bankTellerList
        restBankTellerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bankTeller.getId())))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID)))
            .andExpect(jsonPath("$.[*].thaiName").value(hasItem(DEFAULT_THAI_NAME)))
            .andExpect(jsonPath("$.[*].englishName").value(hasItem(DEFAULT_ENGLISH_NAME)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getBankTeller() throws Exception {
        // Initialize the database
        bankTeller.setId(UUID.randomUUID().toString());
        insertedBankTeller = bankTellerRepository.saveAndFlush(bankTeller);

        // Get the bankTeller
        restBankTellerMockMvc
            .perform(get(ENTITY_API_URL_ID, bankTeller.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bankTeller.getId()))
            .andExpect(jsonPath("$.employeeId").value(DEFAULT_EMPLOYEE_ID))
            .andExpect(jsonPath("$.thaiName").value(DEFAULT_THAI_NAME))
            .andExpect(jsonPath("$.englishName").value(DEFAULT_ENGLISH_NAME))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingBankTeller() throws Exception {
        // Get the bankTeller
        restBankTellerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBankTeller() throws Exception {
        // Initialize the database
        bankTeller.setId(UUID.randomUUID().toString());
        insertedBankTeller = bankTellerRepository.saveAndFlush(bankTeller);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bankTeller
        BankTeller updatedBankTeller = bankTellerRepository.findById(bankTeller.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBankTeller are not directly saved in db
        em.detach(updatedBankTeller);
        updatedBankTeller
            .employeeId(UPDATED_EMPLOYEE_ID)
            .thaiName(UPDATED_THAI_NAME)
            .englishName(UPDATED_ENGLISH_NAME)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        BankTellerDTO bankTellerDTO = bankTellerMapper.toDto(updatedBankTeller);

        restBankTellerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bankTellerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bankTellerDTO))
            )
            .andExpect(status().isOk());

        // Validate the BankTeller in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBankTellerToMatchAllProperties(updatedBankTeller);
    }

    @Test
    @Transactional
    void putNonExistingBankTeller() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bankTeller.setId(UUID.randomUUID().toString());

        // Create the BankTeller
        BankTellerDTO bankTellerDTO = bankTellerMapper.toDto(bankTeller);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankTellerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bankTellerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bankTellerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankTeller in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBankTeller() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bankTeller.setId(UUID.randomUUID().toString());

        // Create the BankTeller
        BankTellerDTO bankTellerDTO = bankTellerMapper.toDto(bankTeller);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankTellerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bankTellerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankTeller in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBankTeller() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bankTeller.setId(UUID.randomUUID().toString());

        // Create the BankTeller
        BankTellerDTO bankTellerDTO = bankTellerMapper.toDto(bankTeller);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankTellerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bankTellerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BankTeller in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBankTellerWithPatch() throws Exception {
        // Initialize the database
        bankTeller.setId(UUID.randomUUID().toString());
        insertedBankTeller = bankTellerRepository.saveAndFlush(bankTeller);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bankTeller using partial update
        BankTeller partialUpdatedBankTeller = new BankTeller();
        partialUpdatedBankTeller.setId(bankTeller.getId());

        partialUpdatedBankTeller
            .thaiName(UPDATED_THAI_NAME)
            .englishName(UPDATED_ENGLISH_NAME)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE);

        restBankTellerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankTeller.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBankTeller))
            )
            .andExpect(status().isOk());

        // Validate the BankTeller in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBankTellerUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBankTeller, bankTeller),
            getPersistedBankTeller(bankTeller)
        );
    }

    @Test
    @Transactional
    void fullUpdateBankTellerWithPatch() throws Exception {
        // Initialize the database
        bankTeller.setId(UUID.randomUUID().toString());
        insertedBankTeller = bankTellerRepository.saveAndFlush(bankTeller);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bankTeller using partial update
        BankTeller partialUpdatedBankTeller = new BankTeller();
        partialUpdatedBankTeller.setId(bankTeller.getId());

        partialUpdatedBankTeller
            .employeeId(UPDATED_EMPLOYEE_ID)
            .thaiName(UPDATED_THAI_NAME)
            .englishName(UPDATED_ENGLISH_NAME)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restBankTellerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankTeller.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBankTeller))
            )
            .andExpect(status().isOk());

        // Validate the BankTeller in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBankTellerUpdatableFieldsEquals(partialUpdatedBankTeller, getPersistedBankTeller(partialUpdatedBankTeller));
    }

    @Test
    @Transactional
    void patchNonExistingBankTeller() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bankTeller.setId(UUID.randomUUID().toString());

        // Create the BankTeller
        BankTellerDTO bankTellerDTO = bankTellerMapper.toDto(bankTeller);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankTellerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bankTellerDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bankTellerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankTeller in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBankTeller() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bankTeller.setId(UUID.randomUUID().toString());

        // Create the BankTeller
        BankTellerDTO bankTellerDTO = bankTellerMapper.toDto(bankTeller);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankTellerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bankTellerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankTeller in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBankTeller() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bankTeller.setId(UUID.randomUUID().toString());

        // Create the BankTeller
        BankTellerDTO bankTellerDTO = bankTellerMapper.toDto(bankTeller);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankTellerMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(bankTellerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BankTeller in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBankTeller() throws Exception {
        // Initialize the database
        bankTeller.setId(UUID.randomUUID().toString());
        insertedBankTeller = bankTellerRepository.saveAndFlush(bankTeller);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the bankTeller
        restBankTellerMockMvc
            .perform(delete(ENTITY_API_URL_ID, bankTeller.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return bankTellerRepository.count();
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

    protected BankTeller getPersistedBankTeller(BankTeller bankTeller) {
        return bankTellerRepository.findById(bankTeller.getId()).orElseThrow();
    }

    protected void assertPersistedBankTellerToMatchAllProperties(BankTeller expectedBankTeller) {
        assertBankTellerAllPropertiesEquals(expectedBankTeller, getPersistedBankTeller(expectedBankTeller));
    }

    protected void assertPersistedBankTellerToMatchUpdatableProperties(BankTeller expectedBankTeller) {
        assertBankTellerAllUpdatablePropertiesEquals(expectedBankTeller, getPersistedBankTeller(expectedBankTeller));
    }
}
