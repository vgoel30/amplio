package com.amplio.amplio.repository;

import com.amplio.amplio.models.Follower;
import org.springframework.data.repository.CrudRepository;

public interface FollowerRepository extends CrudRepository<Follower, Integer> {
  Follower findByUserId(Integer followerId);
}
