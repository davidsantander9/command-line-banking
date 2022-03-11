package handlers;

public class HandlerInputs {
    public static double readDouble(String promp ){
        boolean validNumber = false;
        double input = 0.0;
        while(!validNumber){
            try {
                System.out.print(promp);
                input = Double.parseDouble(System.console().readLine());
                if (input > 0)
                    validNumber = true;
            }catch (NumberFormatException ex){
                System.out.println("Incorrect value");
            }
        }
        return input;
    }

    public static Double readPercentage(String promp){
        boolean validNumber = false;
        double input = 0.0;
        while(!validNumber){
            try {
                System.out.print(promp);
                input = Double.parseDouble(System.console().readLine());
                if(input > 0.0 && input < 2.0)
                    validNumber = true;
            }catch (NumberFormatException ex){
                System.out.println("Incorrect value");
            }
        }
        return input;
    }

    public static int readInteger(String promp ){
        boolean validNumber = false;
        int input = 0;
        while(!validNumber){
            try {
                System.out.print(promp);
                input = Integer.parseInt(System.console().readLine());
                if (input >= 0)
                    validNumber = true;

            }catch (NumberFormatException ex){
                System.out.println("Incorrect value");
            }
        }
        return input;
    }

    public static String readSingleWord(String promp){
        boolean validWord = false;
        String input = "";
        while(!validWord){
            System.out.print(promp);
            input = new String(System.console().readLine());
            validWord = input.matches("(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){0,24}$");
            if(!validWord)
                System.out.println("Incorrect Value");
        }
        return input;
    }
    public static String readIdNumber(String promp){
        boolean validWord = false;
        String input = "";
        while(!validWord){
            System.out.print(promp);
            input = new String(System.console().readLine());
            validWord = input.matches("[0-9]+");
            if (!validWord)
                System.out.println("Just numbers");
        }
        return input;
    }

}
