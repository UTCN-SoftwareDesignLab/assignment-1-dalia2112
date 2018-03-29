package service.account;

import model.Account;
import model.Bill;
import repository.account.AccountRepository;

import java.util.List;
import java.util.Vector;

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

    public void updateAccount(Long id, int col, String newval) {
        repository.updateAccount(id, col, newval);
    }

    public void deleteAccount(Long id) {
        repository.deleteAccount(id);
    }

    public Vector<Vector<String>> getAllAccountsTable(List<Account> a) {
        return repository.getAllAccountsTable(a);
    }

    public Vector<Vector<String>> getAllBillsTable(List<Bill> a) {
        return repository.getAllBillsTable(a);
    }

    @Override
    public List<Bill> findBillByOwner(long id) {
        return repository.findBillByOwner(id);
    }

    public void transferMoney(Long idAcc1, Long idAcc2, float sum) {
        repository.transferMoney(idAcc1, idAcc2, sum);
    }

    public Vector<Vector<String>> writeAccountTable(List<Account> data) {
        Vector<Vector<String>> tabl = getAllAccountsTable(data);
        return tabl;
    }

    public Vector<Vector<String>> writeBillsTable(List<Bill> data) {
        Vector<Vector<String>> tabl = getAllBillsTable(data);
        return tabl;
    }

    public void payBill(long accId, String code) {
        repository.payBill(accId, code);
    }

    public Bill findBillByCode(String code) {
        return repository.findBillByCode(code);
    }
}
