package com.cricket.cricket.entity;

import com.cricket.cricket.enums.MatchStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Where;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "matches")
@Getter
@Setter
@ToString
@Where(clause = "deleted = 0")
@RequiredArgsConstructor
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String teamHeading;

    private String matchDetails;

    private String battingTeam;

    private String battingTeamScore;

    private String bowlingTeam;

    private String bowlingTeamScore;

    private String liveText;

    private String matchLink;

    private String completionText;

    @Enumerated(EnumType.STRING)
    private MatchStatus status;

    private LocalDateTime date = LocalDateTime.now();

    private boolean deleted = false;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Match match = (Match) o;
        return id != null && Objects.equals(id, match.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
