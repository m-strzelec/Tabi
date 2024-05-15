package org.zzpj.tabi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zzpj.tabi.entities.Travel;
import org.zzpj.tabi.repositories.TravelRepository;

import java.util.List;

@Service
public class TravelService {

    @Autowired
    private TravelRepository travelRepository;

    public List<Travel> getAllTravels() {
        return travelRepository.findAll();
    }
}
