package utils;

import java.util.Random;

public final class AccountUtils {

    //--------------------------metodo para numeros de cuenta  tomado de :https://study.com/academy/lesson/java-generate-random-number-between-1-100.html---------------------------

    public static int generateRandomNumber(int min, int max) {
        Random random = new Random();
        int randomNumber = random.nextInt(max - min) + min;
        return randomNumber;
    }
}