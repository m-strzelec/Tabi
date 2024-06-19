package org.zzpj.tabi.repositories;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.zzpj.tabi.entities.Account;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.Optional;

@Testcontainers
@SpringBootTest
public class AccountRepositoryTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url", postgres::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username", postgres::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    AccountRepository accountRepository;

    @BeforeAll
    static void setUp() {
        postgres.start();
    }

    @AfterAll
    static void tearDown() {
        postgres.stop();
    }

    @Test
    void connectionEstablished() {
        assertThat(postgres.isCreated()).isTrue();
        assertThat(postgres.isRunning()).isTrue();
    }

    @Test
    void testFindByLogin() {
        String login = "foo";
        Optional<Account> foo = accountRepository.findByLogin(login);

        assertThat(foo.isPresent()).isTrue();
        assertThat(foo.orElseThrow().getLogin().equals(login)).isTrue();
    }

    @Test
    void testFindByEmail() {
        String email = "foo@email.com";
        Optional<Account> foo = accountRepository.findByEmail(email);

        assertThat(foo.isPresent()).isTrue();
        assertThat(foo.orElseThrow().getEmail().equals(email)).isTrue();
    }

    @Test
    void testFindAll() {
        assertThat(accountRepository.findAll().size()).isEqualTo(6);
    }
}
