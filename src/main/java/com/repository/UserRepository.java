package com.repository;

import java.util.List;
import com.web.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repo with all signed up users.
 * @author Theofanis Tsiantas
 * @version  2021.12.10 - version 1
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findByName(String name);
}

