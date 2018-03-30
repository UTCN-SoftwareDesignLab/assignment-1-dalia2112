import controller.AdminController;
import controller.EmployeeController;
import controller.LoginController;
import model.Activity;
import service.activity.ActivityService;
import view.AdminView;
import view.EmployeeView;
import view.LoginView;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

/**
 * Created by Alex on 18/03/2017.
 */
public class Launcher {

    public static void main(String[] args) {
        ComponentFactory componentFactory = ComponentFactory.instance();

        new LoginController(new LoginView(),
                componentFactory.getAuthenticationService(),
                new AdminController(new AdminView(), componentFactory.getUserService(), componentFactory.getAuthenticationService(),componentFactory.getActivityService()),
                new EmployeeController(new EmployeeView(), componentFactory.getClientService(), componentFactory.getAccountService(),componentFactory.getActivityService()));


    }

}
