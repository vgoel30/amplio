package com.amplio.amplio.repository;

import com.amplio.amplio.models.Album;
import org.springframework.data.repository.CrudRepository;

public interface AlbumRepository extends CrudRepository<Album, Integer> {

}
