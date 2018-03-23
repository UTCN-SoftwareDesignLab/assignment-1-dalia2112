package repository.account;

import model.Account;
import repository.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Alex on 07/03/2017.
 */
public class AccountRepositoryMock implements AccountRepository {

    private List<Account> accounts;

    public AccountRepositoryMock() {
        accounts = new ArrayList<>();
    }

    public List<Account> findAll() {
        return accounts;
    }

    public Account findById(Long id) throws EntityNotFoundException {
        List<Account> filteredAccounts = accounts.parallelStream()
                .filter(it -> it.getId().equals(id))
                .collect(Collectors.toList());
        if (filteredAccounts.size() > 0) {
            return filteredAccounts.get(0);
        }
        throw new EntityNotFoundException(id, Account.class.getSimpleName());
    }

    public Account findByOwner(Long id) throws EntityNotFoundException {
        List<Account> filteredAccounts = accounts.parallelStream()
                .filter(it -> it.getOwnerId().equals(id))
                .collect(Collectors.toList());
        if (filteredAccounts.size() > 0) {
            return filteredAccounts.get(0);
        }
        throw new EntityNotFoundException(id, Account.class.getSimpleName());
    }

    public boolean save(Account account) {
        return accounts.add(account);
    }

    @Override
    public void removeAll() {
        accounts.clear();
    }
}
