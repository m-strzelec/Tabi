package org.zzpj.tabi.mappers;

import org.zzpj.tabi.dto.TravelCreateDTO;
import org.zzpj.tabi.entities.Employee;
import org.zzpj.tabi.entities.Travel;

public class TravelMapper {
    static public Travel toTravel(TravelCreateDTO travelCreateDTO, Employee employee) {
        return new Travel(travelCreateDTO.getId(),
                travelCreateDTO.getTitle(),
                travelCreateDTO.getDescription(),
                travelCreateDTO.getPlace(),
                travelCreateDTO.getBasePrice(),
                travelCreateDTO.getStartDate(),
                travelCreateDTO.getEndDate(),
                travelCreateDTO.getGuestLimit(),
                employee);
    }

    static public TravelCreateDTO toTravelDTO(Travel travel) {
        return new TravelCreateDTO(
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
