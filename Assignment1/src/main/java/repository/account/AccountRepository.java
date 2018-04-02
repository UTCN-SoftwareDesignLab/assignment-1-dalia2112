package repository.account;

import model.Account;

import java.util.List;

public interface AccountRepository {

    List<Account> findAll();

    Account findById(Long id);

    List<Account> findByOwner(Long id);

    boolean save(Account account);

    void removeAll();

    void updateAccount(Long id, String col, String newval);

    void deleteAccount(Long id);


    void transferMoney(Long idAcc1, Long idAcc2, float sumA1, float sumA2);

}
