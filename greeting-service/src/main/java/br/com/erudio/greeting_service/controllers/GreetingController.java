package br.com.erudio.greeting_service.controllers;

import br.com.erudio.greeting_service.configuration.GreetingConfiguration;
import br.com.erudio.greeting_service.model.Greeting;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {

    private static final String TEMPLATE = "%s, %s!";
    private final AtomicLong counter = new AtomicLong();

    private final GreetingConfiguration configuration;

    public GreetingController(GreetingConfiguration configuration) {
        this.configuration = configuration;
    }

    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "") String name){
        if (name.isEmpty()){
            name = configuration.getDefaultValue();
        }

        return new Greeting(counter.incrementAndGet(),String.format(TEMPLATE,configuration.getGreeting(), name));
    }

}
