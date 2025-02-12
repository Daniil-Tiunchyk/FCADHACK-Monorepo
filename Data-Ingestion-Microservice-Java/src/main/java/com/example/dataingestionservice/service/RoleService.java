package com.example.dataingestionservice.service;


import com.example.dataingestionservice.models.Role;
import com.example.dataingestionservice.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    public Role getUserRole() {
        var role = roleRepository.findByName("ROLE_USER");
        if (role.isPresent()){
            return role.get();
        }
       else {
           var newRole = new Role();
           newRole.setName("ROLE_USER");
           newRole = roleRepository.save(newRole);
           return newRole;
        }
    }
    public Role getAdminRole() {
        var role = roleRepository.findByName("ROLE_ADMIN");
        if (role.isPresent()){
            return role.get();
        }
        else {
            var newRole = new Role();
            newRole.setName("ROLE_ADMIN");
            newRole = roleRepository.save(newRole);
            return newRole;
        }
    }
}