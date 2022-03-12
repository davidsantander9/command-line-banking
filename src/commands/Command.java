package commands;


import bank.AdministradorCuentasHabientes;
import handlers.HandlerInputs;
import handlers.PropertyHandler;

public class Command {
    private static final String PROP_PASSWORD = "system.password";
    private static final AdministradorCuentasHabientes admAccounts = new AdministradorCuentasHabientes();

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
                case "cancel-products":
                    cancelProducts();
                    break;
                case "cancel-acct":
                    cancelAcct();
                    break;
                case "set-max-credit-line":
                    setMaxCreditLine();
                    break;
                case "set-taxes":
                    setTaxes();
                    break;
                case "transfer":
                    transfer();
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
        admAccounts.agregarCuentaHabiente( AdministradorCuentasHabientes.getCliente(nameClient, numClient, monthlyIncome));
    }

    private static void showAccounts(){
        admAccounts.mostrarInfoCuentasHabiente();
    }

    private static void addProduct(){
        System.out.println();
        System.out.println("Credit card: 1");
        System.out.println("Checking Account : 2");
        System.out.println("Investment account: 3");
        int typeProduct = HandlerInputs.readInteger("\nEnter type of product: ");

        switch (typeProduct){
            case 1:
                addCreditCard();
                break;
            case 2:
                addCheckingAccount();
                break;
            case 3:
                addInvestmentAccount();
                break;
            default:
                System.err.println("Invalid option");
        }
    }


    private static int chooseAccount(){
        admAccounts.mostrarInfoCuentasHabiente();
        System.out.println();
        System.out.println("Choose a account e.g: 3 ");
        return HandlerInputs.readInteger("Enter account : ");
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
        admAccounts.mostrarProductosCliente(indexAccount);
        return HandlerInputs.readIdNumber("Choose product id: ");
    }

    private static void statement(){
        int indexAccount = chooseAccount();
        String idProduct = chooseIdProduct(indexAccount);
        admAccounts.imprimirEstadoCuenta(indexAccount, idProduct);
    }

    private static void addCreditCard(){
        int indexAccount = chooseAccount();
        String id = HandlerInputs.readIdNumber("Enter credit card id ");
        double creditLimit = HandlerInputs.readDouble("Enter a credit Limit: ");
        admAccounts.agregarProducto(indexAccount, AdministradorCuentasHabientes.getTarjetaCredito(id, creditLimit));

    }

    private static void addInvestmentAccount(){
        int indexAccount = chooseAccount();
        String id = HandlerInputs.readIdNumber("Enter account id: ");
        double balance = HandlerInputs.readDouble("Enter initial balance: ");
        double interest = HandlerInputs.readPercentage("Enter interest: ");
        admAccounts.agregarProducto(indexAccount, AdministradorCuentasHabientes.getCuentaInversion(id, balance, interest));
    }

    private static void addCheckingAccount(){
        int indexAccount = chooseAccount();
        String id = HandlerInputs.readIdNumber("Enter account id: ");
        double balance = HandlerInputs.readDouble("Enter initial balance: ");
        double fee = HandlerInputs.readPercentage("Enter withdrawal fee: ");
        admAccounts.agregarProducto(indexAccount, AdministradorCuentasHabientes.getCuentaCheques(id, balance, fee));
    }

    private static void cancelProducts(){
        int indexAccount = chooseAccount();
        System.out.println("Are you sure you want delete all products?");
        String sure = HandlerInputs.readSingleWord("Enter 'yes' if you are sure: ");
        if(sure.equals("yes"))
            admAccounts.cancelarProductos(indexAccount);
    }

    private static void cancelAcct(){
        int indexAccount = chooseAccount();
        System.out.println("Are you sure you want cancel the account?");
        String sure = HandlerInputs.readSingleWord("Enter 'yes' if you are sure: ");
        if(sure.equals("yes"))
            admAccounts.cancelarCuentaHabiente(indexAccount);
    }

    private static void setMaxCreditLine(){
        double maxCreditLine = HandlerInputs.readDouble("Enter maximum credit line per monthly income: ");
        admAccounts.changeLineadeCreditoMaximaPorIngresoMensual(maxCreditLine);
    }

    private static void setTaxes(){
        double taxes = HandlerInputs.readDouble("Enter taxes: ");
        admAccounts.setImpuesto(taxes);
    }

    private static void transfer(){
        System.out.println("Choose source product");
        int indexAccountSource = chooseAccount();
        String idProductSource = chooseIdProduct(indexAccountSource);
        System.out.println("Choose destination product");
        int indexAccountDestination = chooseAccount();
        if(indexAccountDestination != indexAccountSource){
            String idProductDestination= chooseIdProduct(indexAccountDestination);
            double ammount = HandlerInputs.readDouble("Enter amount: ");
            admAccounts.tranferencia(indexAccountSource, idProductSource, indexAccountDestination, idProductDestination, ammount);
        }
    }

}

