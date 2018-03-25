package repository.client;

import model.Client;
import repository.EntityNotFoundException;

import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Vector;

public interface ClientRepository {
    List<Client> findAll();

    Client findById(Long id) throws EntityNotFoundException;
    Client findByName(String name) throws EntityNotFoundException;

    boolean save(Client client);

    void removeAll();
    Vector<Vector<String>> getAllClientsTable();
    void deleteClient(Long id);
    void updateClient(Long id,int col,String newval);
}
