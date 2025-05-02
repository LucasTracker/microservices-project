package br.com.erudio.controller;

import br.com.erudio.model.Book;
import br.com.erudio.proxy.CambioProxy;
import br.com.erudio.repository.BookRepository;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Objects;

@RequestMapping("/book-service")
@RestController
public class BookController {

    private final Environment environment;

    private final BookRepository bookRepository;

    private final CambioProxy cambioProxy;

    public BookController(Environment environment, BookRepository bookRepository, CambioProxy cambioProxy) {
        this.environment = environment;
        this.bookRepository = bookRepository;
        this.cambioProxy = cambioProxy;
    }

    @GetMapping("/{id}/{currency}")
    public Book findBook(@PathVariable(value = "id") Long id, @PathVariable(value = "currency") String currency){
        Book book = bookRepository.getReferenceById(id);

        Objects.requireNonNull(book,"Book not found");

        var cambio = cambioProxy.getCambio(book.getPrice(),"USD", currency);

        String port =  environment.getProperty("local.server.port");

        book.setEnvironment("Book port: " + port + " Cambio Port " + cambio.getEnvironment());
        book.setPrice(Objects.requireNonNull(cambio).getConvertedValue());
        return book;
    }


//    @GetMapping("/{id}/{currency}")
//    public Book findBook(@PathVariable(value = "id") Long id, @PathVariable(value = "currency") String currency){
//        Book book = bookRepository.getReferenceById(id);
//
//        Objects.requireNonNull(book,"Book not found");
//
//        HashMap<String,String> params = new HashMap<>();
//
//        params.put("amount", book.getPrice().toString());
//        params.put("from","USD");
//        params.put("to",currency);
//        var response = new RestTemplate().getForEntity("http://localhost:8000/cambio-service/{amount}/{from}/{to}", Cambio.class, params);
//
//
//        var cambio = response.getBody();
//
//        String port =  environment.getProperty("local.server.port");
//
//        book.setEnvironment(port);
//        book.setPrice(Objects.requireNonNull(cambio).getConvertedValue());
//        return book;
//    }
}
