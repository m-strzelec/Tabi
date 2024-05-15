package org.zzpj.tabi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zzpj.tabi.dto.TravelDTO;
import org.zzpj.tabi.entities.Travel;
import org.zzpj.tabi.mappers.TravelMapper;
import org.zzpj.tabi.services.TravelService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/travels")
public class TravelController {

    @Autowired
    TravelService travelService;

    @GetMapping
    public List<TravelDTO> getTravels() {
        return travelService
                .getAllTravels()
                .stream()
                .map(TravelMapper::toTravelDTO)
                .toList();
    }
}
