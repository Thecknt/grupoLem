<<<<<<< HEAD
package grupoLem.appGestion.controller;

import grupoLem.appGestion.controller.request.CreateUserDTO;
import grupoLem.appGestion.exception.ResourceNotFoundException;
import grupoLem.appGestion.model.ERole;
import grupoLem.appGestion.model.RoleEntity;
import grupoLem.appGestion.model.UserEntity;
import grupoLem.appGestion.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/")
public class MainController {

    @Autowired
    private UserRepository userRepository;

    @Autowired(required = true)
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserDTO createUserDTO){

        Set<RoleEntity> roles = createUserDTO.getRoles().stream()
                .map(role -> RoleEntity.builder()
                        .name(ERole.valueOf(role))
                        .build())
                .collect(Collectors.toSet());

        Optional<UserEntity> userOptional = userRepository.findByUsername(createUserDTO.getUsername());
        if (userOptional.isPresent()){
            throw new ResourceNotFoundException("Ya existe un usuario con este nombre: " + createUserDTO.getUsername());
        }

        if (userRepository.existsByEmail(createUserDTO.getEmail())){

            throw new ResourceNotFoundException("El email ya se encuentra utilizado: "+createUserDTO.getEmail());
        }

        UserEntity userEntity = UserEntity.builder()
                .username(createUserDTO.getUsername())
                .password(passwordEncoder.encode(createUserDTO.getPassword()))
                .email(createUserDTO.getEmail())
                .roles(roles)
                .build();

        userRepository.save(userEntity);

        Map<String, Object> response = new HashMap<>();
        response.put("user", userEntity);
        response.put("responseMessage", String.format("User with ID %d and EMAIL %s has beed created.", userEntity.getIdUser(),userEntity.getEmail()));

        return ResponseEntity.ok(response);
    }

}
=======
package grupoLem.appGestion.controller;

import grupoLem.appGestion.controller.request.CreateUserDTO;
import grupoLem.appGestion.exception.ResourceNotFoundException;
import grupoLem.appGestion.model.ERole;
import grupoLem.appGestion.model.RoleEntity;
import grupoLem.appGestion.model.UserEntity;
import grupoLem.appGestion.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/")
public class MainController {

    @Autowired
    private UserRepository userRepository;

    @Autowired(required = true)
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserDTO createUserDTO){

        Set<RoleEntity> roles = createUserDTO.getRoles().stream()
                .map(role -> RoleEntity.builder()
                        .name(ERole.valueOf(role))
                        .build())
                .collect(Collectors.toSet());

        Optional<UserEntity> userOptional = userRepository.findByUsername(createUserDTO.getUsername());
        if (userOptional.isPresent()){
            throw new ResourceNotFoundException("Ya existe un usuario con este nombre: " + createUserDTO.getUsername());
        }

        if (userRepository.existsByEmail(createUserDTO.getEmail())){

            throw new ResourceNotFoundException("El email ya se encuentra utilizado: "+createUserDTO.getEmail());
        }

        UserEntity userEntity = UserEntity.builder()
                .username(createUserDTO.getUsername())
                .password(passwordEncoder.encode(createUserDTO.getPassword()))
                .email(createUserDTO.getEmail())
                .roles(roles)
                .build();

        userRepository.save(userEntity);

        Map<String, Object> response = new HashMap<>();
        response.put("user", userEntity);
        response.put("responseMessage", String.format("User with ID %d and EMAIL %s has beed created.", userEntity.getIdUser(),userEntity.getEmail()));

        return ResponseEntity.ok(response);
    }

}
>>>>>>> e294b050ac586c1d154ff242081dc37ef230cbbd
