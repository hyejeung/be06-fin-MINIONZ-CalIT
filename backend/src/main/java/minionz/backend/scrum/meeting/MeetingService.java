package minionz.backend.scrum.meeting;

import lombok.RequiredArgsConstructor;
import minionz.backend.common.exception.BaseException;
import minionz.backend.common.responses.BaseResponseStatus;
import minionz.backend.scrum.meeting.model.Meeting;
import minionz.backend.scrum.meeting.model.request.CreateMeetingRequest;
import minionz.backend.scrum.meeting.model.response.ReadAllMeetingResponse;
import minionz.backend.scrum.meeting.model.response.ReadMeetingResponse;
import minionz.backend.scrum.meeting_participation.MeetingParticipationRepository;
import minionz.backend.scrum.meeting_participation.model.MeetingParticipation;
import minionz.backend.scrum.sprint.model.Sprint;
import minionz.backend.scrum.sprint.model.response.Label;
import minionz.backend.scrum.sprint.model.response.Participant;
import minionz.backend.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MeetingService {

    private final MeetingRepository meetingRepository;
    private final MeetingParticipationRepository meetingParticipationRepository;

    @Transactional
    public void createMeeting(User user, CreateMeetingRequest request, Long sprintId) {
        Meeting meeting = meetingRepository.save(
                Meeting
                        .builder()
                        .sprint(Sprint.builder().sprintId(sprintId).build())
                        .startDate(request.getStartDate())
                        .endDate(request.getEndDate())
                        .meetingTitle(request.getTitle())
                        .meetingContents(request.getContents())
                        .build()
        );

        meetingParticipationRepository.save(MeetingParticipation.builder().meeting(meeting).user(user).build());
        request.getParticipants().forEach(participantId ->
                meetingParticipationRepository.save(MeetingParticipation
                        .builder()
                        .meeting(meeting)
                        .user(User.builder().userId(participantId).build())
                        .build())
        );

//        TODO: 알람 보내야 합니다.

    }

    public ReadMeetingResponse readMeeting(Long meetingId) throws BaseException {
        Optional<Meeting> result = meetingRepository.findById(meetingId);

        if (result.isEmpty()) {
            throw new BaseException(BaseResponseStatus.MEETING_NOT_EXISTS);
        }
        Meeting meeting = result.get();

        return ReadMeetingResponse
                .builder()
                .id(meeting.getMeetingId())
                .title(meeting.getMeetingTitle())
                .contents(meeting.getMeetingContents())
                .startDate(meeting.getStartDate())
                .endDate(meeting.getEndDate())
                .createdAt(meeting.getCreatedAt())
                .participants(findParticipants(meeting))
                .build();
    }


    public List<Participant> findParticipants(Meeting meeting) {
        return meeting.getMeetingParticipations().stream().map(
                participant -> Participant.builder()
                        .id(participant.getUser().getUserId())
                        .userName(participant.getUser().getUserName())
                        .persona(participant.getUser().getPersona())
                        .isManager(true)
                        .build()
        ).toList();
    }

    public List<Label> findLabels(Meeting meeting) {
        return meeting.getNoteLabelSelects().stream().map(
                label -> Label
                        .builder()
                        .id(label.getNoteLabel().getNoteLabelId())
                        .labelName(label.getNoteLabel().getLabelName())
                        .color(label.getNoteLabel().getColor())
                        .build()
        ).toList();
    }


    public Page<ReadAllMeetingResponse> readAll(int page, int size) {
        Page<Meeting> result = meetingRepository.findAll(PageRequest.of(page, size));

        Page<ReadAllMeetingResponse> readMeetingResponses = result.map(meeting -> {
            List<Participant> participants = findParticipants(meeting);
            int participantCount = participants.size();  // 참여자 명수 계산

            return ReadAllMeetingResponse.builder()
                    .id(meeting.getMeetingId())
                    .title(meeting.getMeetingTitle())
                    .contents(meeting.getMeetingContents())
                    .startDate(meeting.getStartDate())
                    .endDate(meeting.getEndDate())
                    .createdAt(meeting.getCreatedAt())
                    .participants(participants)
                    .participantCount(participantCount)// 참여자 명수 추가
                    .labels(findLabels(meeting))
                    .build();
        });

        return readMeetingResponses;
    }
}
