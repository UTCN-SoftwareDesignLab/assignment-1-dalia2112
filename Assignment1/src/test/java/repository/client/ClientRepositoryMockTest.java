package repository.client;

import model.Client;
import model.builder.ClientBuilder;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.Cache;
import repository.EntityNotFoundException;


import static org.junit.Assert.assertTrue;

public class ClientRepositoryMockTest {
    private static ClientRepository repository;

    @BeforeClass
    public static void setupClass() {
        repository = new ClientRepositoryCacheDecorator(
                new ClientRepositoryMock(),
                new Cache<>()
        );
    }

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void findAll() throws Exception {
        assertTrue(repository.findAll().size() != 0);
    }

    @Test
    public void findById() throws EntityNotFoundException {
        repository.findById(1L);
    }

    @Test
    public void save() throws Exception {
        Client client = new ClientBuilder()
                .setID(1L)
                .setName("Ion")
                .setIdCard((long)123456)
                .setPersNumCode((long)12345)
                .build();

        assertTrue(repository.save(client));
    }

    @Test
    public void update(){
        Client client = new ClientBuilder()
                .setID(1L)
                .setName("Ion")
                .setIdCard((long)123456)
                .setPersNumCode((long)12345)
                .build();
        repository.updateClient(client.getId(),"name","Ionel");
    }

    @Test
    public void delete(){

        Client client = new ClientBuilder()
                .setID(1L)
                .setName("Ion")
                .setIdCard((long)123456)
                .setPersNumCode((long)12345)
                .build();
        repository.save(client);
        repository.deleteClient(client.getId());
    }
}
