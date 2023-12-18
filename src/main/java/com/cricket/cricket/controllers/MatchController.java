package com.cricket.cricket.controllers;

import com.cricket.cricket.entity.Match;
import com.cricket.cricket.services.CricketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v0/cricket")
@CrossOrigin("*")
@RequiredArgsConstructor
@Slf4j
public class MatchController {

    private final CricketService cricketService;

    @GetMapping("/live")
    public ResponseEntity<?> getLiveMatchScores() throws InterruptedException {
        System.out.println("getting live match");
        return new ResponseEntity<>(this.cricketService.getLiveMatchScores(), HttpStatus.OK);
    }

    @GetMapping("/point-table")
    public ResponseEntity<?> getCWC2023PointTable() {
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Match>> getAllMatches() {
        return new ResponseEntity<>(this.cricketService.getAllMatches(), HttpStatus.OK);
    }
}
