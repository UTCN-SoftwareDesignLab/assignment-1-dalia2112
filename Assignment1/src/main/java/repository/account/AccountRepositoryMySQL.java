package repository.account;

import model.Account;
import model.Client;
import model.builder.AccountBuilder;
import model.validation.AccountValidator;
import repository.EntityNotFoundException;

import javax.swing.*;
import java.sql.*;
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
    public Account findById(Long id){// throws EntityNotFoundException {
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from account where id=" + id;
            ResultSet rs = statement.executeQuery(sql);

            if (rs.next()) {
                return getAccountFromResultSet(rs);
            } else {
//                throw new EntityNotFoundException(id, Account.class.getSimpleName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
//            throw new EntityNotFoundException(id, Account.class.getSimpleName());
        }
        return  null;
    }

    public List<Account> findByOwner(Long id){//} throws EntityNotFoundException {
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
                    .prepareStatement("INSERT INTO account values (null, ?, ?, ?, ?)");
            insertStatement.setString(1, account.getType());
            insertStatement.setFloat(2, account.getAmount());
            insertStatement.setDate(3, new java.sql.Date(account.getDate_of_creation().getTime()));
            insertStatement.setLong(4,account.getOwnerId());
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

    public void updateAccount(Long id,int col,String newval){
        String column="";
        switch (col) {
            case 0:
                JOptionPane.showMessageDialog(null,"Cannot change id!");
                break;
            case 1:
                column="type";
                break;
            case 2:
                column="amount";
                AccountValidator accountValidator=new AccountValidator();
                if(!accountValidator.validateTransfSum(Long.parseLong(newval),0,false)){
                    JOptionPane.showMessageDialog(null,accountValidator.getErrors());
                    return;
                }
                break;
            case 3:
                column="date_of_creation";
                break;
            case 4:
                column="ownerId";
                break;
            default: column="name";
        }
        try {
            Statement statement = connection.createStatement();
            String sql = "UPDATE account SET "+column+"='"+newval+"' where id="+id;
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAccount(Long id){
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from account where id="+id;
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Vector<Vector<String>> getAllAccountsTable(List<Account> acc){
        Vector<Vector<String>> accounts = new Vector<>();
        if(acc==null) JOptionPane.showMessageDialog(null,"getallAccTable is empty");
        for(Account c:acc){
            Vector<String> data = new Vector<>();
            data.add(c.getId().toString());
            data.add(c.getType());
            data.add(c.getAmount().toString());
            data.add(c.getDate_of_creation().toString());
            data.add(c.getOwnerId().toString());
            accounts.add(data);
        }
        return accounts;
    }


    public void transferMoney(Long idAcc1,Long idAcc2,float sum){
        Account a1=findById(idAcc1);
        Account a2=findById(idAcc2);
        AccountValidator accountValidator=new AccountValidator();
        if(!accountValidator.validateTransfSum(a1.getAmount(),sum,true)){
            JOptionPane.showMessageDialog(null,accountValidator.getErrors());
            return;
        }
        float sumA1=a1.getAmount()-sum;
        float sumA2=a2.getAmount()+sum;


        try {
            Statement statement = connection.createStatement();
            String sql1 = "UPDATE account SET amount='"+sumA1+"' where id="+idAcc1;
            statement.executeUpdate(sql1);

            String sql2 = "UPDATE account SET amount='"+sumA2+"' where id="+idAcc2;
            statement.executeUpdate(sql2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
