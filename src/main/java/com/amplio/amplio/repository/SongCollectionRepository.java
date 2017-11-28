package com.amplio.amplio.repository;

import com.amplio.amplio.models.SongCollection;
import org.springframework.data.repository.CrudRepository;

public interface SongCollectionRepository extends CrudRepository<SongCollection, Integer> {
  SongCollection findByCollectionId(Integer collectionId);
}
