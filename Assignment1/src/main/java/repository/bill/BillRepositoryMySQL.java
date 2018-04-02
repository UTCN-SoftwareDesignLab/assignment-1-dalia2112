package repository.bill;

import model.Account;
import model.Bill;
import model.builder.BillBuilder;
import model.validation.AccountValidator;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BillRepositoryMySQL implements BillRepository {
    private Connection connection;

    public BillRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Bill> findBillByOwner(long id) {
        List<Bill> accounts = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from bill where clientId=" + id;
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                accounts.add(getBillFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    @Override
    public void payBill(Account account, String code) {
        Bill bill = findBillByCode(code);
        AccountValidator accountValidator = new AccountValidator();
        float sumA1 = account.getAmount() - bill.getPrice();

        try {
            Statement statement = connection.createStatement();
            String sql1 = "UPDATE account SET amount='" + sumA1 + "' where id=" + account.getId();
            statement.executeUpdate(sql1);

            String sql2 = "DELETE from bill where `code`=\'" + code + "\'";
            statement.executeUpdate(sql2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Bill findBillByCode(String code) {
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from bill where `code`=\'" + code + "\'";
            ResultSet rs = statement.executeQuery(sql);

            if (rs.next()) {
                return getBillFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Bill getBillFromResultSet(ResultSet rs) throws SQLException {
        return new BillBuilder()
                .setCode(rs.getString("code"))
                .setTitle(rs.getString("title"))
                .setPrice(rs.getFloat("price"))
                .setClientId(rs.getLong("clientId"))
                .build();
    }

}
