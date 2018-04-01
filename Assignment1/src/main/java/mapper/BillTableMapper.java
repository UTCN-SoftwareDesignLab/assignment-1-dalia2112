package mapper;

import database.Constants;
import model.Account;
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

//    public Vector<Vector<String>> formatBillTable() {
//        Vector<Vector<String>> billsVect = new Vector<>();
//        for (Bill bill : bills) {
//            Vector<String> data = new Vector<>();
//            data.add(bill.getCode());
//            data.add(bill.getTitle());
//            data.add(String.valueOf(bill.getPrice()));
//            data.add(String.valueOf(bill.getClientId()));
//            billsVect.add(data);
//        }
//        return billsVect;
//    }

    public Vector<Vector<String>> formatBillTable() {
        Vector<Vector<String>> billsVect = new Vector<>();
        for (Bill bill : bills) {
            Vector<String> data = new Vector<>();
            for(String column: Constants.Columns.BILL_COLS){
                data.add(getValueAtColumn(column,bill));
            }
            billsVect.add(data);
        }
        return billsVect;
    }

    public String getValueAtColumn(String column, Bill bill) {
        switch (column) {
            case "Code":
                return bill.getCode();
            case "Title":
                return bill.getTitle();
            case "Price":
                return String.valueOf(bill.getPrice());
            case "Owner Id":
                return String.valueOf(bill.getClientId());
        }
        return "";
    }

    public String getBillCode(int row){
        return bills.get(row).getCode();
    }
}
