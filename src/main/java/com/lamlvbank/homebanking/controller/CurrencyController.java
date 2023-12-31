package com.lamlvbank.homebanking.controller;

import com.lamlvbank.homebanking.model.Currency;
import com.lamlvbank.homebanking.service.CurrencyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/apiHB/currencies")
public class CurrencyController {

    @Autowired
    private CurrencyService cs;

    @GetMapping
    ResponseEntity<List<Currency>> findAll() {
        return ResponseEntity.ok(cs.findAll());
    }

    @GetMapping("/{idC}")
    ResponseEntity<Currency> findById(@PathVariable("idC") Long idC) {
        Optional<Currency> optionalCurrency = cs.findById(idC);
        if (optionalCurrency.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(optionalCurrency.get());
        }
    }

    @PostMapping
    ResponseEntity<Currency> save(@Valid @RequestBody Currency currency) {
        Currency currencySaved = cs.save(currency);
        if (currencySaved.getIdC() != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(currencySaved);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
    
//! Se comento 'update' ya que no corresponde a la lógica de negocio de nuestra API.     
    // @PutMapping ("/{idC}")
    // ResponseEntity<Currency> update(@Valid @RequestBody Currency currency
    //                                     ,@PathVariable("idC") Long idC) {
    //     currency.setIdC(idC);
    //     Currency currencyUpdated = cs.update(currency);
    //     if (currencyUpdated.getIdC()!=0) {
    //         return ResponseEntity.ok().body(currencyUpdated);
    //     } else {
    //         return ResponseEntity.badRequest().build();
    //     }
    // }

    @DeleteMapping ("/{idC}")
    ResponseEntity<?> deleteById (@PathVariable("idC") Long idC){
        if (cs.deleteById(idC)){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
