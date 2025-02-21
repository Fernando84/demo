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

import static lets.bank.domain.BankTransactionAsserts.*;
import static lets.bank.web.rest.TestUtil.createUpdateProxyForBean;
import static lets.bank.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import lets.bank.IntegrationTest;
import lets.bank.domain.BankTransaction;
import lets.bank.repository.BankTransactionRepository;
import lets.bank.service.dto.BankTransactionDTO;
import lets.bank.service.mapper.BankTransactionMapper;
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
 * Integration tests for the {@link BankTransactionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BankTransactionResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CHANNEL = "AAAAAAAAAA";
    private static final String UPDATED_CHANNEL = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_BALANCE = new BigDecimal(1);
    private static final BigDecimal UPDATED_BALANCE = new BigDecimal(2);

    private static final String DEFAULT_TARGET_ACCOUNT_ID = "AAAAAAAAAA";
    private static final String UPDATED_TARGET_ACCOUNT_ID = "BBBBBBBBBB";

    private static final Instant DEFAULT_TRANSACTION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TRANSACTION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_REMARK = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/bank-transactions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BankTransactionRepository bankTransactionRepository;

    @Autowired
    private BankTransactionMapper bankTransactionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBankTransactionMockMvc;

    private BankTransaction bankTransaction;

    private BankTransaction insertedBankTransaction;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankTransaction createEntity() {
        return new BankTransaction()
            .id(UUID.randomUUID().toString())
            .code(DEFAULT_CODE)
            .channel(DEFAULT_CHANNEL)
            .amount(DEFAULT_AMOUNT)
            .balance(DEFAULT_BALANCE)
            .targetAccountId(DEFAULT_TARGET_ACCOUNT_ID)
            .transactionDate(DEFAULT_TRANSACTION_DATE)
            .remark(DEFAULT_REMARK)
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
    public static BankTransaction createUpdatedEntity() {
        return new BankTransaction()
            .id(UUID.randomUUID().toString())
            .code(UPDATED_CODE)
            .channel(UPDATED_CHANNEL)
            .amount(UPDATED_AMOUNT)
            .balance(UPDATED_BALANCE)
            .targetAccountId(UPDATED_TARGET_ACCOUNT_ID)
            .transactionDate(UPDATED_TRANSACTION_DATE)
            .remark(UPDATED_REMARK)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
    }

    @BeforeEach
    public void initTest() {
        bankTransaction = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedBankTransaction != null) {
            bankTransactionRepository.delete(insertedBankTransaction);
            insertedBankTransaction = null;
        }
    }

    @Test
    @Transactional
    void createBankTransaction() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the BankTransaction
        BankTransactionDTO bankTransactionDTO = bankTransactionMapper.toDto(bankTransaction);
        var returnedBankTransactionDTO = om.readValue(
            restBankTransactionMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bankTransactionDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            BankTransactionDTO.class
        );

        // Validate the BankTransaction in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedBankTransaction = bankTransactionMapper.toEntity(returnedBankTransactionDTO);
        assertBankTransactionUpdatableFieldsEquals(returnedBankTransaction, getPersistedBankTransaction(returnedBankTransaction));

        insertedBankTransaction = returnedBankTransaction;
    }

    @Test
    @Transactional
    void createBankTransactionWithExistingId() throws Exception {
        // Create the BankTransaction with an existing ID
        insertedBankTransaction = bankTransactionRepository.saveAndFlush(bankTransaction);
        BankTransactionDTO bankTransactionDTO = bankTransactionMapper.toDto(bankTransaction);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBankTransactionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bankTransactionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BankTransaction in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBankTransactions() throws Exception {
        // Initialize the database
        bankTransaction.setId(UUID.randomUUID().toString());
        insertedBankTransaction = bankTransactionRepository.saveAndFlush(bankTransaction);

        // Get all the bankTransactionList
        restBankTransactionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bankTransaction.getId())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].channel").value(hasItem(DEFAULT_CHANNEL)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(sameNumber(DEFAULT_AMOUNT))))
            .andExpect(jsonPath("$.[*].balance").value(hasItem(sameNumber(DEFAULT_BALANCE))))
            .andExpect(jsonPath("$.[*].targetAccountId").value(hasItem(DEFAULT_TARGET_ACCOUNT_ID)))
            .andExpect(jsonPath("$.[*].transactionDate").value(hasItem(DEFAULT_TRANSACTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getBankTransaction() throws Exception {
        // Initialize the database
        bankTransaction.setId(UUID.randomUUID().toString());
        insertedBankTransaction = bankTransactionRepository.saveAndFlush(bankTransaction);

        // Get the bankTransaction
        restBankTransactionMockMvc
            .perform(get(ENTITY_API_URL_ID, bankTransaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bankTransaction.getId()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.channel").value(DEFAULT_CHANNEL))
            .andExpect(jsonPath("$.amount").value(sameNumber(DEFAULT_AMOUNT)))
            .andExpect(jsonPath("$.balance").value(sameNumber(DEFAULT_BALANCE)))
            .andExpect(jsonPath("$.targetAccountId").value(DEFAULT_TARGET_ACCOUNT_ID))
            .andExpect(jsonPath("$.transactionDate").value(DEFAULT_TRANSACTION_DATE.toString()))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingBankTransaction() throws Exception {
        // Get the bankTransaction
        restBankTransactionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBankTransaction() throws Exception {
        // Initialize the database
        bankTransaction.setId(UUID.randomUUID().toString());
        insertedBankTransaction = bankTransactionRepository.saveAndFlush(bankTransaction);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bankTransaction
        BankTransaction updatedBankTransaction = bankTransactionRepository.findById(bankTransaction.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBankTransaction are not directly saved in db
        em.detach(updatedBankTransaction);
        updatedBankTransaction
            .code(UPDATED_CODE)
            .channel(UPDATED_CHANNEL)
            .amount(UPDATED_AMOUNT)
            .balance(UPDATED_BALANCE)
            .targetAccountId(UPDATED_TARGET_ACCOUNT_ID)
            .transactionDate(UPDATED_TRANSACTION_DATE)
            .remark(UPDATED_REMARK)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        BankTransactionDTO bankTransactionDTO = bankTransactionMapper.toDto(updatedBankTransaction);

        restBankTransactionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bankTransactionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bankTransactionDTO))
            )
            .andExpect(status().isOk());

        // Validate the BankTransaction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBankTransactionToMatchAllProperties(updatedBankTransaction);
    }

    @Test
    @Transactional
    void putNonExistingBankTransaction() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bankTransaction.setId(UUID.randomUUID().toString());

        // Create the BankTransaction
        BankTransactionDTO bankTransactionDTO = bankTransactionMapper.toDto(bankTransaction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankTransactionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bankTransactionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bankTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankTransaction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBankTransaction() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bankTransaction.setId(UUID.randomUUID().toString());

        // Create the BankTransaction
        BankTransactionDTO bankTransactionDTO = bankTransactionMapper.toDto(bankTransaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankTransactionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bankTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankTransaction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBankTransaction() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bankTransaction.setId(UUID.randomUUID().toString());

        // Create the BankTransaction
        BankTransactionDTO bankTransactionDTO = bankTransactionMapper.toDto(bankTransaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankTransactionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bankTransactionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BankTransaction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBankTransactionWithPatch() throws Exception {
        // Initialize the database
        bankTransaction.setId(UUID.randomUUID().toString());
        insertedBankTransaction = bankTransactionRepository.saveAndFlush(bankTransaction);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bankTransaction using partial update
        BankTransaction partialUpdatedBankTransaction = new BankTransaction();
        partialUpdatedBankTransaction.setId(bankTransaction.getId());

        partialUpdatedBankTransaction
            .code(UPDATED_CODE)
            .amount(UPDATED_AMOUNT)
            .remark(UPDATED_REMARK)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restBankTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankTransaction.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBankTransaction))
            )
            .andExpect(status().isOk());

        // Validate the BankTransaction in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBankTransactionUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBankTransaction, bankTransaction),
            getPersistedBankTransaction(bankTransaction)
        );
    }

    @Test
    @Transactional
    void fullUpdateBankTransactionWithPatch() throws Exception {
        // Initialize the database
        bankTransaction.setId(UUID.randomUUID().toString());
        insertedBankTransaction = bankTransactionRepository.saveAndFlush(bankTransaction);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bankTransaction using partial update
        BankTransaction partialUpdatedBankTransaction = new BankTransaction();
        partialUpdatedBankTransaction.setId(bankTransaction.getId());

        partialUpdatedBankTransaction
            .code(UPDATED_CODE)
            .channel(UPDATED_CHANNEL)
            .amount(UPDATED_AMOUNT)
            .balance(UPDATED_BALANCE)
            .targetAccountId(UPDATED_TARGET_ACCOUNT_ID)
            .transactionDate(UPDATED_TRANSACTION_DATE)
            .remark(UPDATED_REMARK)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restBankTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankTransaction.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBankTransaction))
            )
            .andExpect(status().isOk());

        // Validate the BankTransaction in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBankTransactionUpdatableFieldsEquals(
            partialUpdatedBankTransaction,
            getPersistedBankTransaction(partialUpdatedBankTransaction)
        );
    }

    @Test
    @Transactional
    void patchNonExistingBankTransaction() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bankTransaction.setId(UUID.randomUUID().toString());

        // Create the BankTransaction
        BankTransactionDTO bankTransactionDTO = bankTransactionMapper.toDto(bankTransaction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bankTransactionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bankTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankTransaction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBankTransaction() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bankTransaction.setId(UUID.randomUUID().toString());

        // Create the BankTransaction
        BankTransactionDTO bankTransactionDTO = bankTransactionMapper.toDto(bankTransaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bankTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankTransaction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBankTransaction() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bankTransaction.setId(UUID.randomUUID().toString());

        // Create the BankTransaction
        BankTransactionDTO bankTransactionDTO = bankTransactionMapper.toDto(bankTransaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankTransactionMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(bankTransactionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BankTransaction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBankTransaction() throws Exception {
        // Initialize the database
        bankTransaction.setId(UUID.randomUUID().toString());
        insertedBankTransaction = bankTransactionRepository.saveAndFlush(bankTransaction);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the bankTransaction
        restBankTransactionMockMvc
            .perform(delete(ENTITY_API_URL_ID, bankTransaction.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return bankTransactionRepository.count();
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

    protected BankTransaction getPersistedBankTransaction(BankTransaction bankTransaction) {
        return bankTransactionRepository.findById(bankTransaction.getId()).orElseThrow();
    }

    protected void assertPersistedBankTransactionToMatchAllProperties(BankTransaction expectedBankTransaction) {
        assertBankTransactionAllPropertiesEquals(expectedBankTransaction, getPersistedBankTransaction(expectedBankTransaction));
    }

    protected void assertPersistedBankTransactionToMatchUpdatableProperties(BankTransaction expectedBankTransaction) {
        assertBankTransactionAllUpdatablePropertiesEquals(expectedBankTransaction, getPersistedBankTransaction(expectedBankTransaction));
    }
}
