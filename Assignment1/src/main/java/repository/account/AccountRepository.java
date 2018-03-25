package repository.account;

import model.Account;
import model.Book;
import repository.EntityNotFoundException;

import java.util.List;
import java.util.Vector;

public interface AccountRepository {

    List<Account> findAll();

    Account findById(Long id) throws EntityNotFoundException;
    Account findByOwner(Long id) throws EntityNotFoundException;

    boolean save(Account account);

    void removeAll();

    void updateAccount(Long id,int col,String newval);

    void deleteAccount(Long id);

    public Vector<Vector<String>> getAllAccountsTable();
}
