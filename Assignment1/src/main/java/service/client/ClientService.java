package service.client;

import model.Account;
import model.Client;
import model.validation.Notification;
import repository.EntityNotFoundException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Vector;

public interface ClientService {

    List<Client> findAll();

    Client findById(Long id);//throws EntityNotFoundException;

    Client findByName(String name) throws EntityNotFoundException;

    boolean save(Client client);


    void deleteClient(Long id);

    void updateClient(Long id, String col, String newval);


    DefaultComboBoxModel setOwnerCombo();


}
