package pt.ua.iess103823.userapplication.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pt.ua.iess103823.userapplication.Entidades.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {}

