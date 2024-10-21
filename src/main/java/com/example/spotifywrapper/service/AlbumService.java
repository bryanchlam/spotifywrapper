package com.example.spotifywrapper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.spotifywrapper.model.Album;
import com.example.spotifywrapper.model.AlbumDTO;
import com.example.spotifywrapper.repository.AlbumRepository;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AlbumService {
    @Autowired
    AlbumRepository albumRepository;
    
    private static ModelMapper modelMapper = new ModelMapper();

    public AlbumDTO createAlbum(AlbumDTO albumDto) throws ParseException {
        Album album = albumRepository.save(getAlbumEntity(albumDto));

        return getAlbumDTO(album);
    }

    public List<AlbumDTO> findByParams(String albumType, String name) {
        List<AlbumDTO> resultList = null;
        if (albumType != null && name != null) {
            resultList = getListOfAlbumDTO(albumRepository.findByAlbumTypeAndNameOrderByIdAsc(albumType, name));
        }
        else if (albumType != null) {
            resultList = getListOfAlbumDTO(albumRepository.findByAlbumType(albumType));
        }
        else if (name != null) {
            resultList = getListOfAlbumDTO(albumRepository.findByNameOrderByNameAsc(name));
        }
        else {
            resultList = getListOfAlbumDTO(albumRepository.findAll());
        }
        return resultList;
    }

    public AlbumDTO findAlbumById(int id) {
        if (albumRepository.existsById(id)) {
            return getAlbumDTO(albumRepository.findById(id).get());
        }
        else {
            return null;
        }
    }

    public boolean deleteAlbumById(int id) {
        if (albumRepository.existsById(id)) {
            albumRepository.deleteById(id);
            return true;
        }
        else {
            return false;
        }
    }

    public AlbumDTO updateAlbum(AlbumDTO albumDto) {
        int id = albumDto.getId();
        Album update = null;
        Album updatedAlbum = null;
        Optional<Album> checkExist = albumRepository.findById(id);
        if (checkExist.isPresent()) {
            ModelMapper uModelMapper = new ModelMapper();
            uModelMapper.getConfiguration().setSkipNullEnabled(true);

            Album currentAlbum = this.albumRepository.findById(id).get();
            TypeMap<Album, Album> propertyMap = uModelMapper.createTypeMap(Album.class, Album.class);
            propertyMap.setProvider(p -> currentAlbum);

            update = getAlbumEntity(albumDto);
            if (update.getTotalTracks() == 0) {
                update.setTotalTracks(currentAlbum.getTotalTracks());
            }
            updatedAlbum = uModelMapper.map(update, Album.class);

            updatedAlbum = albumRepository.save(updatedAlbum);
        }
        return getAlbumDTO(updatedAlbum);
    }

    private Album getAlbumEntity(AlbumDTO albumDto) {
        return(modelMapper.map(albumDto, Album.class));
    }

    private List<Album> getListOfAlbumEntity(List<AlbumDTO> listOfAlbumDto) {
        List<Album> resultList = new ArrayList<>();

        for (AlbumDTO albumDto: listOfAlbumDto) {
            resultList.add(getAlbumEntity(albumDto));
        }
        return resultList;
    }

    private AlbumDTO getAlbumDTO(Album album) {
        return(modelMapper.map(album, AlbumDTO.class));
    }

    private List<AlbumDTO> getListOfAlbumDTO(List<Album> listOfAlbumEntity) {
        List<AlbumDTO> resultList = new ArrayList<>();

        for (Album album: listOfAlbumEntity) {
            resultList.add(getAlbumDTO(album));
        }
        return resultList;
    }
}
