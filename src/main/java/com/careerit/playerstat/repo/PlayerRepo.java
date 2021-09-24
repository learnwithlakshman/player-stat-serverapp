package com.careerit.playerstat.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.careerit.playerstat.domain.Player;

public interface PlayerRepo extends MongoRepository<Player,String> {

}
