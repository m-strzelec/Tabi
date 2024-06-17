package org.zzpj.tabi.security.jws;

import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.zzpj.tabi.dto.AccountDTOs.AccountUpdateDTO;
import org.zzpj.tabi.dto.TravelUpdateDTO;
import org.zzpj.tabi.entities.Account;
import org.zzpj.tabi.entities.Review;
import org.zzpj.tabi.entities.Travel;

import javax.crypto.SecretKey;

@Service
@Slf4j
public class JwsService {
    private static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();
    public String signAccount(Account account) {
        return Jwts.builder()
                .claim("login", account.getLogin())
                .claim("id", account.getId())
                .claim("version", account.getVersion())
                .signWith(SECRET_KEY)
                .compact();
    }

    public String signDataFromAccountUpdateDTO(AccountUpdateDTO accountUpdateDTO) {
        return Jwts.builder()
                .claim("login", accountUpdateDTO.getLogin())
                .claim("id", accountUpdateDTO.getId())
                .claim("version", accountUpdateDTO.getVersion())
                .signWith(SECRET_KEY)
                .compact();
    }

    public String signDataFromTravelUpdateDTO(TravelUpdateDTO travelUpdateDTO) {
        return Jwts.builder()
                .claim("id", travelUpdateDTO.getId())
                .claim("version", travelUpdateDTO.getVersion())
                .signWith(SECRET_KEY)
                .compact();
    }

    public boolean isIfMatchValid(String if_match, AccountUpdateDTO accountUpdateDTO) {
        //log.error("\"" + this.signDataFromAccountUpdateDTO(accountUpdateDTO) + "\"");
        return if_match.equals(this.signDataFromAccountUpdateDTO(accountUpdateDTO));
    }

    public boolean isIfMatchValidTravel(String if_match, TravelUpdateDTO travelUpdateDTO) {
        return if_match.equals(this.signDataFromTravelUpdateDTO(travelUpdateDTO));
    }

    public String signTravel(Travel travel) {
        return Jwts.builder()
                .claim("id", travel.getId())
                .claim("version", travel.getVersion())
                .signWith(SECRET_KEY)
                .compact();
    }
}
