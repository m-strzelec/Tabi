package org.zzpj.tabi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zzpj.tabi.dto.TravelDTO;
import org.zzpj.tabi.entities.Account;
import org.zzpj.tabi.entities.Employee;
import org.zzpj.tabi.entities.Travel;
import org.zzpj.tabi.exceptions.AccountNotFoundException;
import org.zzpj.tabi.mappers.TravelMapper;
import org.zzpj.tabi.repositories.AccountRepository;
import org.zzpj.tabi.repositories.TravelRepository;

import java.util.List;
import java.util.UUID;

@Service
public class TravelService {

    @Autowired
    private TravelRepository travelRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TravelMapper travelMapper;

    public List<Travel> getAllTravels() {
        return travelRepository.findAll();
    }

    public Travel getTravelById(UUID id) {
        return travelRepository.findById(id).orElseThrow();
    }

    public Travel createTravel(TravelDTO travelDTO) {
        Travel travel = travelMapper.toEntity(travelDTO);
        return travelRepository.save(travel);
    }
}
