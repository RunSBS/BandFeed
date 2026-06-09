package com.bandfeed.band_service.application;

import com.bandfeed.band_service.application.dto.command.CreateBandCommand;
import com.bandfeed.band_service.domain.model.Band;
import com.bandfeed.band_service.domain.model.BandMember;
import com.bandfeed.band_service.domain.model.TimelinePost;

import java.util.List;

public interface BandService {

    // Band
    Band create(CreateBandCommand command);
    Band findById(Long bandId);
    Band updateInfo(Long bandId, String name, String description, Long requesterId);
    void disband(Long bandId, Long requesterId);

    // BandMember
    BandMember inviteMember(Long bandId, Long userId, Long requesterId);
    void kickOrLeave(Long bandId, Long userId, Long requesterId);
    List<BandMember> findMembers(Long bandId);

    // TimelinePost
    TimelinePost createPost(Long bandId, Long authorId, String title, String content);
    TimelinePost findPost(Long postId);
    List<TimelinePost> findPosts(Long bandId);
    TimelinePost updatePost(Long postId, String title, String content, Long requesterId);
    void deletePost(Long postId, Long requesterId);
}
