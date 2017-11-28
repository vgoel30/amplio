package com.amplio.amplio.repository;

import com.amplio.amplio.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserRepository extends CrudRepository<User, Integer>{
    public User findByUserName(String userName);
    public User getUserByUserId(Integer userId);
}
