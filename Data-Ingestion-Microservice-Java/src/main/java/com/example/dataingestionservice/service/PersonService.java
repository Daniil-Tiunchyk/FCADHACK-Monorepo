package com.example.dataingestionservice.service;


import com.example.dataingestionservice.dto.auth.RegistrationUserDTO;
import com.example.dataingestionservice.exceptions.LocalException;
import com.example.dataingestionservice.models.Person;
import com.example.dataingestionservice.repository.PersonRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonService implements UserDetailsService {

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public Person findByUsername(String username) {
        Person person = personRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(("User with this id not found")));
            return person;
    }
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = findByUsername(username);
        return new User(
                person.getUsername(),
                person.getPassword(),
                person.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList())
        );
    }

    public Person createPerson(RegistrationUserDTO registrationUserDTO) {
        if(personRepository.findByUsername(registrationUserDTO.getUsername()).isPresent()){
            throw new LocalException(HttpStatus.BAD_REQUEST,"Такой пользователь уже существует");
        }
        Person person = new Person();
        person.setPassword(passwordEncoder.encode(registrationUserDTO.getPassword()));
        person.setUsername(registrationUserDTO.getUsername());


            person.setRoles(List.of(roleService.getUserRole()));

        return personRepository.save(person);
    }


    public void changePassword(String code,String newPassword) {
        System.out.println(code);
        System.out.println(newPassword);
        Person person = personRepository.findByPasswordCode(code).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with this password code not found"));
        System.out.println("(((((");
        person.setPasswordCode(null);
        person.setPassword(passwordEncoder.encode(newPassword));
        personRepository.save(person);
    }
}