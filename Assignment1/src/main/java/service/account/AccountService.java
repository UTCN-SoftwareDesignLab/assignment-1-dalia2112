package service.account;

import model.Account;
import model.Book;
import repository.EntityNotFoundException;

import java.util.List;
import java.util.Vector;

public interface AccountService {

    List<Account> findAll();

    Account findById(Long id) throws EntityNotFoundException;
    Account findByOwner(Long id) throws EntityNotFoundException;

    boolean save(Account account);

    void updateAccount(Long id,int col,String newval);

    void deleteAccount(Long id);

    public Vector<Vector<String>> getAllAccountsTable();

    //int transferMoney(Long id) throws EntityNotFoundException;
}
