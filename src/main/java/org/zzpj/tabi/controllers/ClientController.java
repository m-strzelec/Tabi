package org.zzpj.tabi.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zzpj.tabi.entities.Client;
import org.zzpj.tabi.services.ClientService;

@RestController
@RequestMapping("/api/users")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping
    public List<String> getAllClients() {
        List<Client> clientList = clientService.getAllClients();
        List<String> clientListDTO = new ArrayList<>();

        for (Client client : clientList) {
            clientListDTO.add(
                client.getId().toString()
                + " " + client.getName()
                + " " + client.getEmail()
            );
        }

        return clientListDTO;
    }
}
