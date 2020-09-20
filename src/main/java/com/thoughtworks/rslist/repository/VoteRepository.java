package com.thoughtworks.rslist.repository;

import com.thoughtworks.rslist.dto.Vote;
import com.thoughtworks.rslist.entity.VotyEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VoteRepository extends CrudRepository<VotyEntity, Integer> {
    @Query(nativeQuery=true, value="select * from vote v where v.vote_time >= ? and v.vote_time <= ?")
    List<VotyEntity> findAllBetweenTime(String startTime, String endTime);
}
