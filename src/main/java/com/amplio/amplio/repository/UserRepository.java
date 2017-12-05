package com.amplio.amplio.repository;

import com.amplio.amplio.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {
  User findByUserName(String userName);
  User findUserByUserId(Integer userId);
  List<User> findTop10ByUserNameContainingIgnoreCase(String userName);
}
