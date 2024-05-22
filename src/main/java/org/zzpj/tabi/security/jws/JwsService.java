package org.zzpj.tabi.security.jws;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.zzpj.tabi.dto.AccountDTOs.AccountUpdateDTO;
import org.zzpj.tabi.entities.Account;
import org.zzpj.tabi.entities.Travel;
import org.zzpj.tabi.security.JwtService;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@Slf4j
public class JwsService {
    private static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();
    public String signAccount(Account account) {
        return Jwts.builder()
                .claim("login", account.getName())
                .claim("id", account.getId())
                .signWith(SECRET_KEY)
                .compact();
    }

    public String signDataFromAccountUpdateDTO(AccountUpdateDTO accountUpdateDTO) {
        return Jwts.builder()
                .claim("login", accountUpdateDTO.getLogin())
                .claim("id", accountUpdateDTO.getId())
                .signWith(SECRET_KEY)
                .compact();
    }

    public boolean isIfMatchValid(String if_match, AccountUpdateDTO accountUpdateDTO) {
        log.error("\"" + this.signDataFromAccountUpdateDTO(accountUpdateDTO) + "\"");
        return if_match.equals("\"" + this.signDataFromAccountUpdateDTO(accountUpdateDTO) + "\"");
    }

    public String signTravel(Travel travel) {
        return Jwts.builder()
                .claim("id", travel.getId())
                .claim("place", travel.getPlace())
                .signWith(SECRET_KEY)
                .compact();
    }
}
