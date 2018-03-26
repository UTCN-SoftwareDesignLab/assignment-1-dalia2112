package repository.client;

import model.Client;
import model.builder.ClientBuilder;
import repository.EntityNotFoundException;
import repository.client.ClientRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ClientRepositoryMySQL implements ClientRepository {
    private final Connection connection;

    public ClientRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from client";
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                clients.add(getClientFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clients;
    }

    @Override
    public Client findById(Long id) throws EntityNotFoundException {
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from client where id=" + id;
            ResultSet rs = statement.executeQuery(sql);

            if (rs.next()) {
                return getClientFromResultSet(rs);
            } else {
                throw new EntityNotFoundException(id, Client.class.getSimpleName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new EntityNotFoundException(id, Client.class.getSimpleName());
        }
    }


    public Client findByName(String name) throws EntityNotFoundException {
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from client where name=" + name;
            ResultSet rs = statement.executeQuery(sql);

            if (rs.next()) {
                return getClientFromResultSet(rs);
            } else {
                throw new EntityNotFoundException(rs.getLong(0), Client.class.getSimpleName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new EntityNotFoundException((long) 0.0, Client.class.getSimpleName());
        }
    }
    @Override
    public boolean save(Client client) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT INTO client values (null, ?, ?, ?, ?)");
            insertStatement.setString(1, client.getName());
            insertStatement.setLong(2, client.getId_card_nr());
            insertStatement.setLong(3, client.getPers_num_code());
            insertStatement.setString(4, client.getAddress());
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
            String sql = "DELETE from client where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Client getClientFromResultSet(ResultSet rs) throws SQLException {
        return new ClientBuilder()
                .setID(rs.getLong("id"))
                .setName(rs.getString("name"))
                .setIdCard(rs.getLong("id_card_nr"))
                .setPersNumCode(rs.getLong("pers_num_code"))
                .setAddress(rs.getString("address"))
                .build();
    }

//    public Vector<Vector<String>> getAllClientsTable(){
//        String[][] t=new String[][]{};
//        Vector<Vector<String>> clients = new Vector<>();
//        int i=0;
//        for(Client c:findAll()){
//            Vector<String> data = new Vector<>();
//            data.add(c.getId().toString());
//            data.add(c.getName());
//            data.add(c.getId_card_nr().toString());
//            data.add(c.getPers_num_code().toString());
//            data.add(c.getAddress());
//            clients.add(data);
//        }
//        return clients;
//    }

        public void deleteClient(Long id){
            try {
                Statement statement = connection.createStatement();
                String sql = "DELETE from client where id="+id;
                statement.executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        public void updateClient(Long id,String column,String newval){

            try {
                Statement statement = connection.createStatement();
                String sql = "UPDATE client SET "+column+"='"+newval+"' where id="+id;
                statement.executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

}
