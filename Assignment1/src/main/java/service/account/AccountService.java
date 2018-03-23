package service.account;

import model.Account;
import model.Book;
import repository.EntityNotFoundException;

import java.util.List;

public interface AccountService {

    List<Account> findAll();

    Account findById(Long id) throws EntityNotFoundException;
    Account findByOwner(Long id) throws EntityNotFoundException;

    boolean save(Account account);

    //int transferMoney(Long id) throws EntityNotFoundException;
}
