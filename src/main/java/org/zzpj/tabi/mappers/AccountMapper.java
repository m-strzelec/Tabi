package org.zzpj.tabi.mappers;

import org.zzpj.tabi.dto.AccountDTO;
import org.zzpj.tabi.dto.ClientDTO;
import org.zzpj.tabi.dto.EmployeeDTO;
import org.zzpj.tabi.entities.Account;
import org.zzpj.tabi.entities.Client;
import org.zzpj.tabi.entities.Employee;

public class AccountMapper {
    static public AccountDTO toAccountDTO(Account account) {
        if (account instanceof Client) {
            return new ClientDTO(
                    account.getId(),
                    account.getFirstName(),
                    account.getLastName(),
                    account.getName(),
                    account.getEmail(),
                    account.isLocked(),
                    ((Client)account).getStatus()
            );
        } else if (account instanceof Employee) {
            return new EmployeeDTO(
                    account.getId(),
                    account.getFirstName(),
                    account.getLastName(),
                    account.getName(),
                    account.getEmail(),
                    account.isLocked()
            );
        } else {
            // TODO: Throw exception
            return null;
        }
    }
}
