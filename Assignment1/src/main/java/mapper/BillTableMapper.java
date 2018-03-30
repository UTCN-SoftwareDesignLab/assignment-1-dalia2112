package mapper;

import model.Bill;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class BillTableMapper {

    private List<Bill> bills;

    public BillTableMapper() {
        bills = new ArrayList<>();
    }

    public void setBills(List<Bill> bills) {
        this.bills = bills;
    }

    public Vector<Vector<String>> formatBillTable() {
        Vector<Vector<String>> billsVect = new Vector<>();
        for (Bill bill : bills) {
            Vector<String> data = new Vector<>();
            data.add(bill.getCode());
            data.add(bill.getTitle());
            data.add(String.valueOf(bill.getPrice()));
            data.add(String.valueOf(bill.getClientId()));
            billsVect.add(data);
        }
        return billsVect;
    }

    public String getBillCode(int row){
        return bills.get(row).getCode();
    }
}
