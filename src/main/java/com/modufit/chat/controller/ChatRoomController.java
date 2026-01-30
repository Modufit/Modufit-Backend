package com.modufit.chat.controller;

import com.modufit.chat.dto.ChatResponseDto;
import com.modufit.chat.dto.ChatRoomDetailResponseDto;
import com.modufit.chat.dto.PageChatRoomResponseDto;
import com.modufit.chat.service.ChatMessageService;
import com.modufit.chat.service.ChatRoomService;
import com.modufit.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat/rooms")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatMessageService chatMessageService;
    private final ChatRoomService chatRoomService;

    @GetMapping("/{chatRoomId}/messages/recent")
    public ResponseEntity<ApiResponse<List<ChatResponseDto>>> getRecentMessages(
            @PathVariable Long chatRoomId,
            @RequestParam(defaultValue = "30") int limit){
        List<ChatResponseDto> messages = chatMessageService.getMessages(chatRoomId, limit);
        return ResponseEntity.ok(ApiResponse.success("채팅방의 이전 메시지 목록 조회", messages));
    }

    @GetMapping("/{chatRoomId}/messages/before")
    public ResponseEntity<ApiResponse<List<ChatResponseDto>>> getMessagesAfter(
            @PathVariable Long chatRoomId,
            @RequestParam Long lastMessageId,
            @RequestParam(defaultValue = "30") int limit) {
        List<ChatResponseDto> messages = chatMessageService.getMessagesBefore(chatRoomId, lastMessageId, limit);
        return ResponseEntity.ok(ApiResponse.success("채팅방의 이전 메시지 목록 조회", messages));
    }

    @GetMapping("/{chatRoomId}")
    public ResponseEntity<ApiResponse<ChatRoomDetailResponseDto>> getChatRoomParticipants(
            @PathVariable Long chatRoomId
    ) {
        ChatRoomDetailResponseDto participants = chatRoomService.getChatRoomDetail(chatRoomId);
        return ResponseEntity.ok(ApiResponse.success("채팅방 상세 조회", participants));
    }

    @GetMapping("/users/{userId}/list")
    public ResponseEntity<ApiResponse<PageChatRoomResponseDto>> getUserChatRooms(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "8", required = false) int size
    ) {
        PageChatRoomResponseDto chatRooms = chatRoomService.getUserChatRooms(userId, page, size);
        return ResponseEntity.ok(ApiResponse.success("사용자 채팅방 목록 조회", chatRooms));
    }

    @PutMapping("/{chatRoomId}/participants/{participantId}")
    public ResponseEntity<ApiResponse<Void>> leftAtParticipant(
            @PathVariable Long chatRoomId, @PathVariable Long participantId) {

        return ResponseEntity.ok().build();
    }
}
