package com.bandfeed.user_service.presentation.controller;

import com.bandfeed.user_service.presentation.docs.FollowControllerDocs;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/follows")
@RequiredArgsConstructor
public class FollowController implements FollowControllerDocs {
}
