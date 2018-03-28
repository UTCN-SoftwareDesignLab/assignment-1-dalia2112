import controller.AdminController;
import controller.EmployeeController;
import controller.LoginController;
import org.apache.commons.lang3.RandomStringUtils;
import repository.client.ClientRepository;
import view.EmployeeView;
import view.LoginView;

/**
 * Created by Alex on 18/03/2017.
 */
public class Launcher {

    public static void main(String[] args) {
        ComponentFactory componentFactory = ComponentFactory.instance();
        LoginController lg=new LoginController(new LoginView(), componentFactory.getAuthenticationService());
        lg.attachAdminController(new AdminController(componentFactory.getUserService(),componentFactory.getAuthenticationService()));

        lg.attachEmployeeController(new EmployeeController(componentFactory.getClientService(),componentFactory.getAccountService()));

    }

}
