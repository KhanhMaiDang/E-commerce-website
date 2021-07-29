package com.example.ecommerceweb.controller;

import com.example.ecommerceweb.exception.RoleNotFoundException;
import com.example.ecommerceweb.jwt.JwtTokenProvider;
import com.example.ecommerceweb.model.CustomUserDetail;
import com.example.ecommerceweb.model.Role;
import com.example.ecommerceweb.model.User;
import com.example.ecommerceweb.payload.request.LoginRequest;
import com.example.ecommerceweb.payload.request.SignUpRequest;
import com.example.ecommerceweb.payload.response.JwtResponse;
import com.example.ecommerceweb.payload.response.MessageResponse;
import com.example.ecommerceweb.repository.RoleRepository;
import com.example.ecommerceweb.repository.UserRepository;
import com.example.ecommerceweb.service.implement.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) throws Exception {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = tokenProvider.generateToken((CustomUserDetail) authentication.getPrincipal());

            CustomUserDetail userDetails = (CustomUserDetail) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());
            User user = userDetails.getUser();
            return ResponseEntity.ok(new JwtResponse(jwt,
                    user.getId(),
                    user.getUsername(),
                    user.getName(),
                    user.getPhoneNumber(),
                    roles));
        }catch (BadCredentialsException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("Invalid username or password"));
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest){
        if (userRepository.existsByUsername(signUpRequest.getUsername())){
            log.error("username exist");
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }
        else
            log.error("no catch");

        User user = new User(signUpRequest.getUsername(),signUpRequest.getName(), signUpRequest.getPhoneNumber(),
                passwordEncoder.encode(signUpRequest.getPassword()), signUpRequest.getEmail());
        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if(strRoles == null){
            Role userRole = roleRepository.findRoleByName("USER");
            if(user==null)
                throw new RoleNotFoundException("USER");
            else{
                roles.add(userRole);
            }
        }
        else{
            strRoles.forEach(role -> {
                switch (role.toLowerCase()){
                    case "admin":
                        Role adminRole = roleRepository.findRoleByName("ADMIN");
                        if(adminRole==null)
                            throw new RoleNotFoundException("ADMIN");
                        else
                            roles.add(adminRole);
                        break;
                    default:
                        Role userRole = roleRepository.findRoleByName("USER");
                        if(userRole==null)
                            throw new RoleNotFoundException("USER");
                        else
                            roles.add(userRole);
                        break;
                }
            } );
        }
        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully"));

    }
}
