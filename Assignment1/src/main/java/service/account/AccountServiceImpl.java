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
    public Account findById(Long id) throws EntityNotFoundException {
        return repository.findById(id);
    }
    public Account findByOwner(Long id) throws EntityNotFoundException {
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

    public Vector<Vector<String>> getAllAccountsTable(){
        return repository.getAllAccountsTable();
    }
   /* @Override
    public int getAgeOfBook(Long id) throws EntityNotFoundException {
        Book book = findById(id);
        Date publishedDate = book.getPublishedDate();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(publishedDate);
        int yearOfPublishing = calendar.get(Calendar.YEAR);
        calendar.setTime(new Date());
        int yearToday = calendar.get(Calendar.YEAR);

        return yearToday - yearOfPublishing;
    }*/

}
