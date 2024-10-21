package com.example.spotifywrapper.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlbumDTO {
    private int id;
    private String albumType;
    private int totalTracks;
    private String name;
    private String releaseDate;
    private int popularity;
}
