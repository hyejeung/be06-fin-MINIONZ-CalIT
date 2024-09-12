package minionz.backend.chat.message;

import lombok.RequiredArgsConstructor;
import minionz.backend.chat.chat_room.model.response.ReadMessageResponse;
import minionz.backend.chat.message.model.request.SendMessageRequest;
import minionz.backend.chat.message.model.request.UpdateMessageRequest;
import minionz.backend.common.responses.BaseResponse;
import minionz.backend.common.responses.BaseResponseStatus;
import minionz.backend.user.model.CustomSecurityUserDetails;
import minionz.backend.user.model.User;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    // 메세지 전송
    @MessageMapping("/room/{chatRoomId}/send")
    public void sendMessage(@Payload SendMessageRequest request, @AuthenticationPrincipal CustomSecurityUserDetails customUserDetails) {
        Long senderId = customUserDetails.getUser().getUserId();
        messageService.sendMessage(request.getChatRoomId(), request, null, senderId);
    }

    // 파일 전송
    @PostMapping("/message/sendFile")
    public void sendFile(
            @RequestParam("chatRoomId") Long chatRoomId,
            @RequestPart(name = "files") MultipartFile[] files,
            @AuthenticationPrincipal CustomSecurityUserDetails customUserDetails) {

        Long senderId = customUserDetails.getUser().getUserId();
        SendMessageRequest request = new SendMessageRequest();
        request.setChatRoomId(chatRoomId);
        // 파일 처리
        messageService.sendMessage(chatRoomId, request, files, senderId);
    }

    // 채팅 내역 조회
    @GetMapping(value = "/message/{chatRoomId}")
    public BaseResponse<List<ReadMessageResponse>> readMessage(@AuthenticationPrincipal CustomSecurityUserDetails customUserDetails, @PathVariable Long chatRoomId) {
        User user = customUserDetails.getUser();
        List<ReadMessageResponse> response = messageService.readMessage(chatRoomId, user.getUserId());
        return new BaseResponse<>(BaseResponseStatus.CHAT_HISTORY_RETRIEVAL_SUCCESS, response);
    }

    @MessageMapping("/room/{chatRoomId}/edit")
    public void updateMessage(@Payload UpdateMessageRequest request, @AuthenticationPrincipal CustomSecurityUserDetails customUserDetails) {
        Long senderId = customUserDetails.getUser().getUserId();
        messageService.updateMessage(request.getChatRoomId(), request, senderId);
    }

}

