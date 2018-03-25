package service.client;

import model.Account;
import model.Client;
import repository.EntityNotFoundException;

import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Vector;

public interface ClientService {

    List<Client> findAll();

    Client findById(Long id) throws EntityNotFoundException;
    Client findByName(String name) throws EntityNotFoundException;

    boolean save(Client client);
    Vector<Vector<String>> getAllClientsTable();
    void deleteClient(Long id);
    void updateClient(Long id,int col,String newval);


}
