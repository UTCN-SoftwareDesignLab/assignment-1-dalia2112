package service.account;

import model.Account;
import model.Client;
import org.junit.Before;
import org.junit.Test;
import repository.account.AccountRepositoryMock;
import repository.client.ClientRepositoryMock;
import service.client.ClientService;
import service.client.ClientServiceImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AccountServiceImplTest {

    private AccountService accountService;

    @Before
    public void setup() {
        accountService = new AccountServiceImpl(new AccountRepositoryMock());
    }

    @Test
    public void findAll() throws Exception {
        assertEquals(0, accountService.findAll().size());
    }

    @Test
    public void findByIdEx() throws Exception {
        accountService.findById(1L);
    }

    @Test
    public void save() throws Exception {
        assertTrue(accountService.save(new Account()));
    }


}
