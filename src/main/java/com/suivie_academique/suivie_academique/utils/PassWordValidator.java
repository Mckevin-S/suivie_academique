package com.suivie_academique.suivie_academique.utils;

public class PassWordValidator {

    public static boolean isValid(String password){
        if (password.length()>=5 && password.length() <=10)
            return true;
        else
            return false;

    }
}
