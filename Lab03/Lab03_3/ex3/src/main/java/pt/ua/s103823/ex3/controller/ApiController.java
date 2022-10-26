package pt.ua.s103823.ex3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pt.ua.s103823.ex3.exception.ResourceNotFoundException;
import pt.ua.s103823.ex3.model.Quote;
import pt.ua.s103823.ex3.model.Show;
import pt.ua.s103823.ex3.repository.ShowRepository;
import pt.ua.s103823.ex3.service.ApiService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ApiController {
    @Autowired
    private ApiService apiService;

    //LIstar Shows e Quotes
    @GetMapping("/api/shows")
    public List<Show> getShows() {
        return apiService.findAllShows();
    }

    @GetMapping("/api/quotes")
    public List<Quote> getQuotes() {
        return apiService.findAllQuotes();
    }

    //Adicionar Show, listShows e Quote
    @PostMapping("/api/addshow")
    public Show addShow(@Valid @RequestBody Show show) {
        return apiService.addShow(show);
    }

    @PostMapping("/api/addquote")
    public Quote addQuote(@Valid @RequestBody Quote quote) throws ResourceNotFoundException {
        return apiService.addQuote(quote);
    }

    //Apagar Show e Quote By id

    @DeleteMapping("api/deleteshow/{id}")
    public Map<String, Boolean> deleteShow(@PathVariable(value = "id") Long showId) throws ResourceNotFoundException {
        return apiService.deleteShow(showId);
    }

    @DeleteMapping("api/deletequote/{id}")
    public Map<String, Boolean> deleteQuote(@PathVariable(value = "id") Long quoteId) throws ResourceNotFoundException {
        return apiService.deleteQuote(quoteId);
    }

    //Apagar todos Show e todos os Quote
    @DeleteMapping("api/delete")
    public Map<String, Boolean> deleteShows() throws ResourceNotFoundException {
        return apiService.deleteShows();
    }

    @DeleteMapping("api/deleteallquotes")
    public Map<String, Boolean> deleteQuotes() throws ResourceNotFoundException {
        return apiService.deleteQuotes();
    }

    //Update Show e Quote
    @PutMapping("/api/updateshow/{id}")
    public Show updateShow(@PathVariable(value = "id") Long showId,
                           @Valid @RequestBody Show showDetails) throws ResourceNotFoundException {
        return apiService.updateShow(showId, showDetails);
    }

    @PutMapping("/api/updatequote/{id}")
    public Quote updateQuote(@PathVariable(value = "id") Long quoteId,
                           @Valid @RequestBody Quote quoteDetails) throws ResourceNotFoundException {
        return apiService.updateQuote(quoteId, quoteDetails);
    }

    //Get Quote random
    @GetMapping("/api/quote")
    public Quote getRandomQuote(@RequestParam(value = "id", required = false) long idShow) throws ResourceNotFoundException {
        if(idShow == 0) {
            return apiService.getRandomQuote();
        } else {
            return apiService.getRandomQuote(idShow);
        }
    }

    //Get Quote by id
    @GetMapping("/api/quote/{id}")
    public Quote getQuoteById(@PathVariable(value = "id") Long quoteId) throws ResourceNotFoundException {
        return apiService.getQuoteById(quoteId);
    }

    //Get Show by id
    @GetMapping("/api/show/{id}")
    public Show getShowById(@PathVariable(value = "id") Long showId) throws ResourceNotFoundException {
        return apiService.getShowById(showId);
    }

}
