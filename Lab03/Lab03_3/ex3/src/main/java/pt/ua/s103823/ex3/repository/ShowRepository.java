package pt.ua.s103823.ex3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.ua.s103823.ex3.model.Show;

@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {

    boolean existsBysource(String source);

    Show findBySource(String source);
}
