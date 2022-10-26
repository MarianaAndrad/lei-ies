package pt.ua.s103823.ex3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ua.s103823.ex3.exception.ResourceNotFoundException;
import pt.ua.s103823.ex3.model.Quote;
import pt.ua.s103823.ex3.model.Show;
import pt.ua.s103823.ex3.repository.QuoteRepository;
import pt.ua.s103823.ex3.repository.ShowRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ApiService {

    @Autowired
    private ShowRepository showRepository;
    @Autowired
    private QuoteRepository quoteRepository;

    public Show addShow(Show show) {
        //verificar se já existe
        if (showRepository.existsBysource(show.getSource())) {
            return null;
        }
        return showRepository.save(show);
    }

    public List<Show> addShows(List<Show> shows) {
        return showRepository.saveAll(shows);
    }

    public List<Show> findAllShows() {
        return showRepository.findAll();
    }

    public Map<String, Boolean> deleteShow(Long showId) throws ResourceNotFoundException {
        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new ResourceNotFoundException("Show not found for this id :: " + showId));

        //verificar se o show tem quotes
        List<Quote> quotes = quoteRepository.findAll();
        for (Quote quote : quotes) {
            if (quote.getShow().getId() == showId) {
                quoteRepository.delete(quote);
            }
        }
        showRepository.delete(show);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }


    public Quote addQuote(Quote quote) throws ResourceNotFoundException {
        //verificar se já existe a quote
        if (quoteRepository.existsByQuote(quote.getQuote())) {
            return null;
        }
        if(quote.getShow() != null){
            Show show = showRepository.findById(quote.getShow().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Show not found for this id :: " + quote.getShow().getId()));
            quote.setShow(show);
        }
        return quoteRepository.save(quote);
    }

    public List<Quote> findAllQuotes() {
        return quoteRepository.findAll();
    }


    public Map<String, Boolean> deleteQuote(Long quoteId) throws ResourceNotFoundException {
        Quote quote = quoteRepository.findById(quoteId)
                .orElseThrow(() -> new ResourceNotFoundException("Quote not found for this id :: " + quoteId));

        quoteRepository.delete(quote);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    public Map<String, Boolean> deleteShows() {
        //se eliminar todos os shows antes tens de elminar todos os quotes
        quoteRepository.deleteAll();
        showRepository.deleteAll();
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    public Map<String, Boolean> deleteQuotes() {
        quoteRepository.deleteAll();
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    public Show updateShow(Long showId, Show showDetails) throws ResourceNotFoundException {
        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new ResourceNotFoundException("Show not found for this id :: " + showId));

        show.setSource(showDetails.getSource());
        //show.setQuotes(showDetails.getQuotes());
        final Show updatedShow = showRepository.save(show);
        return updatedShow;
    }

    public Quote updateQuote(Long quoteId, Quote quoteDetails) throws ResourceNotFoundException {
        Quote quote = quoteRepository.findById(quoteId)
                .orElseThrow(() -> new ResourceNotFoundException("Quote not found for this id :: " + quoteId));

        quote.setQuote(quoteDetails.getQuote());
        quote.setShow(quoteDetails.getShow());
        final Quote updatedQuote = quoteRepository.save(quote);
        return updatedQuote;
    }

    // devolve um quote aleatório
    public Quote getRandomQuote() {
        //gerar um número aleatório
        long random = (long) (Math.random() * quoteRepository.count());
        //devolver o quote com esse id
        return quoteRepository.findById(random).get();
    }

    public Quote getRandomQuote(long idShow) {
        //ir buscar o show
        Show show = showRepository.findById(idShow).get();
        List<Quote> quotes = quoteRepository.findAll();
        List<Quote> quoteByShow = new ArrayList<>();
        for (Quote quote : quotes) {
            if (quote.getShow().getId() == idShow) {
                quoteByShow.add(quote);
            }
        }

        //gerar um número aleatório
        long random = (long) (Math.random() * quoteByShow.size());
        //devolver o quote com esse id
        return quoteByShow.get((int) random);
    }

    public Show getShowById(Long showId) {
        return showRepository.findById(showId).get();
    }

    public Quote getQuoteById(Long quoteId) {
        return quoteRepository.findById(quoteId).get();
    }
}
