package com.amplio.amplio.dao;

import com.amplio.amplio.models.Label;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface LabelDao extends CrudRepository<Label, UUID> {
}