package repository.client;

import model.Client;
import repository.Cache;
import repository.EntityNotFoundException;

import java.util.List;

public class ClientRepositoryCacheDecorator extends ClientRepositoryDecorator {

    private Cache<Client> cache;

    public ClientRepositoryCacheDecorator(ClientRepository clientRepository, Cache<Client> cache) {
        super(clientRepository);
        this.cache = cache;
    }

    @Override
    public List<Client> findAll() {
        if (cache.hasResult()) {
            return cache.load();
        }
        List<Client> clients = decoratedRepository.findAll();
        cache.save(clients);
        return clients;
    }

    @Override
    public Client findById(Long id) {
        return decoratedRepository.findById(id);
    }

    @Override
    public Client findByName(String name) throws EntityNotFoundException {
        return null;
    }

    @Override
    public boolean save(Client client) {
        cache.invalidateCache();
        return decoratedRepository.save(client);
    }

    @Override
    public void removeAll() {
        cache.invalidateCache();
        decoratedRepository.removeAll();
    }

    @Override
    public void deleteClient(Long id) {

    }

    @Override
    public void updateClient(Long id, String col, String newval) {

    }
}