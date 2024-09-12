package minionz.backend.scrum.schedule;


import lombok.RequiredArgsConstructor;
import minionz.backend.common.exception.BaseException;
import minionz.backend.common.responses.BaseResponse;
import minionz.backend.common.responses.BaseResponseStatus;
import minionz.backend.scrum.schedule.request.ReadScheduleRequest;
import minionz.backend.scrum.schedule.response.ReadMonthlyResponse;
import minionz.backend.scrum.schedule.response.ReadWeeklyResponse;
import minionz.backend.user.model.CustomSecurityUserDetails;
import minionz.backend.user.model.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping("/workspace/{workspaceId}/monthly")
    public BaseResponse<ReadMonthlyResponse> readWorkspaceMonthly(@PathVariable Long workspaceId, ReadScheduleRequest request) {

        ReadMonthlyResponse response;

        try {
            response = scheduleService.readWorkspaceMonthly(workspaceId, request);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

        return new BaseResponse<>(BaseResponseStatus.WORKSPACE_MONTHLY_READ_SUCCESS, response);
    }

    @GetMapping("/myspace/monthly")
    public BaseResponse<ReadMonthlyResponse> readMyspaceMonthly(@AuthenticationPrincipal CustomSecurityUserDetails customUserDetails, ReadScheduleRequest request) {

        ReadMonthlyResponse response;

        try {
            response = scheduleService.readMyspaceMonthly(customUserDetails.getUser(), request);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

        return new BaseResponse<>(BaseResponseStatus.MY_MONTHLY_READ_SUCCESS, response);
    }

    @GetMapping("/workspace/{workspaceId}/weekly")
    public BaseResponse<ReadWeeklyResponse> readWorkspaceWeekly(@PathVariable Long workspaceId, ReadScheduleRequest request) {

        ReadWeeklyResponse response;

        try {
            response = scheduleService.readWorkspaceWeekly(workspaceId, request);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

        return new BaseResponse<>(BaseResponseStatus.WORKSPACE_MONTHLY_READ_SUCCESS, response);
    }

    @GetMapping("/myspace/weekly")
    public BaseResponse<ReadWeeklyResponse> readMyspaceWeekly(@AuthenticationPrincipal CustomSecurityUserDetails customUserDetails,ReadScheduleRequest request) {

        ReadWeeklyResponse response;

        try {
            response = scheduleService.readMyspaceWeekly(customUserDetails.getUser(), request);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

        return new BaseResponse<>(BaseResponseStatus.MY_WEEKLY_READ_SUCCESS, response);
    }
}
