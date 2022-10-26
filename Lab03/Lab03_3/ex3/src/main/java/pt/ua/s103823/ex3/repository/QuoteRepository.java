package pt.ua.s103823.ex3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.ua.s103823.ex3.model.Quote;

import java.util.List;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long> {
    boolean existsByQuote(String quote);

}
