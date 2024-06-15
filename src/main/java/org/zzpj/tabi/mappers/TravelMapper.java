package org.zzpj.tabi.mappers;

import org.zzpj.tabi.dto.TravelCreateDTO;
import org.zzpj.tabi.dto.TravelOutputDTO;
import org.zzpj.tabi.entities.Employee;
import org.zzpj.tabi.entities.Travel;

public class TravelMapper {
    static public Travel toTravel(TravelCreateDTO travelCreateDTO, Employee employee) {
        return new Travel(
                travelCreateDTO.getTitle(),
                travelCreateDTO.getDescription(),
                travelCreateDTO.getPlace(),
                travelCreateDTO.getBasePrice(),
                travelCreateDTO.getStartDate(),
                travelCreateDTO.getEndDate(),
                travelCreateDTO.getMaxPlaces(),
                travelCreateDTO.getAvailablePlaces(),
                employee);
    }

    static public TravelOutputDTO toTravelDTO(Travel travel) {
        return new TravelOutputDTO(
                travel.getId(),
                travel.getTitle(),
                travel.getDescription(),
                travel.getPlace(),
                travel.getBasePrice(),
                travel.getStartDate(),
                travel.getEndDate(),
                travel.getMaxPlaces(),
                travel.getAvailablePlaces(),
                travel.getCreatedBy().getId());
    }
}
