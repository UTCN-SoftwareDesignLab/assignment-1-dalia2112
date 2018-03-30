package service.account;

import model.Account;
import model.Bill;
import model.Book;
import repository.EntityNotFoundException;

import java.util.List;
import java.util.Vector;

public interface AccountService {

    List<Account> findAll();

    Account findById(Long id);// throws EntityNotFoundException;

    List<Account> findByOwner(Long id);//throws EntityNotFoundException;

    boolean save(Account account);

    void updateAccount(Long id, String col, String newval);

    void deleteAccount(Long id);

//    Vector<Vector<String>> getAllAccountsTable(List<Account> a);

    void transferMoney(Long idAcc1, Long idAcc2, float sum);

//    Vector<Vector<String>> writeAccountTable(List<Account> data);

//    Vector<Vector<String>> getAllBillsTable(List<Bill> a);

    public List<Bill> findBillByOwner(long id);

//    Vector<Vector<String>> writeBillsTable(List<Bill> data);

    void payBill(long accId, String code);

    Bill findBillByCode(String code);
}
