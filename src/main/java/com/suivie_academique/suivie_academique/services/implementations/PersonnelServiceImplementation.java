package com.suivie_academique.suivie_academique.services.implementations;

import com.suivie_academique.suivie_academique.dto.PersonnelDTO;
import com.suivie_academique.suivie_academique.entities.Personnel;
import com.suivie_academique.suivie_academique.mappers.PersonnelMappers;
import com.suivie_academique.suivie_academique.repositories.PersonnelRepos;
import com.suivie_academique.suivie_academique.services.interfaces.PersonnelServiceInterface;
import com.suivie_academique.suivie_academique.utils.CodeGenarator;
import com.suivie_academique.suivie_academique.utils.RolePersonnel;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonnelServiceImplementation implements PersonnelServiceInterface {

    private final PersonnelMappers personnelMappers;
    private final PersonnelRepos personnelRepos;
    private final CodeGenarator codeGenarator;
    private final PasswordEncoder passwordEncoder; // ✅ Ajout du PasswordEncoder

    // ✅ Constructeur avec injection du PasswordEncoder
    public PersonnelServiceImplementation(PersonnelMappers personnelMappers, PersonnelRepos personnelRepos, CodeGenarator codeGenarator, PasswordEncoder passwordEncoder) {
        this.personnelMappers = personnelMappers;
        this.personnelRepos = personnelRepos;
        this.codeGenarator = codeGenarator;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public PersonnelDTO save(PersonnelDTO personnelDTO) {
        // Validation des champs obligatoires
        if (personnelDTO.getPhonePersonnel() == null || personnelDTO.getPhonePersonnel().isEmpty()) {
            throw new RuntimeException("Le numéro de téléphone est requis");
        }

        if (personnelDTO.getNomPersonnel() == null || personnelDTO.getNomPersonnel().isEmpty()) {
            throw new RuntimeException("Le nom du personnel est requis");
        }

        if (personnelDTO.getPwdPersonnel() == null || personnelDTO.getPwdPersonnel().isEmpty()) {
            throw new RuntimeException("Le mot de passe est requis");
        }

        // Génération du code personnel
        personnelDTO.setCodePersonnel(codeGenarator.genarate(personnelDTO.getRolePersonnel().name()));

        // ✅ ENCODAGE DU MOT DE PASSE avant sauvegarde
        String encodedPassword = passwordEncoder.encode(personnelDTO.getPwdPersonnel());
        personnelDTO.setPwdPersonnel(encodedPassword);

        // Sauvegarde
        Personnel savedPersonnel = personnelRepos.save(personnelMappers.toEntity(personnelDTO));

        // Conversion pour le retour (sans exposer le mot de passe)
        PersonnelDTO resultDTO = personnelMappers.toDTO(savedPersonnel);
        resultDTO.setPwdPersonnel(null); // ✅ Ne jamais renvoyer le mot de passe

        return resultDTO;
    }

    @Override
    public List<PersonnelDTO> getAll() {
        return personnelRepos.findAll()
                .stream()
                .map(personnel -> {
                    PersonnelDTO dto = personnelMappers.toDTO(personnel);
                    dto.setPwdPersonnel(null); // ✅ Masquer les mots de passe
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public PersonnelDTO getById(String codePersonnel) {
        Personnel personnel = personnelRepos.findById(codePersonnel)
                .orElseThrow(() -> new RuntimeException("Personnel non trouvé avec le code : " + codePersonnel));

        PersonnelDTO dto = personnelMappers.toDTO(personnel);
        dto.setPwdPersonnel(null); // ✅ Masquer le mot de passe
        return dto;
    }

    @Override
    public PersonnelDTO update(String codePersonnel, PersonnelDTO personnelDTO) {
        Personnel personnel = personnelRepos.findById(codePersonnel)
                .orElseThrow(() -> new RuntimeException("Personnel non trouvé avec le code : " + codePersonnel));

        // Mise à jour des champs (sauf le mot de passe pour l'instant)
        if (personnelDTO.getNomPersonnel() != null && !personnelDTO.getNomPersonnel().isEmpty()) {
            personnel.setNomPersonnel(personnelDTO.getNomPersonnel());
        }

        if (personnelDTO.getPhonePersonnel() != null && !personnelDTO.getPhonePersonnel().isEmpty()) {
            personnel.setPhonePersonnel(personnelDTO.getPhonePersonnel());
        }

        if (personnelDTO.getLoginPersonnel() != null && !personnelDTO.getLoginPersonnel().isEmpty()) {
            personnel.setLoginPersonnel(personnelDTO.getLoginPersonnel());
        }

        //  MISE À JOUR DU MOT DE PASSE : encoder seulement si un nouveau mot de passe est fourni
        if (personnelDTO.getPwdPersonnel() != null && !personnelDTO.getPwdPersonnel().isEmpty()) {
            // Vérifier que ce n'est pas déjà un hash BCrypt
            if (!personnelDTO.getPwdPersonnel().startsWith("$2a$") &&
                    !personnelDTO.getPwdPersonnel().startsWith("$2b$")) {
                String encodedPassword = passwordEncoder.encode(personnelDTO.getPwdPersonnel());
                personnel.setPwdPersonnel(encodedPassword);
            } else {
                // Si c'est déjà un hash, le garder tel quel
                personnel.setPwdPersonnel(personnelDTO.getPwdPersonnel());
            }
        }
        // Sinon, on garde l'ancien mot de passe (pas de modification)

        // Mise à jour des autres champs si présents


        if (personnelDTO.getSexePersonnel() != 0) {
            personnel.setSexePersonnel(personnelDTO.getSexePersonnel());
        }

        if (personnelDTO.getRolePersonnel() != null) {
            personnel.setRolePersonnel(personnelDTO.getRolePersonnel());
        }

        // Sauvegarde
        Personnel updatedPersonnel = personnelRepos.save(personnel);

        PersonnelDTO resultDTO = personnelMappers.toDTO(updatedPersonnel);
        resultDTO.setPwdPersonnel(null); // ✅ Ne jamais renvoyer le mot de passe

        return resultDTO;
    }

    @Override
    public void delete(String codePersonnel) {
        if (!personnelRepos.existsById(codePersonnel)) {
            throw new RuntimeException("Personnel non trouvé avec le code : " + codePersonnel);
        }
        personnelRepos.deleteById(codePersonnel);
    }

    @Override
    public List<PersonnelDTO> findByRolePersonnel(RolePersonnel rolePersonnel) {
        return personnelRepos.searchByRolePersonnel(rolePersonnel)
                .stream()
                .map(personnel -> {
                    PersonnelDTO dto = personnelMappers.toDTO(personnel);
                    dto.setPwdPersonnel(null); // ✅ Masquer les mots de passe
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<PersonnelDTO> search(String name) {
        return personnelRepos.searchByName(name)
                .stream()
                .map(personnel -> {
                    PersonnelDTO dto = personnelMappers.toDTO(personnel);
                    dto.setPwdPersonnel(null); // ✅ Masquer les mots de passe
                    return dto;
                })
                .collect(Collectors.toList());
    }

    /**
     * ✅ NOUVELLE MÉTHODE : Changement de mot de passe sécurisé
     * Vérifie l'ancien mot de passe avant de le changer
     */
    public void changePassword(String codePersonnel, String oldPassword, String newPassword) {
        Personnel personnel = personnelRepos.findById(codePersonnel)
                .orElseThrow(() -> new RuntimeException("Personnel non trouvé"));

        // Vérifier que l'ancien mot de passe est correct
        if (!passwordEncoder.matches(oldPassword, personnel.getPwdPersonnel())) {
            throw new RuntimeException("Ancien mot de passe incorrect");
        }

        // Validation du nouveau mot de passe
        if (newPassword == null || newPassword.length() < 6) {
            throw new RuntimeException("Le nouveau mot de passe doit contenir au moins 6 caractères");
        }

        // Encoder et sauvegarder le nouveau mot de passe
        personnel.setPwdPersonnel(passwordEncoder.encode(newPassword));
        personnelRepos.save(personnel);
    }

    /**
     * ✅ NOUVELLE MÉTHODE : Réinitialisation du mot de passe (par un admin)
     * Ne nécessite pas l'ancien mot de passe
     */
    public void resetPassword(String codePersonnel, String newPassword) {
        Personnel personnel = personnelRepos.findById(codePersonnel)
                .orElseThrow(() -> new RuntimeException("Personnel non trouvé"));

        // Validation du nouveau mot de passe
        if (newPassword == null || newPassword.length() < 6) {
            throw new RuntimeException("Le mot de passe doit contenir au moins 6 caractères");
        }

        // Encoder et sauvegarder le nouveau mot de passe
        personnel.setPwdPersonnel(passwordEncoder.encode(newPassword));
        personnelRepos.save(personnel);
    }
}