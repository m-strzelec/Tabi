package org.zzpj.tabi.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.zzpj.tabi.dto.AddCardDTO;
import org.zzpj.tabi.entities.Client;
import org.zzpj.tabi.repositories.AccountRepository;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Token;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PaymentService {

    @Value("${api.stripe.public-key}")
    private String publicKey;

    @Value("${api.stripe.private-key}")
    private String privateKey;

    @Autowired
    private AccountRepository accountRepository;

    public void addCardToClient(AddCardDTO cardDTO, Client client) {
        // TODO: Validate the input data, e.g. if `cardNumber` consists only of digits and has valid length.
        // TODO: Check if client already has a card assigned, and if so, throw an exception

        Stripe.apiKey = publicKey;

        Map<String, Object> card = new HashMap<>();
        card.put("number", cardDTO.getCardNumber());
        card.put("exp_month", Integer.parseInt(cardDTO.getExpMonth()));
        card.put("exp_year", Integer.parseInt(cardDTO.getExpYear()));
        card.put("cvc", cardDTO.getCvc());

        Map<String, Object> params = new HashMap<>();
        params.put("card", card);

        try {
            Token token = Token.create(params);

            if (token == null || token.getId() == null) {
                // TODO: Throw an exception
            }

            client.setCardToken(token.getId());
            accountRepository.save(client);

            log.info("Client " + client.getId() + " added card " + token.getId());
        } catch (StripeException e) {
            // TODO: Throw custom exception
            e.printStackTrace();
        }
    }

    public void chargeClient(Client client, BigDecimal amount, String title) {
        String cardToken = client.getCardToken();
        if (cardToken == null) {
            // TODO: Throw an exception
        }

        Stripe.apiKey = privateKey;

        String description = "Client " + client.getId() + " paid $" + amount + " for " + title;

        Map<String, Object> params = new HashMap<>();
        params.put("amount", amount.multiply(new BigDecimal(100)).setScale(0, RoundingMode.UP).intValue());
        params.put("currency", "USD"); // TODO: Add support for multiple currencies
        params.put("description", description);
        params.put("source", cardToken);

        try {
            Charge charge = Charge.create(params);

            if (!charge.getPaid()) {
                // TODO: Throw an exception
            }

            log.info(description);
        } catch (StripeException e) {
            // TODO: Throw custom exception
            e.printStackTrace();
        }
    }
}
