package com.amplio.amplio.repository;

import com.amplio.amplio.models.Admin;
import org.springframework.data.repository.CrudRepository;


public interface AdminRepository extends CrudRepository<Admin, Integer> {
  Admin findByUsername(String userName);
}
