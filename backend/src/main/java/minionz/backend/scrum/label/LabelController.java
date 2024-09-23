package minionz.backend.scrum.label;

import lombok.RequiredArgsConstructor;
import minionz.backend.common.exception.BaseException;
import minionz.backend.common.responses.BaseResponse;
import minionz.backend.common.responses.BaseResponseStatus;
import minionz.backend.scrum.label.model.request.CreateLabelRequest;
import minionz.backend.scrum.label.model.response.ReadLabelResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/label")
public class LabelController {
    private final LabelService labelService;

    @PostMapping("/{workspaceId}/sprint")
    public BaseResponse<BaseResponseStatus> createSprintLabel(@RequestBody CreateLabelRequest request) {

        try {
            labelService.createSprintLabel(request);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

        return new BaseResponse<>(BaseResponseStatus.SPRINT_LABEL_CREATE_SUCCESS);
    }

    @GetMapping("/{workspaceId}/sprint")
    public BaseResponse<List<ReadLabelResponse>> readSprintLabel(@RequestParam Long id) {
        List<ReadLabelResponse> response;

        try {
            response = labelService.readSprintLabel(id);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

        return new BaseResponse<>(BaseResponseStatus.SPRINT_LABEL_READ_SUCCESS, response);
    }

    @PostMapping("/{workspaceId}/task")
    public BaseResponse<BaseResponseStatus> createTaskLabel(@RequestBody CreateLabelRequest request) {

        try {
            labelService.createTaskLabel(request);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

        return new BaseResponse<>(BaseResponseStatus.TASK_LABEL_CREATE_SUCCESS);
    }

    @GetMapping("/{workspaceId}/task")
    public BaseResponse<List<ReadLabelResponse>> readTaskLabel(@RequestParam Long workspaceId) {
        List<ReadLabelResponse> response;

        try {
            response = labelService.readTaskLabel(workspaceId);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

        return new BaseResponse<>(BaseResponseStatus.SPRINT_LABEL_READ_SUCCESS, response);
    }

}
