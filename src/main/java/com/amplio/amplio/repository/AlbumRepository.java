package com.amplio.amplio.repository;

import com.amplio.amplio.models.Album;
import com.amplio.amplio.models.Artist;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AlbumRepository extends CrudRepository<Album, Integer> {
  Album findById(Integer albumId);
  List<Album> findAlbumsByArtist(Artist artist);
}
