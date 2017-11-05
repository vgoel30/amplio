package com.amplio.amplio.dao;

import com.amplio.amplio.models.Concert;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ConcertDao extends CrudRepository<Concert, UUID> {
}
