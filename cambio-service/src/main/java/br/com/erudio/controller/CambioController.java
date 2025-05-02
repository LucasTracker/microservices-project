package br.com.erudio.controller;

import br.com.erudio.model.Cambio;
import br.com.erudio.repository.CambioRepository;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@RestController
@RequestMapping("/cambio-service")
public class CambioController {

    private final Environment environment;
    private final CambioRepository cambioRepository;
    public CambioController(Environment environment, CambioRepository cambioRepository){
        this.environment = environment;
        this.cambioRepository = cambioRepository;
    }

    @GetMapping(value = "/{amount}/{from}/{to}")
    public Cambio getCambio(@PathVariable(name = "amount") BigDecimal amount,
                            @PathVariable(name = "from") String from,
                            @PathVariable(name = "to") String to) {

        var port =  environment.getProperty("local.server.port");
        var cambio = Objects.requireNonNull(cambioRepository.findByFromAndTo(from,to),"Currency Currency unsupported");

        BigDecimal conversionFactor = cambio.getConversionFactor();
        BigDecimal convertedValue = conversionFactor.multiply(amount);
        cambio.setEnvironment(port);
        cambio.setConvertedValue(convertedValue.setScale(2, RoundingMode.CEILING));
        return cambio;
    }
}
