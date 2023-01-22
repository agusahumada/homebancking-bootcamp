package utils;

import java.util.Random;

public final class CardUtils {


    private CardUtils() {
    }


    //-----------------------metodos obsoletos--------------------------------------
//    public static  String generateNumber() {
//        String cardNumber = "";
//        for (int i = 0; i < 4; ++i) {
//            int newNumber = (int) (Math.random() * 10);
//            cardNumber += String.valueOf(newNumber);
//        }
//        return cardNumber;
//    }
//
//    //generador de cvv-----------------------------
//    public static String generateCvv() {
//        String newCvv = "";
//        for(int i = 0; i < 3; i++) {
//            int newNumber = (int) (Math.random() * 10);
//            newCvv += String.valueOf(newNumber);
//        }
//        return  newCvv;
//    }
//---------------------------metodo de Nina-----------------------------------///
    public static String generateRandomNumbers(int quantity) {
        String cardNumber = "";
        for (int i = 0; i < quantity; ++i) {
            int newNumber = (int) (Math.random() * 10);
            cardNumber += String.valueOf(newNumber);
        }
        return cardNumber;
    }


//-----------------------------------------Balance no lo hago, lo tengo en AccountDTO, y no me retorna el total ( = caso Maité en clase)----------------------------------//

}



