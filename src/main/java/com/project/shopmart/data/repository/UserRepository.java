package com.project.shopmart.data.repository;

import com.project.shopmart.data.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
}
