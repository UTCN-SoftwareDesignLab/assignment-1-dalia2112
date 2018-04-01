package service.client;

import model.Client;
import org.junit.Before;
import org.junit.Test;
import repository.client.ClientRepositoryMock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ClientServiceImplTest {
    private ClientService clientService;

    @Before
    public void setup() {
        clientService = new ClientServiceImpl(new ClientRepositoryMock());
    }

    @Test
    public void findAll() throws Exception {
        assertEquals(0, clientService.findAll().size());
    }

    @Test
    public void findByIdEx() throws Exception {
        clientService.findById(1L);
    }

    @Test
    public void save() throws Exception {
        assertTrue(clientService.save(new Client()));
    }

}
