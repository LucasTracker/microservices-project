package br.com.erudio.proxy;

import br.com.erudio.response.Cambio;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cambio-service")
public interface CambioProxy {

    @GetMapping(value = "/cambio-service/{amount}/{from}/{to}")
    public Cambio getCambio(@PathVariable(name = "amount") Double amount,
                            @PathVariable(name = "from") String from,
                            @PathVariable(name = "to") String to);

}
