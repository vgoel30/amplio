package com.amplio.amplio.repository;

import com.amplio.amplio.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {
  public User findByUserName(String userName);

  public User findUserByUserId(Integer userId);

  public List<User> findTop10ByUserNameContainingIgnoreCase(String userName);
}
