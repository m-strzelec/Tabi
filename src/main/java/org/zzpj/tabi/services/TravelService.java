package org.zzpj.tabi.services;

import jakarta.transaction.TransactionalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zzpj.tabi.dto.TravelUpdateDTO;
import org.zzpj.tabi.entities.Review;
import org.zzpj.tabi.entities.Travel;
import org.zzpj.tabi.exceptions.TravelNotFoundException;
import org.zzpj.tabi.exceptions.TravelWrongEmployeeEditException;
import org.zzpj.tabi.exceptions.TravelWrongGuestNumberException;
import org.zzpj.tabi.repositories.ReviewRepository;
import org.zzpj.tabi.dto.TravelDTO;
import org.zzpj.tabi.entities.Account;
import org.zzpj.tabi.entities.Employee;
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
    private ReviewRepository reviewRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TravelMapper travelMapper;

    public List<Travel> getAllTravels() {
        return travelRepository.findAll();
    }

    public Travel getTravelById(UUID id) throws TravelNotFoundException {
        return travelRepository.findById(id).orElseThrow(TravelNotFoundException::new);
    }

    public List<Review> getTravelReviews(UUID id) {
        return reviewRepository.findAllByTravelId(id);
    }

    public Travel createTravel(TravelDTO travelDTO) {
        Travel travel = travelMapper.toEntity(travelDTO);
        return travelRepository.save(travel);
    }

    public void editTravel(TravelUpdateDTO dto, String employeeLogin) throws AccountNotFoundException,
            TravelNotFoundException, TravelWrongGuestNumberException, TravelWrongEmployeeEditException {
        Employee employee = (Employee) accountRepository.findByName(employeeLogin).orElseThrow(AccountNotFoundException::new);
        Travel travel = travelRepository.findById(dto.getId()).orElseThrow(TravelNotFoundException::new);
        if (employee.getId() == travel.getCreatedBy().getId()) {
            travel.setTitle(dto.getTitle());
            travel.setDescription(dto.getDescription());
            if (dto.getGuestLimit() > travel.getGuestLimit()) {
                travel.setGuestLimit(dto.getGuestLimit());
            } else {
                throw new TravelWrongGuestNumberException();
            }
            travel.setCreatedBy(employee);
            travelRepository.save(travel);
        } else {
            throw new TravelWrongEmployeeEditException();
        }
    }
}
