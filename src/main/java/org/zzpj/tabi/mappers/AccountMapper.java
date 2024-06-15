package org.zzpj.tabi.mappers;

import org.zzpj.tabi.dto.AccountDTO;
import org.zzpj.tabi.dto.ClientDTO;
import org.zzpj.tabi.dto.EmployeeDTO;
import org.zzpj.tabi.dto.account.AdminDTO;
import org.zzpj.tabi.dto.account.AdminOutputDTO;
import org.zzpj.tabi.entities.Account;
import org.zzpj.tabi.entities.Admin;
import org.zzpj.tabi.entities.Client;
import org.zzpj.tabi.entities.Employee;

public class AccountMapper {
    static public AccountDTO toAccountDTO(Account account) {
        if (account instanceof Client) {
            return new ClientDTO(
                    account.getId(),
                    account.getFirstName(),
                    account.getLastName(),
                    account.getLogin(),
                    account.getEmail(),
                    account.isLocked(),
                    account.getRole().toString(),
                    ((Client)account).getStatus()
            );
        } else if (account instanceof Employee) {
            return new EmployeeDTO(
                    account.getId(),
                    account.getFirstName(),
                    account.getLastName(),
                    account.getLogin(),
                    account.getEmail(),
                    account.isLocked(),
                    account.getRole().toString()
            );
        } else if (account instanceof Admin){
            return new AdminOutputDTO(
                    account.getId(),
                    account.getFirstName(),
                    account.getLastName(),
                    account.getLogin(),
                    account.getEmail(),
                    account.isLocked(),
                    account.getRole().toString()
            );
        } else {
            return null;
        }
    }
}
