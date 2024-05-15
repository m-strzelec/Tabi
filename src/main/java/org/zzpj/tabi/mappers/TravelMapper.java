package org.zzpj.tabi.mappers;

import org.zzpj.tabi.dto.EmployeeDTO;
import org.zzpj.tabi.dto.TravelDTO;
import org.zzpj.tabi.entities.Employee;
import org.zzpj.tabi.entities.Travel;

public class TravelMapper {
    static public TravelDTO toTravelDTO(Travel travel) {
        if (travel != null) {
            Employee employee = travel.getCreatedBy();
            EmployeeDTO employeeDTO = (EmployeeDTO) AccountMapper.toAccountDTO(employee);
            return new TravelDTO(travel.getTitle(),
                    travel.getDescription(),
                    travel.getPlace(),
                    travel.getBasePrice(),
                    travel.getStartDate(),
                    travel.getEndDate(),
                    travel.getGuestLimit(),
                    employeeDTO);
        } else {
//            TODO: Throw Exception
            return null;
        }
    }
}
