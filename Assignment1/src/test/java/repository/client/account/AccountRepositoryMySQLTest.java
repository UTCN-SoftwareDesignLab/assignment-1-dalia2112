package repository.client.account;

import database.DBConnectionFactory;
import model.Account;
import model.Client;
import model.builder.AccountBuilder;
import model.builder.ClientBuilder;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.Cache;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryCacheDecorator;
import repository.account.AccountRepositoryMySQL;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryCacheDecorator;
import repository.client.ClientRepositoryMySQL;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AccountRepositoryMySQLTest {

    private static AccountRepository accountRepository;

    @BeforeClass
    public static void setupClass() {
        accountRepository = new AccountRepositoryCacheDecorator(
                new AccountRepositoryMySQL(
                        new DBConnectionFactory().getConnectionWrapper(true).getConnection()
                ),
                new Cache<>()
        );

    }

    @Before
    public void cleanUp() {
        accountRepository.removeAll();
    }

    @Test
    public void findAll() throws Exception {
        List<Account> accounts = accountRepository.findAll();
        assertEquals(accounts.size(), 0);
    }

    @Test
    public void findAllWhenDbNotEmpty() throws Exception {
        Account account = new AccountBuilder()
                .setType("Personal")
                .setAmount((float)2)
                .setDate(new Date())
                .setOwner(1L)
                .build();
        accountRepository.save(account);
        accountRepository.save(account);
        accountRepository.save(account);

        List<Account> accounts = accountRepository.findAll();
        assertEquals(accounts.size(), 3);
    }

    @Test
    public void findById() throws Exception {
        List<Account> accounts = new ArrayList<>();
        Account account = accountRepository.findById((long) 1);
        accounts.add(account);
        assertEquals(accounts.size(), 1);
    }

    @Test
    public void save() throws Exception {
        assertTrue(accountRepository.save(
                new AccountBuilder()
                        .setType("Personal")
                        .setAmount((float)2)
                        .setDate(new Date())
                        .setOwner((long)1)
                        .build()
        ));
    }

    @Test
    public void removeAll() throws Exception {
        accountRepository.removeAll();
        List<Account> accounts = accountRepository.findAll();
        assertEquals(accounts.size(), 0);
    }

}
