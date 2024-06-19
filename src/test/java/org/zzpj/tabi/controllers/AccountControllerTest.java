package org.zzpj.tabi.controllers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.zzpj.tabi.TestContainersRunner;
import org.zzpj.tabi.dto.account.AccountOutputDTO;
import org.zzpj.tabi.dto.account.ChangeSelfPasswordDTO;
import org.zzpj.tabi.dto.account.LoginDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AccountControllerTest extends TestContainersRunner {

    @Autowired
    TestRestTemplate restTemplate;

    String login(String login, String password) {
        LoginDTO loginDTO = new LoginDTO(login, password);

        HttpEntity<LoginDTO> request = new HttpEntity<LoginDTO>(loginDTO);
        ResponseEntity<?> response = restTemplate.postForEntity("/api/auth/login", request, String.class);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        return response.getBody().toString();
    }

    @Test
    void testGetSelfIfAuthenticatedThenReturnsOkAndAccountOutputDTO() throws JsonProcessingException, Exception {
        String token = login("foo", "foo");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<AccountOutputDTO> response = restTemplate.exchange(
            "/api/accounts/self",
            HttpMethod.GET,
            request,
            AccountOutputDTO.class,
            1
        );

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000001"));
        assertThat(response.getBody().getLogin()).isEqualTo("foo");
        assertThat(response.getBody().getEmail()).isEqualTo("foo@email.com");
        assertThat(response.getBody().getRole()).isEqualTo("CLIENT");
    }

    @Test
    void testGetSelfIfNotAuthenticatedThenReturnsForbidden() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<?> response = restTemplate.exchange(
            "/api/accounts/self",
            HttpMethod.GET,
            request,
            String.class,
            1
        );

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    void testChangePasswordIfValidThenReturnsOk() {
        String token = login("foo", "foo");

        ChangeSelfPasswordDTO dto = new ChangeSelfPasswordDTO("foo", "oof");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<ChangeSelfPasswordDTO> request = new HttpEntity<>(dto, headers);

        ResponseEntity<?> response = restTemplate.exchange(
            "/api/accounts/change-password-self",
            HttpMethod.POST,
            request,
            String.class,
            1
        );

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void testChangePasswordIfOldPasswordInvalidThenReturnsBadRequest() {
        String token = login("foo", "foo");

        ChangeSelfPasswordDTO dto = new ChangeSelfPasswordDTO("asdf", "oof");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<ChangeSelfPasswordDTO> request = new HttpEntity<>(dto, headers);

        ResponseEntity<?> response = restTemplate.exchange(
            "/api/accounts/change-password-self",
            HttpMethod.POST,
            request,
            String.class,
            1
        );

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
