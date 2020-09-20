package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.dto.Vote;
import com.thoughtworks.rslist.entity.VotyEntity;
import com.thoughtworks.rslist.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class voteController {
    @Autowired
    VoteRepository voteRepository;

    @GetMapping("/vote/listBetweenTime")
    public ResponseEntity getVoteListBetweenTime(@RequestParam String startTime, @RequestParam String endTime){
        List<VotyEntity> votes = voteRepository.findAllBetweenTime(startTime, endTime);
        return ResponseEntity.ok().body(votes);
    }
}
