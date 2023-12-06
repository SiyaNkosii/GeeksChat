package Geeks.Chat.repository;

import Geeks.Chat.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;


@RepositoryRestResource(collectionResourceRel = "users", path = "users")
//@CrossOrigin("http://localhost:4200")
public interface UserRepository extends JpaRepository<User,Long> {
// Page<User> findByUsernameContaining(@Param("username") String name , Pageable page);

 User findByEmail(String email);
 //Optional<User> findById(Long user1Id);

 List<User> findByusernameContaining(String username);
 @Query("SELECT c.contactUser FROM Contact c WHERE c.user.userid = :loggedInUserId")
 List<User> getChatList(@Param("loggedInUserId") int loggedInUserId);
 Optional<User> findById(Long Id);
}
