package com.lamlvbank.homebanking.controller;

import com.lamlvbank.homebanking.model.User;
import com.lamlvbank.homebanking.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/apiHB/users")
public class UserController {
    @Autowired
    private UserService uS;

    @GetMapping
    ResponseEntity<List<User>> findAll() {
      return ResponseEntity.ok(uS.findAll());
    }

    @GetMapping("/{idU}")
    ResponseEntity<User> findById(@PathVariable("idU")Long idU){
        Optional<User> optUser=uS.findById(idU);
          if (optUser.isPresent()){
            return ResponseEntity.ok(optUser.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/dni/{dniU}")
    ResponseEntity<User> findByDni(@PathVariable("dniU")String dniU){
        Optional<User> optUser=uS.findByDni(dniU);
        if (optUser.isPresent()){
            return ResponseEntity.ok(optUser.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    ResponseEntity<User> save(@Valid @RequestBody User user){
        User userSaved = uS.save(user);
        if (userSaved.getIdU() !=null){
            return ResponseEntity.status(HttpStatus.CREATED).body(userSaved);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

//? El método 'register' lleva un proceso distinto en el SERVICE al usado en el método 'save'.
    @PostMapping("/register")
    ResponseEntity<User> register(@Valid @RequestBody User user){
        User userRegister = uS.register(user);
        if (userRegister.getIdU() !=null){
            return ResponseEntity.status(HttpStatus.CREATED).body(userRegister);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping
    ResponseEntity<User> update(@Valid @RequestBody  User user)throws URISyntaxException {
        User userUpdated = uS.update(user);
        if (!userUpdated.equals(user)){
                return ResponseEntity.status(HttpStatus.OK).body(userUpdated);
        } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(user);
        }
    }

    @DeleteMapping("/{idU}")
    ResponseEntity<?> deleteById(@PathVariable("idU")Long idU){
        if (uS.deleteById(idU)){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/dni/{dniU}")
    ResponseEntity<?> deleteByDni(@PathVariable("dniU")String dniU){
        if (uS.deleteByDni(dniU)){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}