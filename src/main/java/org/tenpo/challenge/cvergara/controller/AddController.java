package org.tenpo.challenge.cvergara.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tenpo.challenge.cvergara.service.AddService;
import reactor.core.publisher.Mono;

import java.util.Objects;

@RestController
public class AddController {


     AddService addService;

    public AddController(AddService addService) {
        this.addService = addService;
    }

    @PostMapping("/add")
    public Mono<Integer> add(@RequestParam Integer num1, @RequestParam Integer num2) {


        return addService.addNumbers(num1, num2);
    }

}
