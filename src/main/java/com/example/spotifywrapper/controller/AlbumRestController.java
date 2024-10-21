package com.example.spotifywrapper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spotifywrapper.model.AlbumDTO;
import com.example.spotifywrapper.service.AlbumService;
import java.text.ParseException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;


@RestController
@RequestMapping(path="/albums")
public class AlbumRestController {
    @Autowired
    AlbumService albumService;

    @PostMapping
    public ResponseEntity<?> createAlbum(@RequestBody AlbumDTO albumDto) throws ParseException {
        AlbumDTO resultDto = albumService.createAlbum(albumDto);

        return new ResponseEntity<>(resultDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> findByParams(@RequestParam(value = "album_type", required = false) String albumType, @RequestParam(required = false) String name) {
        List<AlbumDTO> resultList = albumService.findByParams(albumType, name);

        return ResponseEntity.ok().body(resultList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findAlbumById(@PathVariable int id) {
        AlbumDTO albumDto = albumService.findAlbumById(id);

        if (albumDto != null) {
            return new ResponseEntity<>(albumDto, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAlbumById(@PathVariable int id) {
        if (albumService.deleteAlbumById(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateAlbum(@RequestBody AlbumDTO albumDto) {
        AlbumDTO updatedAlbumDto = albumService.updateAlbum(albumDto);

        if (updatedAlbumDto != null) {
            return new ResponseEntity<>(updatedAlbumDto, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
