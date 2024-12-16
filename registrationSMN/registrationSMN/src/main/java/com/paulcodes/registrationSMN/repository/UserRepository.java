package com.paulcodes.registrationSMN.repository;

import com.paulcodes.registrationSMN.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//JpaRepository<Class, primaryKey in User>
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    //Optional is often used in repository methods to handle cases where the query result might be empty
    //to properly use Optional, you need to specify the generic type of the entity that you're working with

    // Custom query method to find a user by email
    Optional<User> findByEmail(String email);

    // Custom query method to find by id
    Optional<User> findById(int id);

}
