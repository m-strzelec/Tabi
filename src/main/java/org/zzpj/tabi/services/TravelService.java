package org.zzpj.tabi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zzpj.tabi.entities.Review;
import org.zzpj.tabi.entities.Travel;
import org.zzpj.tabi.exceptions.AccountNotFoundException;
import org.zzpj.tabi.exceptions.TravelNotFoundException;
import org.zzpj.tabi.repositories.AccountRepository;
import org.zzpj.tabi.repositories.ReviewRepository;
import org.zzpj.tabi.dto.TravelDTO;
import org.zzpj.tabi.mappers.TravelMapper;
import org.zzpj.tabi.repositories.TravelRepository;
import java.util.List;
import java.util.UUID;

@Service
public class TravelService {

    @Autowired
    private TravelRepository travelRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private AccountRepository accountRepository;

    public List<Travel> getAllTravels() {
        return travelRepository.findAll();
    }

    public Travel getTravelById(UUID id) throws TravelNotFoundException {
        return travelRepository.findById(id).orElseThrow(TravelNotFoundException::new);
    }

    public List<Review> getTravelReviews(UUID id) {
        return reviewRepository.findAllByTravelId(id);
    }

    public Travel createTravel(TravelDTO travelDTO, UUID employeeId) throws AccountNotFoundException {
        Travel travel = TravelMapper.toTravel(
            travelDTO,
            accountRepository
                .findEmployeeById(employeeId)
                .orElseThrow(AccountNotFoundException::new)
        );
        return travelRepository.save(travel);
    }
}
