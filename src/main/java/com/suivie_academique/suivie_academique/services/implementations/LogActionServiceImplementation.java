package com.suivie_academique.suivie_academique.services.implementations;

import com.suivie_academique.suivie_academique.entities.LogAction;
import com.suivie_academique.suivie_academique.repositories.LogActionRepos;
import com.suivie_academique.suivie_academique.services.interfaces.LogActionServiceInterface;
import org.springframework.stereotype.Service;

@Service
public class LogActionServiceImplementation implements LogActionServiceInterface {
    private LogActionRepos logActionRepos;

    public LogActionServiceImplementation(LogActionRepos logActionRepos) {
        this.logActionRepos = logActionRepos;
    }

    public void log(String acteur, String action, String description) {
        logActionRepos.save(new LogAction(acteur, action, description));
    }
}

