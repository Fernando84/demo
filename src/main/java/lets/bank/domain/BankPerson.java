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
package lets.bank.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.springframework.data.domain.Persistable;

/**
 * A BankPerson.
 */
@Entity
@Table(name = "bank_person")
@JsonIgnoreProperties(value = { "new" })
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BankPerson implements Serializable, Persistable<String> {

    private static final long serialVersionUID = 1L;

    @Size(max = 64)
    @Id
    @Column(name = "id", length = 64)
    private String id;

    @Column(name = "citizen_id")
    private String citizenId;

    @Column(name = "thai_name")
    private String thaiName;

    @Column(name = "english_name")
    private String englishName;

    @Size(max = 64)
    @Column(name = "created_by", length = 64)
    private String createdBy;

    @Column(name = "created_date")
    private Instant createdDate;

    @Size(max = 64)
    @Column(name = "last_modified_by", length = 64)
    private String lastModifiedBy;

    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    @org.springframework.data.annotation.Transient
    @Transient
    private boolean isPersisted;

    @JsonIgnoreProperties(value = { "person" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "person")
    private BankCustomer bankCustomer;

    @JsonIgnoreProperties(value = { "person", "bankTransaction" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "person")
    private BankAccount bankAccount;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public BankPerson id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCitizenId() {
        return this.citizenId;
    }

    public BankPerson citizenId(String citizenId) {
        this.setCitizenId(citizenId);
        return this;
    }

    public void setCitizenId(String citizenId) {
        this.citizenId = citizenId;
    }

    public String getThaiName() {
        return this.thaiName;
    }

    public BankPerson thaiName(String thaiName) {
        this.setThaiName(thaiName);
        return this;
    }

    public void setThaiName(String thaiName) {
        this.thaiName = thaiName;
    }

    public String getEnglishName() {
        return this.englishName;
    }

    public BankPerson englishName(String englishName) {
        this.setEnglishName(englishName);
        return this;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public BankPerson createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public BankPerson createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public BankPerson lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public BankPerson lastModifiedDate(Instant lastModifiedDate) {
        this.setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @PostLoad
    @PostPersist
    public void updateEntityState() {
        this.setIsPersisted();
    }

    @org.springframework.data.annotation.Transient
    @Transient
    @Override
    public boolean isNew() {
        return !this.isPersisted;
    }

    public BankPerson setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    public BankCustomer getBankCustomer() {
        return this.bankCustomer;
    }

    public void setBankCustomer(BankCustomer bankCustomer) {
        if (this.bankCustomer != null) {
            this.bankCustomer.setPerson(null);
        }
        if (bankCustomer != null) {
            bankCustomer.setPerson(this);
        }
        this.bankCustomer = bankCustomer;
    }

    public BankPerson bankCustomer(BankCustomer bankCustomer) {
        this.setBankCustomer(bankCustomer);
        return this;
    }

    public BankAccount getBankAccount() {
        return this.bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        if (this.bankAccount != null) {
            this.bankAccount.setPerson(null);
        }
        if (bankAccount != null) {
            bankAccount.setPerson(this);
        }
        this.bankAccount = bankAccount;
    }

    public BankPerson bankAccount(BankAccount bankAccount) {
        this.setBankAccount(bankAccount);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BankPerson)) {
            return false;
        }
        return getId() != null && getId().equals(((BankPerson) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BankPerson{" +
            "id=" + getId() +
            ", citizenId='" + getCitizenId() + "'" +
            ", thaiName='" + getThaiName() + "'" +
            ", englishName='" + getEnglishName() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
