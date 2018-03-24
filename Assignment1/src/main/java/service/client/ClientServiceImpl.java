package service.client;

import model.Client;
import repository.EntityNotFoundException;
import repository.client.ClientRepository;

import javax.swing.table.DefaultTableModel;
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
    public Client findById(Long id) throws EntityNotFoundException {
        return repository.findById(id);
    }
    public Client findByName(String name) throws EntityNotFoundException {
        return repository.findByName(name);
    }

    @Override
    public boolean save(Client client) {
        return repository.save(client);
    }
    public Vector<Vector<String>> getAllClientsTable(){return repository.getAllClientsTable();}

}
