package Geeks.Chat.repository;

import Geeks.Chat.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
//@CrossOrigin("http://localhost:4200")
public interface UserRepository extends JpaRepository<User,Long> {
// Page<User> findByUsernameContaining(@Param("username") String name , Pageable page);

 User findByEmail(String email);
 //Optional<User> findById(Long user1Id);

 List<User> findByUsernameContaining(String username);
// @Query("SELECT c.contactUser FROM Contact c WHERE c.user.userid = :loggedInUserId")
// List<User> getChatList(@Param("loggedInUserId") int loggedInUserId);
 Optional<User> findById(Long Id);
}
