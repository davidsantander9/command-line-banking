package commands;


import bank.AdministradorCuentasHabientes;
import bank.Cliente;
import handlers.HandlerInputs;
import handlers.PropertyHandler;

public class Command {
    private static final String PROP_PASSWORD = "system.password";
    private static AdministradorCuentasHabientes admAccounts = new AdministradorCuentasHabientes();

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
                case "create-acct":
                    createAccountHolder();
                    break;
                case "show-accts":
                    showAccounts();
                    break;
                case "add-product":
                    addProduct();
                    break;
                case "withdrawal":
                    withdrawal();
                    break;
                case "deposit":
                    deposit();
                    break;
                case "cutting":
                    cutting();
                    break;
                case "statement":
                    statement();
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
                "- create-acct: create account holder\n" +
                "- show-accts: show all accounts \n" +
                "- add-product: add product\n" +
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

    private static void createAccountHolder(){
        String numClient = HandlerInputs.readIdNumber("Enter client number: ");
        String nameClient = HandlerInputs.readSingleWord("Enter client name: ");
        double monthlyIncome = HandlerInputs.readDouble("Enter monthlyIncome: ");
        admAccounts.agregarCuentaHabiente( new Cliente(nameClient, numClient, monthlyIncome));
    }

    private static void showAccounts(){
        admAccounts.mostrarInfoCuentasHabiente();
    }

    private static void addProduct(){
        int indexAccount = chooseAccount();
        System.out.println();
        System.out.println("Credit card: 1");
        System.out.println("Checking Account : 2");
        System.out.println("Investment account: 3");
        System.out.print("Enter type of product: ");
        int typeProduct = Integer.parseInt(System.console().readLine());

        switch (typeProduct){
            case 1:
                admAccounts.agregarProducto(indexAccount, AdministradorCuentasHabientes.getTarjetaCredito("1", 20000));
                break;
            case 2:
                admAccounts.agregarProducto(indexAccount, AdministradorCuentasHabientes.getCuentaCheques("2", 20000, .05));
                break;
            case 3:
                //String id, double balance, double interesAlCorte, double impuesto
                admAccounts.agregarProducto(indexAccount, AdministradorCuentasHabientes.getCuentaInversion("3", 2000, .05, .15));
                break;
            default:
                System.err.println("Invalid option");
        }
    }


    private static int chooseAccount(){
        admAccounts.mostrarInfoCuentasHabiente();
        System.out.println();
        System.out.println("Choose a account e.g: 3 ");
        int indexAccount = HandlerInputs.readInteger("Enter account : ");
        return indexAccount;
    }

    private static void withdrawal(){
        int indexAccount = chooseAccount();
        String idProduct = chooseIdProduct(indexAccount);
        double amount = HandlerInputs.readDouble("Enter amount: ");
        admAccounts.retiro(indexAccount, idProduct, amount);
    }

    private static void deposit(){
        int indexAccount = chooseAccount();
        String idProduct = chooseIdProduct(indexAccount);
        double amount = HandlerInputs.readDouble("Enter amount: ");
        admAccounts.deposito(indexAccount, idProduct, amount);
    }

    private static void cutting(){
        int indexAccount = chooseAccount();
        String idProduct = chooseIdProduct(indexAccount);
        admAccounts.corte(indexAccount, idProduct);
    }

    private static String chooseIdProduct(int indexAccount){
        System.out.println("List of client products ");
        admAccounts.mostraCuentasClientes(indexAccount);
        String idProduct = HandlerInputs.readIdNumber("Choose product id: ");
        return idProduct;
    }

    private static void statement(){
        int indexAccount = chooseAccount();
        String idProduct = chooseIdProduct(indexAccount);
        admAccounts.imprimirEstadoCuenta(indexAccount, idProduct);
    }
}

