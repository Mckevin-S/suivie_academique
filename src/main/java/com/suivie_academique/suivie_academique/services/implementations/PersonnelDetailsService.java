package com.suivie_academique.suivie_academique.services.implementations;

import com.suivie_academique.suivie_academique.entities.Personnel;
import com.suivie_academique.suivie_academique.repositories.PersonnelRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonnelDetailsService implements UserDetailsService {

    private PersonnelRepos personnelRepos;

    /**
     * Constructeur pour l'injection de dépendance.
     * Spring injectera automatiquement l'implémentation de PersonnelRepos.
     * @param personnelRepos Le repository utilisé pour accéder aux données du personnel.
     */
    public PersonnelDetailsService(PersonnelRepos personnelRepos) {
        this.personnelRepos = personnelRepos;
    }

    /**
     * Méthode principale requise par l'interface UserDetailsService.
     * Spring Security l'appelle lors de la tentative d'authentification (login).
     *
     * @param username Le nom d'utilisateur (ici, le login du personnel) fourni par l'utilisateur.
     * @return Un objet UserDetails qui contient les informations de l'utilisateur (username, mot de passe, rôles).
     * @throws UsernameNotFoundException Si aucun utilisateur n'est trouvé avec le login donné.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 1. Cherche l'entité Personnel correspondante dans la base de données
        // L'entité Personnel est récupérée via une méthode personnalisée dans le repository.
        Personnel personnel = personnelRepos.findByLoginPersonnel(username);

        // 2. Vérification de l'existence de l'utilisateur
        if (personnel == null) {
            // Si l'utilisateur n'est pas trouvé, lance une exception spécifique
            // à Spring Security qui sera capturée pour signaler l'échec d'authentification.
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        // 3. Construction de la liste des autorités (rôles) de l'utilisateur
        List<GrantedAuthority> authorities = new ArrayList<>();

        // Ajoute le rôle de l'utilisateur au format attendu par Spring Security : "ROLE_NOMDUROLE".
        // Le .name() est utilisé car on suppose que le rôle est une énumération (Enum).
        authorities.add(new SimpleGrantedAuthority("ROLE_" + personnel.getRolePersonnel().name()));

        // 4. Retourne un objet UserDetails (ici, l'implémentation de Spring Security)
        // Cet objet est utilisé par Spring Security pour comparer le mot de passe fourni
        // par l'utilisateur avec le mot de passe stocké (getPwdPersonnel) et encoder (haché).
        return new User(
                personnel.getLoginPersonnel(), // Nom d'utilisateur (Username)
                personnel.getPwdPersonnel(),   // Mot de passe HACHÉ (Password)
                authorities                    // Liste des autorités/rôles (Authorities)
        );
    }
}
