package service.account;

import model.Account;
import model.Book;
import repository.EntityNotFoundException;
import repository.account.AccountRepository;

import java.util.Calendar;
import java.util.Date;
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

    public void updateAccount(Long id,int col,String newval){
        repository.updateAccount(id,col,newval);
    }

    public void deleteAccount(Long id){
        repository.deleteAccount(id);
    }

    public Vector<Vector<String>> getAllAccountsTable(List<Account> a){
        return repository.getAllAccountsTable(a);
    }

    public void transferMoney(Long idAcc1,Long idAcc2,float sum){
        repository.transferMoney(idAcc1,idAcc2,sum);
    }

    public Vector<Vector<String>> writeAccountTable(List<Account> data){
        Vector<Vector<String>> tabl=getAllAccountsTable(data);
        return tabl;
    }



}
