package repository.account;

import database.Constants;
import model.Account;
import model.Bill;
import model.builder.AccountBuilder;
import model.builder.BillBuilder;
import model.validation.AccountValidator;
import org.joda.time.DateTime;
import repository.EntityNotFoundException;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class AccountRepositoryMySQL implements AccountRepository {
    private final Connection connection;

    public AccountRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from account";
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                accounts.add(getAccountFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return accounts;
    }

    @Override
    public Account findById(Long id) {// throws EntityNotFoundException {
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from account where id=" + id;
            ResultSet rs = statement.executeQuery(sql);

            if (rs.next()) {
                return getAccountFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Account> findByOwner(Long id) {//} throws EntityNotFoundException {
        List<Account> accounts = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from account where ownerID=" + id;
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                accounts.add(getAccountFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
//            throw new EntityNotFoundException(id, Account.class.getSimpleName());
        }
        return accounts;
    }

    @Override
    public boolean save(Account account) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT INTO account values (null, ?, ?, ?, ?)",
                            PreparedStatement.RETURN_GENERATED_KEYS
                    );
            insertStatement.setString(1, account.getType());
            insertStatement.setFloat(2, account.getAmount());
            insertStatement.setDate(3, new java.sql.Date(account.getDate_of_creation().getTime()));
            insertStatement.setLong(4, account.getOwnerId());
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from account where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Account getAccountFromResultSet(ResultSet rs) throws SQLException {
        return new AccountBuilder()
                .setId(rs.getLong("id"))
                .setType(rs.getString("type"))
                .setAmount(rs.getFloat("amount"))
                .setDate(new Date(rs.getDate("date_of_creation").getTime()))
                .setOwner(rs.getLong("ownerID"))
                .build();
    }

    private Bill getBillFromResultSet(ResultSet rs) throws SQLException {
        return new BillBuilder()
                .setCode(rs.getString("code"))
                .setTitle(rs.getString("title"))
                .setPrice(rs.getFloat("price"))
                .setClientId(rs.getLong("clientId"))
                .build();
    }

    public void updateAccount(Long id, String column, String newval) {
        try {
            Statement statement = connection.createStatement();
            String sql = "UPDATE account SET " + column + "='" + newval + "' where id=" + id;
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAccount(Long id) {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from account where id=" + id;
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void transferMoney(Long idAcc1, Long idAcc2, float sum) {
        Account a1 = findById(idAcc1);
        Account a2 = findById(idAcc2);
        float sumA1 = a1.getAmount() - sum;
        float sumA2 = a2.getAmount() + sum;


        try {
            Statement statement = connection.createStatement();
            String sql1 = "UPDATE account SET amount='" + sumA1 + "' where id=" + idAcc1;
            statement.executeUpdate(sql1);

            String sql2 = "UPDATE account SET amount='" + sumA2 + "' where id=" + idAcc2;
            statement.executeUpdate(sql2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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

    public Bill findBillByCode(String code) {// throws EntityNotFoundException {
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


    public void payBill(long accId, String code) {
        JOptionPane.showMessageDialog(null, "Account ID= " + accId + " bill code= " + code);
        Account a = findById(accId);
        Bill b = findBillByCode(code);
        AccountValidator accountValidator = new AccountValidator();
        float k = a.getAmount();
        float l = b.getPrice();
        float sumA1 = a.getAmount() - b.getPrice();


        try {
            Statement statement = connection.createStatement();
            String sql1 = "UPDATE account SET amount='" + sumA1 + "' where id=" + accId;
            statement.executeUpdate(sql1);

            String sql2 = "DELETE from bill where `code`=\'" + code + "\'";
            statement.executeUpdate(sql2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
