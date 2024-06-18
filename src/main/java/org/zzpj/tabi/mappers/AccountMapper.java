package org.zzpj.tabi.mappers;

import org.zzpj.tabi.dto.account.AccountOutputDTO;
import org.zzpj.tabi.dto.account.ClientOutputDTO;
import org.zzpj.tabi.dto.account.EmployeeOutputDTO;
import org.zzpj.tabi.dto.account.AdminOutputDTO;
import org.zzpj.tabi.entities.Account;
import org.zzpj.tabi.entities.Admin;
import org.zzpj.tabi.entities.Client;
import org.zzpj.tabi.entities.Employee;

public class AccountMapper {
    static public AccountOutputDTO toAccountDTO(Account account) {
        if (account instanceof Client) {
            return new ClientOutputDTO(
                    account.getId(),
                    account.getLogin(),
                    account.getFirstName(),
                    account.getLastName(),
                    account.getEmail(),
                    account.getRole().toString(),
                    ((Client)account).getStatus(),
                    account.isLocked(),
                    account.getVersion()
            );
        } else if (account instanceof Employee) {
            return new EmployeeOutputDTO(
                    account.getId(),
                    account.getLogin(),
                    account.getFirstName(),
                    account.getLastName(),
                    account.getEmail(),
                    account.getRole().toString(),
                    account.isLocked(),
                    account.getVersion()
            );
        } else if (account instanceof Admin){
            return new AdminOutputDTO(
                    account.getId(),
                    account.getLogin(),
                    account.getFirstName(),
                    account.getLastName(),
                    account.getEmail(),
                    account.getRole().toString(),
                    account.isLocked(),
                    account.getVersion()
            );
        } else {
            return null;
        }
    }
}
