package com.announcementdesk.repositories;


import com.announcementdesk.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findByName(String name);


    User findByEmail(String email);
}
