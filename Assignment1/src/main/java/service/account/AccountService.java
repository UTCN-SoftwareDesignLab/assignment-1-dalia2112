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


    void transferMoney(Long idAcc1, Long idAcc2, float sum);


    List<Bill> findBillByOwner(long id);


    void payBill(long accId, String code);

    Bill findBillByCode(String code);
}
