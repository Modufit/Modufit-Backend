package com.modufit.chat.controller;

import com.modufit.chat.service.ChatParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat/rooms")
@RequiredArgsConstructor
public class ChatParticipantController {
    private final ChatParticipantService participantService;

}
