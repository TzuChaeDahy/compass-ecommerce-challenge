package com.tzuchaedahy.compass_ecommerce_challenge.domain.util;

import java.util.Arrays;
import java.util.InputMismatchException;

public class Validations {

    public static boolean isCPFValid(String CPF) {
        String[] invalidCPF = {
                "00000000000", "11111111111", "22222222222",
                "33333333333", "44444444444", "55555555555",
                "66666666666", "77777777777", "88888888888",
                "99999999999"
        };

        if (CPF.length() != 11 || Arrays.asList(invalidCPF).contains(CPF)) {
            return false;
        }

        try {
            int[] digits = new int[11];
            for (int i = 0; i < 11; i++) {
                digits[i] = Integer.parseInt(String.valueOf(CPF.charAt(i)));
            }

            int sm = 0, peso = 10;
            for (int i = 0; i < 9; i++) {
                sm += digits[i] * peso--;
            }
            int r = 11 - (sm % 11);
            char dig10 = (r == 10 || r == 11) ? '0' : (char) (r + '0');

            sm = 0;
            peso = 11;
            for (int i = 0; i < 10; i++) {
                sm += digits[i] * peso--;
            }
            r = 11 - (sm % 11);
            char dig11 = (r == 10 || r == 11) ? '0' : (char) (r + '0');

            return dig10 == CPF.charAt(9) && dig11 == CPF.charAt(10);
        } catch (InputMismatchException e) {
            return false;
        }
    }

    public static Boolean isEmailValid(String email) {
        return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }

    public static Boolean isPasswordValid(String password) {
        return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");
    }
}