package br.com.webflux.starwarsapiclient.modules.controller;

import br.com.webflux.starwarsapiclient.modules.client.PeopleClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/people")
public class PeopleController {

    @Autowired
    private PeopleClient client;

    @GetMapping("page/{page}")
    public Flux<Object> findAll(@PathVariable Integer page) {
        return client.findAllPeople(page);
    }
}
