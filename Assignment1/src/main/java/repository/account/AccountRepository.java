package repository.account;

import model.Account;
import model.Bill;

import java.util.List;
import java.util.Vector;

public interface AccountRepository {

    List<Account> findAll();

    Account findById(Long id);//throws EntityNotFoundException;

    List<Account> findByOwner(Long id);//throws EntityNotFoundException;

    boolean save(Account account);

    void removeAll();

    void updateAccount(Long id, int col, String newval);

    void deleteAccount(Long id);

    Vector<Vector<String>> getAllAccountsTable(List<Account> a);

    void transferMoney(Long idAcc1, Long idAcc2, float sum);

    Vector<Vector<String>> getAllBillsTable(List<Bill> a);

    List<Bill> findBillByOwner(long id);

    void payBill(long accId, String code);

    Bill findBillByCode(String code);
}
