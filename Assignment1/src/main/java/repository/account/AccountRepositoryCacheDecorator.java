package repository.account;

import model.Account;
import repository.Cache;

import java.util.List;

public class AccountRepositoryCacheDecorator extends AccountRepositoryDecorator {
    private Cache<Account> cache;

    public AccountRepositoryCacheDecorator(AccountRepository accountRepository, Cache<Account> cache) {
        super(accountRepository);
        this.cache = cache;
    }

    @Override
    public List<Account> findAll() {
        if (cache.hasResult()) {
            return cache.load();
        }
        List<Account> accounts = decoratedRepository.findAll();
        cache.save(accounts);
        return accounts;
    }

    @Override
    public Account findById(Long id) {
        return decoratedRepository.findById(id);
    }

    @Override
    public List<Account> findByOwner(Long id) {
        return null;
    }

    @Override
    public boolean save(Account account) {
        cache.invalidateCache();
        return decoratedRepository.save(account);
    }

    @Override
    public void removeAll() {
        cache.invalidateCache();
        decoratedRepository.removeAll();
    }

    @Override
    public void updateAccount(Long id, String col, String newval) {
        cache.invalidateCache();
        decoratedRepository.updateAccount(id, col, newval);
    }

    @Override
    public void deleteAccount(Long id) {
        cache.invalidateCache();
        decoratedRepository.deleteAccount(id);
    }

    @Override
    public void transferMoney(Long idAcc1, Long idAcc2, float sumA1, float sumA2) {
        cache.invalidateCache();
        decoratedRepository.transferMoney(idAcc1, idAcc2, sumA1, sumA2);
    }


}
