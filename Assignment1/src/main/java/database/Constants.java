package database;

import java.util.*;

import static database.Constants.Rights.*;
import static database.Constants.Rights.DELETE_ACCOUNT;
import static database.Constants.Rights.UPDATE_ACCOUNT;
import static database.Constants.Roles.*;

/**
 * Created by Alex on 11/03/2017.
 */
public class Constants {

    public static class Schemas {
        public static final String TEST = "test_library";
        public static final String PRODUCTION = "library";

        public static final String[] SCHEMAS = new String[]{TEST, PRODUCTION};
    }

    public static class Tables {
        public static final String ACCOUNT = "account";
        public static final String CLIENT = "client";
        public static final String BILL = "bill";
        public static final String ACTIVITY = "activity";
        public static final String USER = "user";
        public static final String ROLE = "role";
        public static final String RIGHT = "right";
        public static final String ROLE_RIGHT = "role_right";
        public static final String USER_ROLE = "user_role";

        public static final String[] ORDERED_TABLES_FOR_CREATION = new String[]{USER, ROLE, RIGHT, ROLE_RIGHT, USER_ROLE, CLIENT, ACCOUNT, BILL,ACTIVITY};
    }

    public static class Roles {
        public static final String ADMINISTRATOR = "administrator";
        public static final String EMPLOYEE = "employee";
        public static final String CLIENT = "client";
        public static final String[] ROLES = new String[]{ADMINISTRATOR, EMPLOYEE, CLIENT};
    }

    public static class Columns {
        public static final String ID = "Id";
        public static final String TYPE = "Type";
        public static final String AMOUNT = "Amount";
        public static final String DATE_OF_CREATION = "Date of creation";
        public static final String OWNERID = "Owner Id";

        public static final String[] ACCOUNT_COLS = {ID, TYPE, AMOUNT, DATE_OF_CREATION, OWNERID};

        public static final String CODE = "Code";
        public static final String TITLE = "Title";
        public static final String PRICE = "Date of creation";

        public static final String[] BILL_COLS = {CODE, TITLE, PRICE, OWNERID};

        public static final String NAME = "Name";
        public static final String IDCARDNR = "Id card nr";
        public static final String PERSNUMCODE = "Personal Num Code";
        public static final String ADDRESS = "Address";

        public static final String[] CLIENT_COLS = {ID, NAME, IDCARDNR, PERSNUMCODE, ADDRESS};

        public static final String PERSONAL = "Personal";
        public static final String REAL = "Real";
        public static final String NOMINAL = "Nominal";

        public static final String USERNAME = "Username";
        public static final String PASSWORD = "Password";
        public static final String ROLE = "Role";

        public static final String[] USER_COLS = {ID, USERNAME, PASSWORD, ROLE};

        public static final String ADMINISTRATOR = "Administrator";
        public static final String EMPLOYEE = "Employee";



    }


    public static class Rights {
        public static final String CREATE_USER = "create_user";
        public static final String DELETE_USER = "delete_user";
        public static final String UPDATE_USER = "update_user";
        public static final String GENERATE_REPORT = "generate_report";

        public static final String CREATE_CLIENT = "create_client";
        public static final String DELETE_CLIENT = "delete_client";
        public static final String UPDATE_CLIENT = "update_client";

        public static final String CREATE_ACCOUNT = "create_account";
        public static final String DELETE_ACCOUNT = "delete_account";
        public static final String UPDATE_ACCOUNT = "update_account";

        public static final String TRANSFER_MONEY = "transfer_money";
        public static final String PROCESS_BILLS = "process_bills";

        public static final String[] RIGHTS = new String[]{CREATE_CLIENT, DELETE_CLIENT, UPDATE_CLIENT, CREATE_USER, DELETE_USER, UPDATE_USER, CREATE_ACCOUNT, DELETE_ACCOUNT, UPDATE_ACCOUNT, TRANSFER_MONEY, PROCESS_BILLS};
    }

    public static Map<String, List<String>> getRolesRights() {
        Map<String, List<String>> ROLES_RIGHTS = new HashMap<>();
        for (String role : ROLES) {
            ROLES_RIGHTS.put(role, new ArrayList<>());
        }
        ROLES_RIGHTS.get(ADMINISTRATOR).addAll(Arrays.asList(RIGHTS));

        ROLES_RIGHTS.get(EMPLOYEE).addAll(Arrays.asList(CREATE_CLIENT, DELETE_CLIENT, UPDATE_CLIENT, CREATE_ACCOUNT, DELETE_ACCOUNT, UPDATE_ACCOUNT, TRANSFER_MONEY, PROCESS_BILLS));

        return ROLES_RIGHTS;
    }

}
