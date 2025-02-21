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

import static lets.bank.domain.BankPersonAsserts.*;
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
import lets.bank.domain.BankPerson;
import lets.bank.repository.BankPersonRepository;
import lets.bank.service.dto.BankPersonDTO;
import lets.bank.service.mapper.BankPersonMapper;
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
 * Integration tests for the {@link BankPersonResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BankPersonResourceIT {

    private static final String DEFAULT_CITIZEN_ID = "AAAAAAAAAA";
    private static final String UPDATED_CITIZEN_ID = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/bank-people";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BankPersonRepository bankPersonRepository;

    @Autowired
    private BankPersonMapper bankPersonMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBankPersonMockMvc;

    private BankPerson bankPerson;

    private BankPerson insertedBankPerson;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankPerson createEntity() {
        return new BankPerson()
            .id(UUID.randomUUID().toString())
            .citizenId(DEFAULT_CITIZEN_ID)
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
    public static BankPerson createUpdatedEntity() {
        return new BankPerson()
            .id(UUID.randomUUID().toString())
            .citizenId(UPDATED_CITIZEN_ID)
            .thaiName(UPDATED_THAI_NAME)
            .englishName(UPDATED_ENGLISH_NAME)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
    }

    @BeforeEach
    public void initTest() {
        bankPerson = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedBankPerson != null) {
            bankPersonRepository.delete(insertedBankPerson);
            insertedBankPerson = null;
        }
    }

    @Test
    @Transactional
    void createBankPerson() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the BankPerson
        BankPersonDTO bankPersonDTO = bankPersonMapper.toDto(bankPerson);
        var returnedBankPersonDTO = om.readValue(
            restBankPersonMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bankPersonDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            BankPersonDTO.class
        );

        // Validate the BankPerson in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedBankPerson = bankPersonMapper.toEntity(returnedBankPersonDTO);
        assertBankPersonUpdatableFieldsEquals(returnedBankPerson, getPersistedBankPerson(returnedBankPerson));

        insertedBankPerson = returnedBankPerson;
    }

    @Test
    @Transactional
    void createBankPersonWithExistingId() throws Exception {
        // Create the BankPerson with an existing ID
        insertedBankPerson = bankPersonRepository.saveAndFlush(bankPerson);
        BankPersonDTO bankPersonDTO = bankPersonMapper.toDto(bankPerson);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBankPersonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bankPersonDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BankPerson in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBankPeople() throws Exception {
        // Initialize the database
        bankPerson.setId(UUID.randomUUID().toString());
        insertedBankPerson = bankPersonRepository.saveAndFlush(bankPerson);

        // Get all the bankPersonList
        restBankPersonMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bankPerson.getId())))
            .andExpect(jsonPath("$.[*].citizenId").value(hasItem(DEFAULT_CITIZEN_ID)))
            .andExpect(jsonPath("$.[*].thaiName").value(hasItem(DEFAULT_THAI_NAME)))
            .andExpect(jsonPath("$.[*].englishName").value(hasItem(DEFAULT_ENGLISH_NAME)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getBankPerson() throws Exception {
        // Initialize the database
        bankPerson.setId(UUID.randomUUID().toString());
        insertedBankPerson = bankPersonRepository.saveAndFlush(bankPerson);

        // Get the bankPerson
        restBankPersonMockMvc
            .perform(get(ENTITY_API_URL_ID, bankPerson.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bankPerson.getId()))
            .andExpect(jsonPath("$.citizenId").value(DEFAULT_CITIZEN_ID))
            .andExpect(jsonPath("$.thaiName").value(DEFAULT_THAI_NAME))
            .andExpect(jsonPath("$.englishName").value(DEFAULT_ENGLISH_NAME))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingBankPerson() throws Exception {
        // Get the bankPerson
        restBankPersonMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBankPerson() throws Exception {
        // Initialize the database
        bankPerson.setId(UUID.randomUUID().toString());
        insertedBankPerson = bankPersonRepository.saveAndFlush(bankPerson);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bankPerson
        BankPerson updatedBankPerson = bankPersonRepository.findById(bankPerson.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBankPerson are not directly saved in db
        em.detach(updatedBankPerson);
        updatedBankPerson
            .citizenId(UPDATED_CITIZEN_ID)
            .thaiName(UPDATED_THAI_NAME)
            .englishName(UPDATED_ENGLISH_NAME)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        BankPersonDTO bankPersonDTO = bankPersonMapper.toDto(updatedBankPerson);

        restBankPersonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bankPersonDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bankPersonDTO))
            )
            .andExpect(status().isOk());

        // Validate the BankPerson in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBankPersonToMatchAllProperties(updatedBankPerson);
    }

    @Test
    @Transactional
    void putNonExistingBankPerson() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bankPerson.setId(UUID.randomUUID().toString());

        // Create the BankPerson
        BankPersonDTO bankPersonDTO = bankPersonMapper.toDto(bankPerson);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankPersonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bankPersonDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bankPersonDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankPerson in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBankPerson() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bankPerson.setId(UUID.randomUUID().toString());

        // Create the BankPerson
        BankPersonDTO bankPersonDTO = bankPersonMapper.toDto(bankPerson);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankPersonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bankPersonDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankPerson in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBankPerson() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bankPerson.setId(UUID.randomUUID().toString());

        // Create the BankPerson
        BankPersonDTO bankPersonDTO = bankPersonMapper.toDto(bankPerson);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankPersonMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bankPersonDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BankPerson in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBankPersonWithPatch() throws Exception {
        // Initialize the database
        bankPerson.setId(UUID.randomUUID().toString());
        insertedBankPerson = bankPersonRepository.saveAndFlush(bankPerson);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bankPerson using partial update
        BankPerson partialUpdatedBankPerson = new BankPerson();
        partialUpdatedBankPerson.setId(bankPerson.getId());

        partialUpdatedBankPerson
            .citizenId(UPDATED_CITIZEN_ID)
            .englishName(UPDATED_ENGLISH_NAME)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restBankPersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankPerson.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBankPerson))
            )
            .andExpect(status().isOk());

        // Validate the BankPerson in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBankPersonUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBankPerson, bankPerson),
            getPersistedBankPerson(bankPerson)
        );
    }

    @Test
    @Transactional
    void fullUpdateBankPersonWithPatch() throws Exception {
        // Initialize the database
        bankPerson.setId(UUID.randomUUID().toString());
        insertedBankPerson = bankPersonRepository.saveAndFlush(bankPerson);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bankPerson using partial update
        BankPerson partialUpdatedBankPerson = new BankPerson();
        partialUpdatedBankPerson.setId(bankPerson.getId());

        partialUpdatedBankPerson
            .citizenId(UPDATED_CITIZEN_ID)
            .thaiName(UPDATED_THAI_NAME)
            .englishName(UPDATED_ENGLISH_NAME)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restBankPersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankPerson.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBankPerson))
            )
            .andExpect(status().isOk());

        // Validate the BankPerson in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBankPersonUpdatableFieldsEquals(partialUpdatedBankPerson, getPersistedBankPerson(partialUpdatedBankPerson));
    }

    @Test
    @Transactional
    void patchNonExistingBankPerson() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bankPerson.setId(UUID.randomUUID().toString());

        // Create the BankPerson
        BankPersonDTO bankPersonDTO = bankPersonMapper.toDto(bankPerson);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankPersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bankPersonDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bankPersonDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankPerson in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBankPerson() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bankPerson.setId(UUID.randomUUID().toString());

        // Create the BankPerson
        BankPersonDTO bankPersonDTO = bankPersonMapper.toDto(bankPerson);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankPersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bankPersonDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankPerson in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBankPerson() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bankPerson.setId(UUID.randomUUID().toString());

        // Create the BankPerson
        BankPersonDTO bankPersonDTO = bankPersonMapper.toDto(bankPerson);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankPersonMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(bankPersonDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BankPerson in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBankPerson() throws Exception {
        // Initialize the database
        bankPerson.setId(UUID.randomUUID().toString());
        insertedBankPerson = bankPersonRepository.saveAndFlush(bankPerson);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the bankPerson
        restBankPersonMockMvc
            .perform(delete(ENTITY_API_URL_ID, bankPerson.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return bankPersonRepository.count();
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

    protected BankPerson getPersistedBankPerson(BankPerson bankPerson) {
        return bankPersonRepository.findById(bankPerson.getId()).orElseThrow();
    }

    protected void assertPersistedBankPersonToMatchAllProperties(BankPerson expectedBankPerson) {
        assertBankPersonAllPropertiesEquals(expectedBankPerson, getPersistedBankPerson(expectedBankPerson));
    }

    protected void assertPersistedBankPersonToMatchUpdatableProperties(BankPerson expectedBankPerson) {
        assertBankPersonAllUpdatablePropertiesEquals(expectedBankPerson, getPersistedBankPerson(expectedBankPerson));
    }
}
