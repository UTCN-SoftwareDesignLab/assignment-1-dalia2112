package repository.client;

import model.Client;
import model.validation.Notification;
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
//    Vector<Vector<String>> getAllClientsTable();
    void deleteClient(Long id);
    void updateClient(Long id,String col,String newval);


}
