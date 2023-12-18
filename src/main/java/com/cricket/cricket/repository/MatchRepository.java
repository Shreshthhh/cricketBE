package com.cricket.cricket.repository;

import com.cricket.cricket.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MatchRepository extends JpaRepository<Match, Long> {

    @Query(nativeQuery = true, value = "select * from matches where deleted = 0 and team_heading = :teamHeading order" +
            " by id desc limit 1")
    Match findFirstByTeamHeading(String teamHeading);
}
