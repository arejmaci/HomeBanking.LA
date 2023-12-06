package com.lamlvbank.homebanking.controller;

import com.lamlvbank.homebanking.model.Account;
import com.lamlvbank.homebanking.model.dto.AccountDTO;
import com.lamlvbank.homebanking.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/apiHB/accounts")
public class AccountController {
    @Autowired
    private AccountService accountServ;

    /*
     * Se usa Dto por que el front no se puede usar información sensible, por
     * cuestión de seguridad
     */
    // FIND ALL ACCOUNTS
    @GetMapping
    ResponseEntity<List<AccountDTO>> findAll() {
        return ResponseEntity.ok().body(accountServ.findAll());
    }

    // FIND ACCOUNT BY ID
    @GetMapping("/{idA}")
    ResponseEntity<AccountDTO> findById(@PathVariable("idA") Long idA) {
        Optional<AccountDTO> optAccount = accountServ.findById(idA);
        if (optAccount.isPresent()) {
            return ResponseEntity.ok().body(optAccount.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // CREATE ACCOUNT
    @PostMapping("/{idAT}/{idC}")
    ResponseEntity<Account> save(@Valid @RequestBody Account account, @PathVariable("idAT") Long idAT,
            @PathVariable("idC") Long idC) {
        account.addType(idAT); //Agregado de Meli
        account.addCurrency(idC);//Agregado de pedro
        Account accSaved = accountServ.save(account);
        if (accSaved != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(accSaved);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    // REGISTER ACCOUNT
    @PostMapping("/new")
    ResponseEntity<AccountDTO> openAccount(@Valid @RequestBody AccountDTO dto) {
        AccountDTO accSaved = accountServ.openAccount(dto);
        if (accSaved != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(accSaved);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    // UPDATE ACCOUNT
    @PutMapping
    ResponseEntity<Account> update(@Valid @RequestBody Account account) {
        Account accUpdated = accountServ.update(account);
        if (accUpdated.getIdA() != null) {
            return ResponseEntity.ok(accUpdated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE ACCOUNT
    @DeleteMapping("/{idA}")
    ResponseEntity<?> deleteById(@PathVariable("idA") Long idA) {
        if (accountServ.deleteById(idA)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
