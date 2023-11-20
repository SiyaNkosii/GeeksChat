package Geeks.Chat.repository;

import Geeks.Chat.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin("http://localhost:4200")
public interface ContactRepository extends JpaRepository<Contact,Long> {
}
