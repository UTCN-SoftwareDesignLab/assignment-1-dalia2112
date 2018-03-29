import controller.AdminController;
import controller.EmployeeController;
import controller.LoginController;
import org.apache.commons.lang3.RandomStringUtils;
import repository.client.ClientRepository;
import view.AdminView;
import view.EmployeeView;
import view.LoginView;

/**
 * Created by Alex on 18/03/2017.
 */
public class Launcher {

    public static void main(String[] args) {
        ComponentFactory componentFactory = ComponentFactory.instance();
        new LoginController(new LoginView(),
                componentFactory.getAuthenticationService(),
                new AdminController(new AdminView(), componentFactory.getUserService(), componentFactory.getAuthenticationService()),
                new EmployeeController(new EmployeeView(), componentFactory.getClientService(), componentFactory.getAccountService()));

    }

}
