package repository.client;

import model.Book;
import model.Client;
import repository.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ClientRepositoryMock implements ClientRepository {
    private List<Client> clients;

    public ClientRepositoryMock() {
        clients = new ArrayList<>();
    }

    @Override
    public List<Client> findAll() {
        return clients;
    }

    @Override
    public Client findById(Long id) {
        List<Client> filteredClients = clients.parallelStream()
                .filter(it -> it.getId().equals(id))
                .collect(Collectors.toList());
        if (filteredClients.size() > 0) {
            return filteredClients.get(0);
        }
        return null;
    }

    @Override
    public Client findByName(String name) throws EntityNotFoundException {
        List<Client> filteredClients = clients.parallelStream()
                .filter(it -> it.getName().equals(name))
                .collect(Collectors.toList());
        if (filteredClients.size() > 0) {
            return filteredClients.get(0);
        }
        return null;
    }

    @Override
    public boolean save(Client client) {
        return clients.add(client);
    }

    @Override
    public void removeAll() {
        clients.clear();
    }

    @Override
    public void deleteClient(Long id) {
        List<Client> filteredClients = clients.parallelStream()
                .filter(it -> it.getId().equals(id))
                .collect(Collectors.toList());
        clients.removeAll(filteredClients);
    }

    @Override
    public void updateClient(Long id, String col, String newval) {
        List<Client> filteredClients = clients.parallelStream()
                .filter(it -> it.getId().equals(id))
                .collect(Collectors.toList());
        switch (col) {
            case "id":
                break;
            case "name":
                filteredClients.get(0).setName(newval);
                break;
            case "id_card_nr":
                break;
            case "pers_num_code":
                break;
            case "address":
                filteredClients.get(0).setAddress(newval);
                break;
        }


    }
}
