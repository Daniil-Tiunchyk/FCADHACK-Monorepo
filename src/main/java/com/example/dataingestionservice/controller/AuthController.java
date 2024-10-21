package com.example.dataingestionservice.controller;


import com.example.dataingestionservice.dto.auth.*;
import com.example.dataingestionservice.service.AuthService;
import com.example.dataingestionservice.service.PersonService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/identity")
public class AuthController {




    private final AuthService authService;
    private final PersonService personService;
    @PostMapping("/auth")
    @Operation(summary = "Authentication", description = "Authentication on the site")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful authorization",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = JwtResponse.class))),
            @ApiResponse(responseCode = "401", description = "Incorrect email or password",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest){
        return ResponseEntity.ok(authService.authorize(authRequest));
    }
    @PostMapping("/registration")
    @Operation(summary = "Create a new user", description = "Create a new user with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "409", description = "Conflict: user with such details already exists")
    })
    public ResponseEntity<?> createNewPerson(@RequestBody RegistrationUserDTO registrationUserDTO)  {
        authService.createNewPerson(registrationUserDTO);
       return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PostMapping("/refresh")
    @Operation(summary = "JWT update", description = "Getting a new access token using refresh token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = JwtResponse.class))),
            @ApiResponse(responseCode = "403", description = "Refresh token is not valid",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))),
    })
    public ResponseEntity<?> refreshtoken( @Valid @RequestBody TokenRefreshRequest request) {

        return ResponseEntity.ok(authService.refreshToken(request.getRefreshToken()));

    }

    @PostMapping("/password/change/{code}")
    public ResponseEntity<Void> changePassword(@PathVariable String code, @RequestBody ChangePasswordDTO newPassword) {
        personService.changePassword(code, newPassword.getNewPassword());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}