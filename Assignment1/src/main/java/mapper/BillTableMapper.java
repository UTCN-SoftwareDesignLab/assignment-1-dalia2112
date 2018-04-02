package mapper;

import database.Constants;
import model.Account;
import model.Bill;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class BillTableMapper {


    public Vector<Vector<String>> formatBillTable(List<Bill> bills) {
        Vector<Vector<String>> billsVect = new Vector<>();
        for (Bill bill : bills) {
            Vector<String> data = new Vector<>();
            for (String column : Constants.Columns.BILL_COLS) {
                data.add(getValueAtColumn(column, bill));
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

    public String getBillCode(List<Bill> bills, int row) {
        return bills.get(row).getCode();
    }
}
