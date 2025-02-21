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
import lets.bank.domain.BankAccount;
import lets.bank.domain.User;
import lets.bank.service.dto.BankAccountDTO;
import lets.bank.service.dto.BankStatementListDTO;
import lets.bank.service.dto.BankTransactionDTO;
import lets.bank.service.dto.CreateAccountDTO;
import lets.bank.service.dto.MoneyDepositDTO;
import lets.bank.service.dto.MoneyTransferDTO;
import lets.bank.service.dto.OnlineRegisterDTO;
import lets.support.DataResultSupport;
import lets.support.ResultSupport;
import lets.support.concurrent.locks.LockService;
import lets.support.tx.TxManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankService {

  public @Autowired LockService lockService;
  public @Autowired TxManager txManager;

  /**
   * 1. Online Registration
   * <p>
   * Only new PERSON can register by input both email and password. Then they need
   * to fill-in personal information including citizen id, Thai name, and English
   * name and PIN 6 digits number.
   * 
   * @param dto
   * @return
   */
  public ResultSupport onlineRegister(OnlineRegisterDTO dto) {
    ResultSupport result = new ResultSupport();
    try {
      // validate input: email,password,PIN etc.
      // check email if existed
      // check citizen id if existed
      ResultSupport lock = this.lockService.lock(OnlineRegisterDTO.class, dto.getCitizenId(),
          new Runnable() {

            @Override
            public void run() {
              ResultSupport tx = onlineRegisterUnitOfWork(dto);
              tx.checkState();
            }

          });
      lock.checkState();
    } catch (Throwable e) {
      result.onException(e);
    }
    return result;
  }

  private ResultSupport onlineRegisterUnitOfWork(OnlineRegisterDTO dto) {
    ResultSupport tx = this.txManager.tx(new Runnable() {

      @Override
      public void run() {
        // Create User,
        // Create BankPerson
        // Create BankCustomer
      }
    });
    return tx;
  }

  /**
   * 2. Creating a new account
   * <p>
   * Both CUSTOMER and PERSON can go to the bank asking TELLER to create a new
   * account. Teller can create a new account using his/her citizen id, Thai name,
   * and English name with or without a deposit. System generates unique “account
   * number” with 7 numeric characters – for example “7777777” that will be use as
   * a reference for deposit/money transfer
   * 
   * @param dto
   * @return
   */
  public ResultSupport createAccount(User teller, CreateAccountDTO dto) {
    ResultSupport result = new ResultSupport();
    try {
      // validate input
      // check citizen id if existed
      // Create User,
      // Create BankPerson
      // Create BankCustomer
      // Create BankAccount
    } catch (Throwable e) {
      result.onException(e);
    }
    return result;
  }

  /**
   * 3. Money Deposit
   * <p>
   * Only TELLER can deposit money to an existing account number. The amount must
   * be 1 THB or more
   * 
   * @param dto
   * @return
   */
  public ResultSupport moneyDeposit(User teller, MoneyDepositDTO dto) {
    ResultSupport result = new ResultSupport();
    try {
      // validate input
      // check citizen id if existed
      // Create User,
      // Create BankPerson
      // Create BankCustomer
      // Create BankAccount
    } catch (Throwable e) {
      result.onException(e);
    }
    return result;
  }

  /**
   * 4.Account Information
   * <p>
   * Only CUSTOMER can login and see his/her account (if the teller created)
   * 
   * @param customer
   * @param accountId
   * @return
   */
  public DataResultSupport<BankAccountDTO> accountInformation(User customer, String accountId) {
    DataResultSupport<BankAccountDTO> result = new DataResultSupport<BankAccountDTO>();
    try {
      // validate input
      // return BankAccount of Customer
    } catch (Throwable e) {
      result.onException(e);
    }
    return result;
  }

  /**
   * 4.Account Information
   * <p>
   * Only CUSTOMER can login and see his/her account (if the teller created)
   */
  public DataResultSupport<List<BankAccountDTO>> accountInformationList(User customer,
      String accountId) {
    DataResultSupport<List<BankAccountDTO>> result = new DataResultSupport<List<BankAccountDTO>>();
    try {
      // validate input
      // return list of BankAccount
    } catch (Throwable e) {
      result.onException(e);
    }
    return result;
  }

  /**
   * 5.Money Transfer
   * <p>
   * Only CUSTOMER can transfer money from his/her own account to any other
   * existing account after login to system with PIN confirmation.
   * 
   * @return
   */
  public ResultSupport moneyTransfer(User customer, MoneyTransferDTO dto) {
    ResultSupport result = new ResultSupport();
    try {
      // validate input
      // load BankAccount
      BankAccountDTO src = new BankAccountDTO();
      BankAccountDTO target = new BankAccountDTO();
      // validate account and balance
      // lock src and target account
      ResultSupport lockSrc = this.lockService.lock(BankAccount.class, src.getAccountNumber(),
          new Runnable() {

            @Override
            public void run() {
              ResultSupport lockTarget = lockService.lock(BankAccount.class,
                  target.getAccountNumber(), new Runnable() {

                    @Override
                    public void run() {
                      ResultSupport tx = moneyTransferUnitOfWork(customer, dto, src, target);
                      tx.checkState();
                    }

                  });
              lockTarget.checkState();
            }

          });
      lockSrc.checkState();

    } catch (Throwable e) {
      result.onException(e);
    }
    return result;
  }

  private ResultSupport moneyTransferUnitOfWork(User customer, MoneyTransferDTO dto,
      BankAccountDTO src, BankAccountDTO target) {
    ResultSupport tx = this.txManager.tx(new Runnable() {

      @Override
      public void run() {
        // money transfer from src to target
        // update balance
        // create bank transaction
      }
    });
    return tx;
  }

  /**
   * 5. Bank Statement
   * <p>
   * Only CUSTOMER can ask for his/her bank statement of a specific month after
   * login to the system with PIN confirmation. The list of transaction must be
   * displayed from past to present.
   * 
   * @param customer
   * @return
   */
  public DataResultSupport<List<BankTransactionDTO>> bankStatementList(User customer,
      BankStatementListDTO dto) {
    DataResultSupport<List<BankTransactionDTO>> result = new DataResultSupport<List<BankTransactionDTO>>();
    try {
      //validate input.
      // The list of transaction must be from past to present. 
    } catch (Throwable e) {
      result.onException(e);
    }
    return result;
  }
}
