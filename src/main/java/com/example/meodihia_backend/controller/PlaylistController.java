package com.example.meodihia_backend.controller;

import com.example.meodihia_backend.dto.request.DeleteSong;
import com.example.meodihia_backend.dto.request.PlayListAdd;
import com.example.meodihia_backend.dto.request.PlayListForm;
import com.example.meodihia_backend.dto.response.ResponeMessage;
import com.example.meodihia_backend.model.Playlist;
import com.example.meodihia_backend.model.Song;
import com.example.meodihia_backend.model.User;
import com.example.meodihia_backend.security.userprincal.UserDetailServices;
import com.example.meodihia_backend.service.comment.CommentService;
import com.example.meodihia_backend.service.like.LikeService;
import com.example.meodihia_backend.service.playlist.PlaylistService;
import com.example.meodihia_backend.service.song.SongService;
import com.example.meodihia_backend.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("")
public class PlaylistController {
    @Autowired
    UserDetailServices userDetailServices;
    @Autowired
    SongService songService;
    @Autowired
    CommentService commentService;
    @Autowired
    UserService userService;
    @Autowired
    LikeService likeService;
    @Autowired
    PlaylistService playlistService;

    @GetMapping("/playlists/{id}")
    public ResponseEntity<List<Playlist>> findAllByUser_Id(@PathVariable("id") Long idUser) {
        List<Playlist> playlist = playlistService.findAllByUser_Id(idUser);
        return new ResponseEntity<>(playlist, HttpStatus.OK);
    }

    @DeleteMapping("/deletePlaylist/{id}")
    public ResponseEntity<?> deletePlaylist(@PathVariable("id") Long id) {
        Playlist playlist = playlistService.findById(id).get();
        if (playlist == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        playlistService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PostMapping("/createPlaylist")
    public ResponseEntity<?> creatList(@RequestBody PlayListForm playListForm) {
        User user = userService.findById(playListForm.getIdUser()).get();
        Date date = new Date();
        Playlist playlist = new Playlist();
        playlist.setName(playListForm.getName());
        playlist.setUser(user);
        playlist.setImage(playListForm.getFile());
        playlist.setDate(date);
        playlistService.save(playlist);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/playlist/{id}")
    public ResponseEntity<List<Song>> findAllSongOfPlaylist(@PathVariable("id") Long id) {
        Playlist playlist = playlistService.findById(id).get();
        List<Song> songList = (List<Song>) playlist.getSongs();
        return new ResponseEntity<>(songList, HttpStatus.OK);
    }

    @PutMapping("/delete-song-playlist")
    public ResponseEntity<?> removeSong(@RequestBody DeleteSong deleteSong) {
        Playlist playlist = playlistService.findById(deleteSong.getIdPlaylist()).get();
        Song song = songService.findById(deleteSong.getIdSong()).get();
        List<Song> songList = (List<Song>) playlist.getSongs();

//        Xoa song khoi playlist
        List<Song> newSongList = new ArrayList<>();
        for (int i = 0; i < songList.size(); i++) {
            if (deleteSong.getIdSong() != songList.get(i).getId()) {
                newSongList.add(songList.get(i));
            }
        }
        playlist.setSongs(newSongList);
        playlistService.save(playlist);

//        Xoa song khoi playlist
        List<Playlist> playlists = song.getPlaylists();
        List<Playlist> newListPlaylist = new ArrayList<>();
        for (int i = 0; i < playlists.size(); i++) {
            if (deleteSong.getIdPlaylist() != playlists.get(i).getId()) {
                newListPlaylist.add(playlists.get(i));
            }
        }
        song.setPlaylists(newListPlaylist);
        songService.save(song);
        return new ResponseEntity<>(new ResponeMessage("yes"), HttpStatus.OK);
    }

    @PostMapping("/addSongToList")
    public ResponseEntity<?> addSongToList(@RequestBody PlayListAdd playListAdd) {
        Long idPlaylist = playListAdd.getIdPlaylist();
        Playlist playlist = playlistService.findById(idPlaylist).get();
        List<Song> songList = (List<Song>) playlist.getSongs();
        List<Long> listIdSong = playListAdd.getIdSongs();
        for (int i = 0; i < listIdSong.size(); i++) {
            Song song = songService.findById(listIdSong.get(i)).get();
            if (song != null) {
                songList.add(song);
            }
        }
        playlist.setSongs(songList);
        playlistService.save(playlist);
        return new ResponseEntity<>(new ResponeMessage("yes"), HttpStatus.OK);
    }
}
