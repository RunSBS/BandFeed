package com.bandfeed.wiki_service.infrastructure.client.spotify;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SpotifySearchResponse {
    private List<SpotifyTrackItem> items;
}
