package mapper;

import model.Account;
import model.Bill;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public class AccountTableMapper {

    private List<Account> accounts;

    public AccountTableMapper() {
        accounts = new ArrayList<>();
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public Vector<Vector<String>> formatAccountTable() {
        Vector<Vector<String>> accountsVect = new Vector<>();
        for (Account account : accounts) {
            Vector<String> data = new Vector<>();
            data.add(account.getId().toString());
            data.add(account.getType());
            data.add(account.getAmount().toString());
            data.add(account.getDate_of_creation().toString());
            data.add(account.getOwnerId().toString());
            accountsVect.add(data);
        }
        return accountsVect;
    }


    public long getAccountId(int row) {
        return accounts.get(row).getId();
    }

    public String getAccountType(int row) {
        return accounts.get(row).getType();
    }

    public float getAccountAmount(int row) {
        return accounts.get(row).getAmount();
    }

    public Date getAccountDate(int row) {
        return accounts.get(row).getDate_of_creation();
    }

    public long getAccountOwnerId(int row) {
        return accounts.get(row).getOwnerId();
    }


    public void deleteAccountFromTable(int row) {
        accounts.remove(row);
    }

    public void updateAccountFromTable(int row, int col, String val) {
        switch (col) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                accounts.get(row).setAmount(Float.parseFloat(val));
            case 3:
                break;
            case 4:
                break;
        }
    }

    public int tableSize() {
        return accounts.size();
    }

    public String getColumnName(int column) {
        String colName = "";
        switch (column) {
            case 0:
                colName = "id";
                break;
            case 1:
                colName = "type";
                break;
            case 2:
                colName = "amount";
                break;
            case 3:
                colName = "date_of_creation";
                break;
            case 4:
                colName = "ownerId";
                break;
        }
        return colName;
    }

    public Vector<Vector<String>> formatBillTable(List<Bill> allBills) {
        Vector<Vector<String>> bills = new Vector<>();
        for (Bill bill : allBills) {
            Vector<String> data = new Vector<>();
            data.add(bill.getCode());
            data.add(bill.getTitle());
            data.add((String.valueOf(bill.getPrice())));
            data.add(String.valueOf(bill.getClientId()));
            bills.add(data);
        }
        return bills;
    }
}
