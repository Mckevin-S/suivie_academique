package com.suivie_academique.suivie_academique.test_cours;


import com.suivie_academique.suivie_academique.utils.PassWordValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ValidPasswordTest {

    static String password = "123456";


    @BeforeEach
    void beforeTestValid (){
        System.out.println("Travail avant le test");
        password = password+ "66";
    }

    @AfterEach
    void afterTestValid (){
        System.out.println("Fin du test"+ password );
    }


    @Test
    public void testPassword(){
//        PassWordValidator passWordValidator = new PassWordValidator();
        Assertions.assertEquals(true, PassWordValidator.isValid("111111"),"Le message");
    }
}
