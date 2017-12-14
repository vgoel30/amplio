package com.amplio.amplio.repository;

import com.amplio.amplio.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface UserRepository extends CrudRepository<User, Integer> {
  @Query(value = "select user_id from user_followed_artists where followed_artists_id = :artistId ", nativeQuery = true)
  Set<Integer> findUsersByFollowedArtist(@Param("artistId") Integer artistId);

  User findByUserName(String userName);

  User findUserById(Integer userId);

  List<User> findTop10UsersByUserNameContainingIgnoreCase(String userName);
}
