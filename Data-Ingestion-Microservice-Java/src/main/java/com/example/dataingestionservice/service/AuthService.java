package com.example.dataingestionservice.service;


import com.example.dataingestionservice.config.JWTUtil;
import com.example.dataingestionservice.dto.auth.JwtRequest;
import com.example.dataingestionservice.dto.auth.JwtResponse;
import com.example.dataingestionservice.dto.auth.PersonDTO;
import com.example.dataingestionservice.dto.auth.RegistrationUserDTO;
import com.example.dataingestionservice.exceptions.LocalException;
import com.example.dataingestionservice.exceptions.TokenRefreshException;
import com.example.dataingestionservice.models.Person;
import com.example.dataingestionservice.models.RefreshToken;
import jakarta.servlet.ServletException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;
import com.example.dataingestionservice.models.Role;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PersonService personService;

    private final JWTUtil jwtUtil;
    private  final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    public JwtResponse authorize(@RequestBody JwtRequest authRequest){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),
                    authRequest.getPassword()));
        } catch(BadCredentialsException e){
            throw new LocalException(HttpStatus.UNAUTHORIZED,"Неверный логин или пароль");
        }
        Person person = personService.findByUsername(authRequest.getUsername());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(person);
        String token = null;
        try {
            token = jwtUtil.generateToken(person);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
        List <String>  roles=person.getRoles().stream().map(Role::getName).collect(Collectors.toList());
        return new JwtResponse(token,refreshToken.getToken(),roles);
    }

    public PersonDTO createNewPerson(@RequestBody RegistrationUserDTO registrationUserDTO)  {
        if(!registrationUserDTO.getPassword().equals(registrationUserDTO.getConfirmPassword())){
            throw new LocalException(HttpStatus.BAD_REQUEST,"Пароли не совпадают");
        }
        Person person = personService.createPerson(registrationUserDTO);
        return new PersonDTO(person.getId(), person.getUsername());
    }


    public JwtResponse refreshToken(String requestRefreshToken){
        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getPerson)
                .map(user -> {
                    String token = null;
                    try {
                        token = jwtUtil.generateToken((com.example.dataingestionservice.models.Person) user);
                    } catch (ServletException e) {
                        throw new RuntimeException(e);
                    }
                    List <String> roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toList());
                    return new JwtResponse(token, requestRefreshToken,roles);
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }
}
