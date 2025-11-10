package com.modufit.chat.controller;

import com.modufit.chat.dto.ChatResponseDto;
import com.modufit.chat.dto.ChatRoomResponseDto;
import com.modufit.chat.service.ChatMessageService;
import com.modufit.chat.service.ChatRoomService;
import com.modufit.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/chat/rooms")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatMessageService chatMessageService;
    private final ChatRoomService chatRoomService;

    @GetMapping("/{chatRoomId}/messages/after")
    public ResponseEntity<ApiResponse<List<ChatResponseDto>>> getMessagesAfter(
            @PathVariable Long chatRoomId,
            @RequestParam LocalDateTime afterTime,
            @RequestParam(defaultValue = "50") int limit) {
        List<ChatResponseDto> messages = chatMessageService.getMessagesAfter(chatRoomId, afterTime, limit);
        return ResponseEntity.ok(ApiResponse.success("채팅방의 이전 메시지 목록 조회", messages));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<List<ChatRoomResponseDto>>> getUserChatRooms(@PathVariable Long userId) {
        List<ChatRoomResponseDto> chatRooms = chatRoomService.getChatRooms(userId);
        return ResponseEntity.ok(ApiResponse.success("사용자 채팅방 목록 조회", chatRooms));
    }
}
