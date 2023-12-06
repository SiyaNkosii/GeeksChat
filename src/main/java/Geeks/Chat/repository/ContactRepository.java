package Geeks.Chat.repository;

import Geeks.Chat.entity.Contact;
import Geeks.Chat.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

//@CrossOrigin("http://localhost:4200")
public interface ContactRepository extends JpaRepository<Contact,Long> {
    //boolean existsByUserAndContactUser(User user, User contactUser);

}
