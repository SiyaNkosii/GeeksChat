package Geeks.Chat.repository;

import Geeks.Chat.entity.Contact;
import Geeks.Chat.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;

//@CrossOrigin ("http://localhost:4200")
public interface ContactRepository extends JpaRepository<Contact,Long> {

    @Query("SELECT c FROM Contact c " +
            "JOIN c.user u " +
            "WHERE u.username = :username")
    Optional<Contact> findByUserUsername(String username);
    List<Contact> findByUser(User user);

    Optional<Contact> findByUserAndContactuser(User user, User contactUser);

}
