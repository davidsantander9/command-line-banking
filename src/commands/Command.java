package commands;

import handlers.PropertyHandler;

public class Command {
    private static final String PROP_PASSWORD = "system.password";

    private Command(){}
    public static void runCommandListener() {
        String command;
        do {
            System.out.print(">_ ");
            command = System.console().readLine();
            switch (command) {
                case "help":
                    printHelp();
                    break;
                case "chg-pass":
                    changePassword();
                    break;
                case "exit":
                    break;
                default:
                    System.err.printf("\"%s\" is not a recognized command%n", command);
            }
        } while(!"exit".equalsIgnoreCase(command));
    }

    private static void printHelp() {
        System.out.println("- help: shows available commands and short description\n" +
                "- chg-pass: change user password\n" +
                "- exit: finishes the program");
    }


    private static void changePassword() {
        System.out.print("Enter new password: ");
        String newPass = new String(System.console().readPassword());
        if(!newPass.trim().isEmpty()) {
            PropertyHandler.setProperty(PROP_PASSWORD, newPass);
            try {
                PropertyHandler.persist();
                System.out.println("Password changed");
            } catch (Exception e) {
                System.err.println("Password could not be set");
                System.err.printf("%s: %s%n", e.getClass().getName(), e.getMessage());
            }
        } else {
            System.err.println("Password could not be set");
        }
    }

}

