package service.client;

import model.Client;
import model.validation.ClientValidator;
import repository.EntityNotFoundException;
import repository.client.ClientRepository;

import javax.swing.*;
import java.util.List;
import java.util.Vector;

public class ClientServiceImpl implements ClientService {

    private final ClientRepository repository;

    public ClientServiceImpl(ClientRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Client> findAll() {
        return repository.findAll();
    }

    @Override
    public Client findById(Long id) {//throws EntityNotFoundException {
        return repository.findById(id);
    }

    public Client findByName(String name) throws EntityNotFoundException {
        return repository.findByName(name);
    }

    @Override
    public boolean save(Client client) {
        return repository.save(client);
    }

    public Vector<Vector<String>> getAllClientsTable() {
        Vector<Vector<String>> clients = new Vector<>();
        for (Client c : findAll()) {
            Vector<String> data = new Vector<>();
            data.add(c.getId().toString());
            data.add(c.getName());
            data.add(c.getId_card_nr().toString());
            data.add(c.getPers_num_code().toString());
            data.add(c.getAddress());
            clients.add(data);
        }
        return clients;
    }

    public void deleteClient(Long id) {
        repository.deleteClient(id);
    }

    public void updateClient(Long id, int col, String newval) {

        String column = "";
        switch (col) {
            case 0:
                break;
            case 1:
                column = "name";
                break;
            case 2:
                column = "id_card_nr";
                ClientValidator clientValidator = new ClientValidator();
                if (!clientValidator.validateIdCardNr(Long.parseLong(newval))) {
                    return;
                }
                break;
            case 3:
                column = "pers_num_code";
                ClientValidator clientValidato = new ClientValidator();
                if (!clientValidato.validatePersNumCode(Long.parseLong(newval))) {
                    return;
                }
                break;
            case 4:
                column = "address";
                break;
            default:
                column = "name";
        }
        repository.updateClient(id, column, newval);
    }

    public Vector<Vector<String>> writeClientTable(String nameOrId) {
        if (nameOrId.chars().allMatch(Character::isDigit) && !nameOrId.equalsIgnoreCase("")) {

            Vector<Vector<String>> data = new Vector<>();
            Vector<String> d = new Vector<>();
            Long id = Long.parseLong(nameOrId);
            Client u = findById(id);
            d.add(u.getId().toString());
            d.add(u.getName());
            d.add(u.getId_card_nr().toString());
            d.add(u.getPers_num_code().toString());
            d.add(u.getAddress());
            data.add(d);
            return data;
        } else {
            return getAllClientsTable();
        }
    }

    public DefaultComboBoxModel setOwnerCombo() {
        Vector<Vector<String>> clients = getAllClientsTable();
        DefaultComboBoxModel dcm = new DefaultComboBoxModel();
        for (int i = 0; i < clients.size(); i++) {
            String nm = clients.elementAt(i).elementAt(0) + " " + clients.elementAt(i).elementAt(1);
            dcm.addElement(nm);
        }
        return dcm;

    }


}
