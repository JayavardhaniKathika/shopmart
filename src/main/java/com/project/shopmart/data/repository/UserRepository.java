package com.project.shopmart.data.repository;

import com.project.shopmart.data.entity.User;
import com.project.shopmart.exceptions.EtAuthException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
        User findByEmail(String email);


}
