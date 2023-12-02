package com.lamlvbank.homebanking.service;

import com.lamlvbank.homebanking.model.Transference;
import com.lamlvbank.homebanking.model.dto.TransferenceDTO;
import com.lamlvbank.homebanking.repository.TransferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class IMPTransferenceService implements TransferenceService{
    @Autowired
    private TransferenceRepository tR;

    @Autowired
    private AccountService aS;

    @Override
    public List<Transference> findAll() {
        return tR.findAll();
    }

    @Override
    public Optional<Transference> findById(Long idT) {
        return tR.findById(idT);
    }

    @Override
    public Transference save(Transference transference) {
        aS.updateAmounts(transference.getOrigin().getIdA(), transference.getDestiny().getIdA()
                            ,transference.getAmount());
            return tR.save(transference);   
    }

    @Override
    public Transference register(TransferenceDTO dto){
            Transference transference = new Transference();
            transference.setTransferenceN(transferenceNGen());
            transference.setReference(dto.getReference());
            transference.setAmount(dto.getAmount());
            transference.addOriginAcc(dto.getIdO());
            transference.addDestinyAcc(dto.getIdD());
            aS.updateAmounts(dto.getIdO(), dto.getIdD(), dto.getAmount());
        return tR.save(transference);
    }

    @Override
    public boolean deleteById(Long idT) {
        if(tR.existsById(idT)){
            tR.deleteById(idT);
            return true;
        }
    return false;
    }

//?Metodo AUXILIAR para generar el numero de transferencia.
    private Long transferenceNGen(){
        Long transfN = 0L;
        Random random = new Random();
        do{
            transfN = Math.abs(random.nextLong() % (999999999 + 1));
        }while(tR.existsByTransferenceN(transfN));
    return transfN;
    }
}