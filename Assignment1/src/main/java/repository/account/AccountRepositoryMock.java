package repository.account;

import model.Account;
import model.Bill;
import model.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AccountRepositoryMock implements AccountRepository {

    private List<Account> accounts;

    public AccountRepositoryMock() {
        accounts=new ArrayList<>();
    }

    @Override
    public List<Account> findAll() {
        return accounts;
    }

    @Override
    public Account findById(Long id) {
        List<Account> filteredClients = accounts.parallelStream()
                .filter(it -> it.getId().equals(id))
                .collect(Collectors.toList());
        if (filteredClients.size() > 0) {
            return filteredClients.get(0);
        }
        return null;
    }

    @Override
    public List<Account> findByOwner(Long id) {
        List<Account> filteredClients = accounts.parallelStream()
                .filter(it -> it.getOwnerId().equals(id))
                .collect(Collectors.toList());
        if (filteredClients.size() > 0) {
            return filteredClients;
        }
        return null;
    }

    @Override
    public boolean save(Account account) {
        return accounts.add(account);
    }

    @Override
    public void removeAll() {
        accounts.clear();
    }

    @Override
    public void updateAccount(Long id, String col, String newval) {
        List<Account> filteredAccounts = accounts.parallelStream()
                .filter(it -> it.getId().equals(id))
                .collect(Collectors.toList());
        switch (col) {
            case "Id":
                break;
            case "Type":
                break;
            case "Amount":
                filteredAccounts.get(0).setAmount(Float.parseFloat(newval));
                break;
            case "Date of creation":
                break;
            case "Owner Id":
                break;
        }
    }

    @Override
    public void deleteAccount(Long id) {

    }

    @Override
    public void transferMoney(Long idAcc1, Long idAcc2, float sumA1, float sumA2) {

    }

    @Override
    public List<Bill> findBillByOwner(long id) {
        return null;
    }

    @Override
    public void payBill(long accId, String code) {

    }

    @Override
    public Bill findBillByCode(String code) {
        return null;
    }
}
