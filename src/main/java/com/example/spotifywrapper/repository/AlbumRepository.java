package com.example.spotifywrapper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.spotifywrapper.model.Album;
import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Integer>{
    public List<Album> findByNameOrderByNameAsc(String name);
    public List<Album> findByAlbumType(String albumType);
    public List<Album> findByAlbumTypeAndNameOrderByIdAsc(String albumType, String name);
}
