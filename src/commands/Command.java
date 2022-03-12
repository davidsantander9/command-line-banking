package commands;


import bank.AdministradorCuentasHabientes;
import handlers.HandlerInputs;
import handlers.PropertyHandler;

public class Command {
    private static final String PROP_PASSWORD = "system.password";
    private static final String PROP_MAX_CREDIT_LINE = "system.max.credit.line.per.monthly.income";
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
                "- withdrawal: withdrawal from a client product\n" +
                "- deposit: deposit to a client product\n" +
                "- cutting: cutting to a client product\n" +
                "- statement: print a client product statement\n" +
                "- cancel-products: cancel a client product statement \n" +
                "- cancel-acct: cancel account client\n" +
                "- set-max-credit-line: set max credit line\n" +
                "- set-taxes: set percentage taxes\n" +
                "- transfer: transfer from one product to another a \n" +
                "- add-product: add product to a account client\n" +
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


    private static String chooseClient(){
        admAccounts.mostrarInfoCuentasHabiente();
        System.out.println();
        System.out.println("Choose a number client e.g: 1234 ");
        return HandlerInputs.readIdNumber("Enter number client : ");
    }

    private static String chooseIdProduct(String numClient){
        System.out.println("List of client products ");
        admAccounts.mostrarProductosCliente(numClient);
        return HandlerInputs.readIdNumber("Choose product id: ");
    }

    private static void withdrawal(){
        String numClient = chooseClient();
        String idProduct = chooseIdProduct(numClient);
        double amount = HandlerInputs.readDouble("Enter amount: ");
        admAccounts.retiro(numClient, idProduct, amount);
    }

    private static void deposit(){
        String numClient = chooseClient();
        String idProduct = chooseIdProduct(numClient);
        double amount = HandlerInputs.readDouble("Enter amount: ");
        admAccounts.deposito(numClient, idProduct, amount);
    }

    private static void cutting(){
        String numClient = chooseClient();
        String idProduct = chooseIdProduct(numClient);
        admAccounts.corte(numClient, idProduct);
    }

    private static void statement(){
        String numClient = chooseClient();
        String idProduct = chooseIdProduct(numClient);
        admAccounts.imprimirEstadoCuenta(numClient, idProduct);
    }

    private static void addCreditCard(){
        String numClient = chooseClient();
        String id = HandlerInputs.readIdNumber("Enter credit card id ");
        double creditLimit = HandlerInputs.readDouble("Enter a credit Limit: ");
        admAccounts.agregarProducto(numClient, AdministradorCuentasHabientes.getTarjetaCredito(id, creditLimit));

    }

    private static void addInvestmentAccount(){
        String numClient = chooseClient();
        String id = HandlerInputs.readIdNumber("Enter account id: ");
        double balance = HandlerInputs.readDouble("Enter initial balance: ");
        double interest = HandlerInputs.readPercentage("Enter interest: ");
        admAccounts.agregarProducto(numClient, AdministradorCuentasHabientes.getCuentaInversion(id, balance, interest));
    }

    private static void addCheckingAccount(){
        String numClient = chooseClient();
        String id = HandlerInputs.readIdNumber("Enter account id: ");
        double balance = HandlerInputs.readDouble("Enter initial balance: ");
        double fee = HandlerInputs.readPercentage("Enter withdrawal fee: ");
        admAccounts.agregarProducto(numClient, AdministradorCuentasHabientes.getCuentaCheques(id, balance, fee));
    }

    private static void cancelProducts(){
        String numClient = chooseClient();
        System.out.println("Are you sure you want delete all products?");
        String sure = HandlerInputs.readSingleWord("Enter 'yes' if you are sure: ");
        if(sure.equals("yes"))
            admAccounts.cancelarProductos(numClient);
    }

    private static void cancelAcct(){
        String numClient = chooseClient();
        System.out.println("Are you sure you want cancel the account?");
        String sure = HandlerInputs.readSingleWord("Enter 'yes' if you are sure: ");
        if(sure.equals("yes"))
            admAccounts.cancelarCuentaHabiente(numClient);
    }

    private static void setMaxCreditLine(){
        double maxCreditLine = HandlerInputs.readDouble("Enter maximum credit line per monthly income: ");
        admAccounts.changeLineadeCreditoMaximaPorIngresoMensual();
        PropertyHandler.setProperty(PROP_MAX_CREDIT_LINE, String.valueOf(maxCreditLine));
        try {
            PropertyHandler.persist();
            System.out.println("Maximum credit line per monthly income change");
        } catch (Exception e) {
            System.err.println("Password could not be set");
            System.err.printf("%s: %s%n", e.getClass().getName(), e.getMessage());
        }

    }

    private static void setTaxes(){
        double taxes = HandlerInputs.readDouble("Enter taxes: ");
        admAccounts.setImpuesto(taxes);
    }

    private static void transfer(){
        System.out.println("Choose source product");
        String numClientSource = chooseClient();
        String idProductSource = chooseIdProduct(numClientSource);
        System.out.println("Choose destination product");
        String numClientDestination = chooseClient();
        if(numClientSource.equals(numClientDestination)){
            String idProductDestination = chooseIdProduct(numClientSource);
            double ammount = HandlerInputs.readDouble("Enter amount: ");
            admAccounts.tranferencia(numClientSource, idProductSource, numClientDestination, idProductDestination, ammount);
        }
    }

}

