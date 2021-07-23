package com.flat.mogaco.rank;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RankRepository extends JpaRepository<Rank, Long> {

    List<Rank> findAllByMember_Channel(String channel);
}
