package com.amplio.amplio.repository;

import com.amplio.amplio.models.Label;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface LabelRepository extends CrudRepository<Label, UUID> {
  public Label getLabelByName(String name);
}
