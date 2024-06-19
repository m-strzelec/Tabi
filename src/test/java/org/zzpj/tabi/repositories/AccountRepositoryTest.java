package org.zzpj.tabi.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zzpj.tabi.TestContainersRunner;
import org.zzpj.tabi.entities.Account;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.Optional;

@SpringBootTest
public class AccountRepositoryTest extends TestContainersRunner {

    @Autowired
    AccountRepository accountRepository;

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
