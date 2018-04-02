import database.DBConnectionFactory;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;
import repository.activity.ActivityRepository;
import repository.activity.ActivityRepositoryMySQL;
import repository.bill.BillRepository;
import repository.bill.BillRepositoryMySQL;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
//import repository.client.ClientRepositoryMySQL;
import service.account.AccountService;
import service.account.AccountServiceImpl;
import service.activity.ActivityService;
import service.activity.ActivityServiceImpl;
import service.bill.BillService;
import service.bill.BillServiceImpl;
import service.client.ClientService;
import service.client.ClientServiceImpl;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceMySQL;
import service.user.UserService;
import service.user.UserServiceMySQL;

import java.sql.Connection;
//import java.util.List;

/**
 * Created by Alex on 18/03/2017.
 */
public class ComponentFactory {

    private final AuthenticationService authenticationService;
    private final ActivityService activityService;
    private final ActivityRepository activityRepository;

    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;
    private final ClientService clientService;
    private final ClientRepository clientRepository;
    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final UserService userService;
    private final BillService billService;
    private final BillRepository billRepository;



    private static ComponentFactory instance;

    public static ComponentFactory instance() {
        if (instance == null) {
            instance = new ComponentFactory();
        }
        return instance;
    }

    private ComponentFactory() {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(false).getConnection();
        this.rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        this.userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
        this.authenticationService = new AuthenticationServiceMySQL(this.userRepository, this.rightsRolesRepository);
        this.clientRepository = new ClientRepositoryMySQL(connection);
        this.clientService = new ClientServiceImpl(this.clientRepository);
        this.accountRepository = new AccountRepositoryMySQL(connection);
        this.accountService = new AccountServiceImpl(this.accountRepository);
        this.userService = new UserServiceMySQL(userRepository);
        this.activityRepository= new ActivityRepositoryMySQL(connection);
        this.activityService= new ActivityServiceImpl(activityRepository);
        this.billRepository=new BillRepositoryMySQL(connection);
        this.billService=new BillServiceImpl(billRepository);
    }

    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public RightsRolesRepository getRightsRolesRepository() {
        return rightsRolesRepository;
    }

    public ClientService getClientService() {
        return clientService;
    }

    public AccountService getAccountService() {
        return accountService;
    }

    public UserService getUserService() {
        return userService;
    }

    public ActivityService getActivityService(){
        return activityService;
    }

    public BillService getBillService() {
        return billService;
    }

    //    public static void main(String[] args){
//        ComponentFactory c=new ComponentFactory();
//        List<Account> ac=c.accountRepository.findByOwner((long)9);
//    }
}
