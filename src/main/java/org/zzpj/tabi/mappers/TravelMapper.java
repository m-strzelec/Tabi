package org.zzpj.tabi.mappers;

import org.zzpj.tabi.dto.TravelDTO;
import org.zzpj.tabi.entities.Employee;
import org.zzpj.tabi.entities.Travel;

public class TravelMapper {
    static public Travel toTravel(TravelDTO travelDTO, Employee employee) {
        return new Travel(travelDTO.getId(),
                travelDTO.getTitle(),
                travelDTO.getDescription(),
                travelDTO.getPlace(),
                travelDTO.getBasePrice(),
                travelDTO.getStartDate(),
                travelDTO.getEndDate(),
                travelDTO.getGuestLimit(),
                employee);
    }

    static public TravelDTO toTravelDTO(Travel travel) {
        return new TravelDTO(travel.getId(),
                travel.getTitle(),
                travel.getDescription(),
                travel.getPlace(),
                travel.getBasePrice(),
                travel.getStartDate(),
                travel.getEndDate(),
                travel.getGuestLimit(),
                travel.getCreatedBy().getId());
    }
}
