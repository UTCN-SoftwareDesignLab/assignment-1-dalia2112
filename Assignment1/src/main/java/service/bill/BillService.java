package service.bill;

import model.Account;
import model.Bill;

import java.util.List;

public interface BillService {

    List<Bill> findBillByOwner(long id);


    void payBill(Account account, String code);

    Bill findBillByCode(String code);
}
