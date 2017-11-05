package com.amplio.amplio.dao;

import com.amplio.amplio.models.Advertisement;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface AdvertisementDao extends CrudRepository<Advertisement, UUID> {

}
