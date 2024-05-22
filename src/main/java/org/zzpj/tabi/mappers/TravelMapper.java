package org.zzpj.tabi.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zzpj.tabi.dto.TravelDTO;
import org.zzpj.tabi.entities.Employee;
import org.zzpj.tabi.entities.Travel;
import org.zzpj.tabi.repositories.AccountRepository;

@Component
public class TravelMapper {
    @Autowired
    private AccountRepository accountRepository;

    public Travel toEntity(TravelDTO travelDTO) {
        Employee createdBy = (Employee) accountRepository.findById(travelDTO.getCreatedBy())
                .orElseThrow(() -> new IllegalArgumentException("Employee not found"));
        return new Travel(travelDTO.getId(),
                travelDTO.getTitle(),
                travelDTO.getDescription(),
                travelDTO.getPlace(),
                travelDTO.getBasePrice(),
                travelDTO.getStartDate(),
                travelDTO.getEndDate(),
                travelDTO.getGuestLimit(),
                createdBy);
    }

    static public TravelDTO toTravelDTO(Travel travel) {
        if (travel != null) {
            return new TravelDTO(travel.getId(),
                    travel.getTitle(),
                    travel.getDescription(),
                    travel.getPlace(),
                    travel.getBasePrice(),
                    travel.getStartDate(),
                    travel.getEndDate(),
                    travel.getGuestLimit(),
                    travel.getCreatedBy().getId());
        } else {
//            TODO: Throw Exception
            return null;
        }
    }
}
