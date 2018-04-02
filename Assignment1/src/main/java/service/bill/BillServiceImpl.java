package service.bill;

import model.Account;
import model.Bill;
import repository.account.AccountRepository;
import repository.bill.BillRepository;

import java.util.List;

public class BillServiceImpl implements BillService {

    private final BillRepository repository;

    public BillServiceImpl(BillRepository repository) {
        this.repository = repository;
    }
    @Override
    public List<Bill> findBillByOwner(long id) {
        return repository.findBillByOwner(id);
    }

    @Override
    public void payBill(Account account, String code) {
        repository.payBill(account, code);
    }

    @Override
    public Bill findBillByCode(String code) {
        return repository.findBillByCode(code);
    }
}
