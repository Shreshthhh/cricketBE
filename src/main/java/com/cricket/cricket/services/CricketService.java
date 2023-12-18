package com.cricket.cricket.services;

import com.cricket.cricket.entity.Match;
import com.cricket.cricket.enums.MatchStatus;
import com.cricket.cricket.repository.MatchRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CricketService {

    private final MatchRepository matchRepository;

    public List<Match> getLiveMatchScores() {
        List<Match> matches = new ArrayList<>();
        try {
            String url = "https://www.cricbuzz.com/cricket-match/live-scores";
            Document document = Jsoup.connect(url).get();
            Elements liveScoreElements = document.select("div.cb-mtch-lst.cb-tms-itm");
            for (Element match : liveScoreElements) {
                HashMap<String, String> liveMatchInfo = new LinkedHashMap<>();
                String teamsHeading = match.select("h3.cb-lv-scr-mtch-hdr").select("a").text();
                String matchNumberVenue = match.select("span").text();
                Elements matchBatTeamInfo = match.select("div.cb-hmscg-bat-txt");
                String battingTeam = matchBatTeamInfo.select("div.cb-hmscg-tm-nm").text();
                String score = matchBatTeamInfo.select("div.cb-hmscg-tm-nm+div").text();
                Elements bowlTeamInfo = match.select("div.cb-hmscg-bwl-txt");
                String bowlTeam = bowlTeamInfo.select("div.cb-hmscg-tm-nm").text();
                String bowlTeamScore = bowlTeamInfo.select("div.cb-hmscg-tm-nm+div").text();
                String textLive = match.select("div.cb-text-live").text();
                String completionText = match.select("div.cb-text-complete").text();

                String matchLink = match.select("a.cb-lv-scrs-well.cb-lv-scrs-well-live").attr("href").toString();

                Match newMatch = new Match();
                newMatch.setTeamHeading(teamsHeading);
                newMatch.setMatchDetails(matchNumberVenue);
                newMatch.setBattingTeam(battingTeam);
                newMatch.setBattingTeamScore(score);
                newMatch.setBowlingTeam(bowlTeam);
                newMatch.setBowlingTeamScore(bowlTeamScore);
                newMatch.setLiveText(textLive);
                newMatch.setMatchLink(matchLink);
                newMatch.setCompletionText(completionText);
                if (newMatch.getCompletionText().trim().isBlank()) {
                    newMatch.setStatus(MatchStatus.IN_PROGRESS);
                } else {
                    newMatch.setStatus(MatchStatus.COMPLETED);
                }

                matches.add(newMatch);
                updateMatch(newMatch);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return matches;
    }

    private void updateMatch(Match updateMatch) {

        Match match = matchRepository.findFirstByTeamHeading(updateMatch.getTeamHeading());
        if (match != null) {
            updateMatch.setId(match.getId());
        }
        matchRepository.save(updateMatch);
    }


    public List<Match> getAllMatches() {
        return matchRepository.findAll();
    }

}
