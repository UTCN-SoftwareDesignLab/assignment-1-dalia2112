package service.account;

import model.Account;
import repository.account.AccountRepository;

import java.util.List;

public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;

    public AccountServiceImpl(AccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Account> findAll() {
        return repository.findAll();
    }

    @Override
    public Account findById(Long id) {//throws EntityNotFoundException {
        return repository.findById(id);
    }

    public List<Account> findByOwner(Long id) {//throws EntityNotFoundException {
        return repository.findByOwner(id);
    }

    @Override
    public boolean save(Account account) {
        return repository.save(account);
    }  //CREATE

    public void updateAccount(Long id, String col, String newval) {
        repository.updateAccount(id, col, newval);
    }

    public void deleteAccount(Long id) {
        repository.deleteAccount(id);
    }

    public void transferMoney(Long idAcc1, Long idAcc2, float sum) {
        Account account1 = findById(idAcc1);
        Account account2 = findById(idAcc2);
        float sumA1 = account1.getAmount() - sum;
        float sumA2 = account2.getAmount() + sum;
        repository.transferMoney(idAcc1, idAcc2, sumA1, sumA2);
    }


}
