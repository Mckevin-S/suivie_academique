package com.suivie_academique.suivie_academique.utils;

import com.suivie_academique.suivie_academique.repositories.PersonnelRepos;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class CodeGenarator {

    private  PersonnelRepos personnelRepos;

    public CodeGenarator(PersonnelRepos personnelRepos) {
        this.personnelRepos = personnelRepos;
    }

    public String genarate(String role){
        String prefix =switch (role){
            case "ENSEIGNANT"->"ENS";
            case "RESPONSSALE_ACADEMIQUE"->"RA";
            case "RESPONSSABLE_DICIPLINE"->"RD";
            default -> null;
        };
        long randomNumber = ThreadLocalRandom.current().nextInt(1000,10000);
        int year = LocalDate.now().getYear();
        String code = prefix + year+randomNumber;
        if(personnelRepos.existsById(code))
            return genarate(role);
        else
            return code;
    }
}
