package mapper;

import database.Constants;
import model.Account;
import model.Bill;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public class AccountTableMapper {


    public Vector<Vector<String>> formatAccountTable(List<Account> accounts) {
        Vector<Vector<String>> accountsVect = new Vector<>();
        for (Account account : accounts) {
            Vector<String> data = new Vector<>();
            for (String column : Constants.Columns.ACCOUNT_COLS) {
                data.add(getValueAtColumn(column, account));
            }
            accountsVect.add(data);
        }
        return accountsVect;
    }

    public String getValueAtColumn(String column, Account account) {
        switch (column) {
            case "Id":
                return account.getId().toString();
            case "Type":
                return account.getType();
            case "Amount":
                return account.getAmount().toString();
            case "Date of creation":
                return account.getDate_of_creation().toString();
            case "Owner Id":
                return account.getOwnerId().toString();
        }
        return "";
    }

    public long getAccountId(List<Account> accounts, int row) {
        return accounts.get(row).getId();
    }


    public float getAccountAmount(List<Account> accounts, int row) {
        return accounts.get(row).getAmount();
    }


    public long getAccountOwnerId(List<Account> accounts, int row) {
        return accounts.get(row).getOwnerId();
    }


    public void updateAccountFromTable(List<Account> accounts, int row, int col, String val) {
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

}
