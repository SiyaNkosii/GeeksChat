package Geeks.Chat.repository;

import Geeks.Chat.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;


@RepositoryRestResource(collectionResourceRel = "users", path = "users")
//@CrossOrigin("http://localhost:4200")
public interface UserRepository extends JpaRepository<User,Long> {
// Page<User> findByUsernameContaining(@Param("username") String name , Pageable page);

 User findByEmail(String email);
 List<User> findByUsernameContaining(String username);
}
