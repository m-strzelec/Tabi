package org.zzpj.tabi.services;

import jakarta.persistence.OptimisticLockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zzpj.tabi.dto.travel.TravelUpdateDTO;
import org.zzpj.tabi.entities.Review;
import org.zzpj.tabi.entities.Travel;
import org.zzpj.tabi.exceptions.AccountNotFoundException;
import org.zzpj.tabi.exceptions.TravelNotFoundException;
import org.zzpj.tabi.exceptions.TravelWrongEmployeeEditException;
import org.zzpj.tabi.repositories.ReviewRepository;
import org.zzpj.tabi.dto.travel.TravelCreateDTO;
import org.zzpj.tabi.entities.Employee;
import org.zzpj.tabi.repositories.AccountRepository;
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

    public Travel createTravel(TravelCreateDTO travelCreateDTO, UUID employeeId) throws AccountNotFoundException {
        Travel travel = TravelMapper.toTravel(
                travelCreateDTO,
                accountRepository
                    .findEmployeeById(employeeId)
                    .orElseThrow(AccountNotFoundException::new)
        );
        return travelRepository.save(travel);
    }

    public void editTravel(TravelUpdateDTO dto, String employeeLogin) throws AccountNotFoundException,
            TravelNotFoundException, TravelWrongEmployeeEditException {
        Employee employee = (Employee) accountRepository.findByLogin(employeeLogin).orElseThrow(AccountNotFoundException::new);
        Travel travel = travelRepository.findById(dto.getId()).orElseThrow(TravelNotFoundException::new);
        //check if current version is equal to version specified in DTO
        if (!dto.getVersion().equals(travel.getVersion())) {
            throw new OptimisticLockException();
        }
        if (!employee.getId().equals(travel.getCreatedBy().getId())) {
            throw new TravelWrongEmployeeEditException();
        }
        travel.setTitle(dto.getTitle());
        travel.setDescription(dto.getDescription());
        travel.setCreatedBy(employee);
        travelRepository.save(travel);
    }
}
