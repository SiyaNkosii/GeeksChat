package Geeks.Chat.repository;

import Geeks.Chat.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

//@CrossOrigin("http://localhost:4200")
public interface ContactRepository extends JpaRepository<Contact,Long> {

    @Query("SELECT c FROM Contact c " +
            "JOIN c.user u " +
            "WHERE u.username = :username")
    Optional<Contact> findByUserUsername(String username);
}
