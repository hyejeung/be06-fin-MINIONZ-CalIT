package minionz.backend.meeting.model;

import jakarta.persistence.*;
import lombok.*;
import minionz.backend.common.BaseEntity;
import minionz.backend.issue.model.Issue;
import minionz.backend.meeting_participation.model.MeetingParticipation;
import minionz.backend.sprint.model.Sprint;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Meeting extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long meetingId;

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String meetingTitle;
    private String meetingContents;

    // Meeting : MeetingParticipation = 1 : N
    @OneToMany(mappedBy = "meeting", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MeetingParticipation> meetingParticipations = new ArrayList<>();

    // Meeting : Issue = 1 : N
    @OneToMany(mappedBy = "meeting", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Issue> issues = new ArrayList<>();

    // Meeting : Sprint = N : 1
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "sprint_id")
    private Sprint sprint;
}
