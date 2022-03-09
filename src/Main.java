import handlers.PropertyHandler;

import java.util.Objects;

public class Main {

    private static final String PROP_USERNAME = "system.username";
    private static final String PROP_PASSWORD = "system.password";
    private static final byte TRY_LIMIT = 3;

    public static void main(String[] args) {
        try {
            PropertyHandler.load("/application-default.properties", "application.properties");

            System.out.println("Login...");

            String username, password;

            boolean isLoggedIn = false;
            byte tryCount = 0;

            do {
                System.out.print("username: ");
                username = System.console().readLine();
                System.out.print("password: ");
                password = new String(System.console().readPassword());
                if(Objects.equals(username, PropertyHandler.getStringProperty(PROP_USERNAME)) &&
                        Objects.equals(password, PropertyHandler.getStringProperty(PROP_PASSWORD)))
                    isLoggedIn = true;
                else
                    System.err.println("Incorrect username or password\n\n");
                tryCount++;
            } while (!isLoggedIn && tryCount < TRY_LIMIT);

            if(isLoggedIn) {
                System.out.printf("Successfully logged in as %s%n", username);
                //runCommandListener();
            }
            else
                System.err.println("You have reached your attempts limit");

            PropertyHandler.persist();
            System.out.println("PROGRAM END");
        } catch (Exception e) {
            System.err.printf("%s: %s%n", e.getClass().getName(), e.getMessage());
        }
    }
}
