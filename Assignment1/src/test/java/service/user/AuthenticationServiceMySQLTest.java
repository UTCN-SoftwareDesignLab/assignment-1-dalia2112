package service.user;

import database.DBConnectionFactory;
import model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;

import java.sql.Connection;

/**
 * Created by Alex on 11/03/2017.
 */
public class AuthenticationServiceMySQLTest {

    public static final String TEST_USERNAME = "test@username.com";
    public static final String TEST_PASSWORD = "TestPassword1@";
    public static final String ROLE = "ADMINISTRATOR";
    private static AuthenticationService authenticationService;
    private static UserRepository userRepository;

    @BeforeClass
    public static void setUp() {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);

        authenticationService = new AuthenticationServiceMySQL(
                userRepository,
                rightsRolesRepository
        );
    }

    @Before
    public void cleanUp() {
        userRepository.removeAll();
    }


    @Test
    public void register() throws Exception {
        Assert.assertTrue(
                authenticationService.registerUser(TEST_USERNAME, TEST_PASSWORD, ROLE).getResult()
        );
    }

    @Test
    public void login() throws Exception {
        authenticationService.registerUser(TEST_USERNAME, TEST_PASSWORD, ROLE).getResult();
        User user = authenticationService.login(TEST_USERNAME, TEST_PASSWORD).getResult();
        Assert.assertNotNull(user);
    }

}