package com.thoughtworks.rslist.repository;

import com.thoughtworks.rslist.entity.VotyEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VoteRepository extends CrudRepository<VotyEntity, Integer> {
    List<VotyEntity> findAll();
}
