package com.amplio.amplio.service;

import com.amplio.amplio.forms.AlbumForm;
import com.amplio.amplio.forms.ArtistForm;
import com.amplio.amplio.models.Album;
import com.amplio.amplio.models.Artist;

public interface AdminService {
  public Artist addArtist(ArtistForm artistForm);

  public Album addAlbum(AlbumForm albumForm);
}
