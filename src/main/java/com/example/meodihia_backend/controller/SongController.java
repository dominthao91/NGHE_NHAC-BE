package com.example.meodihia_backend.controller;

import com.example.meodihia_backend.dto.request.CommentDto;
import com.example.meodihia_backend.dto.request.LikeUser;
import com.example.meodihia_backend.dto.request.SearchForm;
import com.example.meodihia_backend.dto.response.ResponeMessage;
import com.example.meodihia_backend.model.Comment;
import com.example.meodihia_backend.model.Likes;
import com.example.meodihia_backend.model.Song;
import com.example.meodihia_backend.model.User;
import com.example.meodihia_backend.security.userprincal.UserDetailServices;
import com.example.meodihia_backend.service.comment.CommentService;
import com.example.meodihia_backend.service.like.LikeService;
import com.example.meodihia_backend.service.song.SongService;
import com.example.meodihia_backend.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("")
public class SongController {
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
    @GetMapping("/list-song")
    public ResponseEntity<?> pageSong(@PageableDefault(sort = "name", direction = Sort.Direction.ASC)Pageable pageable){
        Page<Song> songPage = songService.findAll(pageable);
        if(songPage.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(songPage, HttpStatus.OK);
    }

    @PostMapping("/create-song")
    public ResponseEntity<?> createSong(@RequestBody Song song){
        User user = userDetailServices.getCurrentUser();
        if(user.getUsername().equals("Anonymous")){
            return new ResponseEntity<>(new ResponeMessage("Please login!"), HttpStatus.OK);
        }
        if (songService.existsByName(song.getName())) {
            return new ResponseEntity<>(new ResponeMessage("name_song_exist"), HttpStatus.OK);
        }
        if (song.getAvatar()==null) {
            return new ResponseEntity<>(new ResponeMessage("no_avatar_song"), HttpStatus.OK);
        }
        if (song.getFile()==null) {
            return new ResponseEntity<>(new ResponeMessage("no_music_song"), HttpStatus.OK);
        }
        Song  song1= new Song();
        song1.setName(song.getName());
        song1.setDescription(song.getDescription());
        song1.setFile(song.getFile());
        song1.setAvatar(song.getAvatar());
        song1.setSingers(song.getSingers());
        song1.setMusician(song.getMusician());
        song1.setUser(user);
        song1.setCount(0L);
        song1.setCountLike(0L);
        songService.save(song1);
        return new ResponseEntity<>(new ResponeMessage("yes"), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete-song/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        User user = userDetailServices.getCurrentUser();
        if(user.getUsername().equals("Anonymous")){
            return new ResponseEntity<>(new ResponeMessage("Please login!"), HttpStatus.OK);
        }
        Song song = songService.findById(id).get();
        if (song == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        songService.deleteById(id);
        return new ResponseEntity<>(new ResponeMessage("Done Delete!"),HttpStatus.OK);
    }

    @GetMapping("/find-song/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        return new ResponseEntity<>(songService.findById(id).get(), HttpStatus.OK);
    }

    @PutMapping("/edit-song/{id}")
    public ResponseEntity<?> editSong(@RequestBody Song song, @PathVariable Long id){
        User user = userDetailServices.getCurrentUser();
        if(user.getUsername().equals("Anonymous")){
            return new ResponseEntity<>(new ResponeMessage("Please login!"), HttpStatus.OK);
        }

        Optional<Song> song1 = songService.findById(id);
        if (!song1.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (songService.existsByName(song.getName())) {
            if (!song.getDescription().equals(song1.get().getDescription())) {
                song1.get().setDescription(song.getDescription());
                songService.save(song1.get());
                return new ResponseEntity<>(new ResponeMessage("yes"), HttpStatus.OK);
            }
            if (!song.getSingers().equals(song1.get().getSingers())) {
                song1.get().setSingers(song.getSingers());
                songService.save(song1.get());
                return new ResponseEntity<>(new ResponeMessage("yes"), HttpStatus.OK);
            }
            if (!song.getMusician().equals(song1.get().getMusician())) {
                song1.get().setMusician(song.getMusician());
                songService.save(song1.get());
                return new ResponseEntity<>(new ResponeMessage("yes"), HttpStatus.OK);
            }
            if (!song.getFile().equals(song1.get().getFile())) {
                song1.get().setFile(song.getFile());
                songService.save(song1.get());
                return new ResponseEntity<>(new ResponeMessage("yes"), HttpStatus.OK);
            }
            if (!song.getPlaylists().equals(song1.get().getPlaylists())) {
                song1.get().setPlaylists(song.getPlaylists());
                songService.save(song1.get());
                return new ResponseEntity<>(new ResponeMessage("yes"), HttpStatus.OK);
            }
            if (!song.getAvatar().equals(song1.get().getAvatar())) {
                song1.get().setAvatar(song.getAvatar());
                songService.save(song1.get());
                return new ResponseEntity<>(new ResponeMessage("yes"), HttpStatus.OK);
            }
            return new ResponseEntity<>(new ResponeMessage("no_name_song"), HttpStatus.OK);
        }
        song1.get().setName(song.getName());
        song1.get().setDescription(song.getDescription());
        song1.get().setFile(song.getFile());
        song1.get().setSingers(song.getSingers());
        song1.get().setMusician(song.getMusician());
        song1.get().setPlaylists(song.getPlaylists());
        song1.get().setAvatar(song.getAvatar());
        songService.save(song1.get());
        return new ResponseEntity<>(new ResponeMessage("yes"), HttpStatus.OK);
    }

    @GetMapping("/find-song-by-name/{name}")
    public ResponseEntity<?> findSongByName(@PathVariable("name") String name){
        Pageable pageable = PageRequest.of(0,10,Sort.by("id").descending());
        Page<Song> songPage = songService.findSongByNameContaining(name,pageable);
        List<Song> list = songPage.getContent();
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @PostMapping("/comment")
    public ResponseEntity<?>createCommentForSong(@RequestBody CommentDto comment){

        Comment newComment = new Comment();
        User user = userService.findById(comment.getIdUser()).get();
        Song song =songService.findById(comment.getIdSong()).get();
        String text = comment.getText();
        newComment.setUser(user);
        newComment.setSong(song);
        newComment.setText(text);
        commentService.save(newComment);
        return new ResponseEntity<>(new ResponeMessage("Post comment successfully"),HttpStatus.OK);
    }

    @GetMapping("/show-song-by-count")
    public ResponseEntity<?> showSongByCount(@PageableDefault(sort = "count", direction = Sort.Direction.DESC)Pageable pageable){
        Page<Song> songPage = songService.findAll(pageable);
        if(songPage.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(songPage, HttpStatus.OK);
    }

    @GetMapping("/song-list")
    public ResponseEntity<?> showListSongNew() {
        List<Song> songList = songService.findAll();
        if (songList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(songList, HttpStatus.OK);
    }

    @GetMapping("/latest")
    public ResponseEntity<Page<Song>> latest() {
        Pageable pageable = PageRequest.of(0, 4, Sort.by("id").descending());
        Page<Song> songPage = songService.findAllLaters(pageable);
        return new ResponseEntity<>(songPage, HttpStatus.OK);
    }
    @PutMapping("/updateCount")
    public ResponseEntity<String> updateSong(@RequestBody Long id) {
        Song song = songService.findById(id).get();
        Long count = song.getCount() + 1;
        song.setCount(count);
        System.out.println(song.getCount());
        songService.save(song);
        return new ResponseEntity<>("Ok", HttpStatus.OK);
    }

    @GetMapping("/song-count")
    public ResponseEntity<Page<Song>> count() {
        Pageable pageable = PageRequest.of(0, 6, Sort.by("count").descending());
        Page<Song> songPage = songService.findAllByCount(pageable);
        return new ResponseEntity<>(songPage, HttpStatus.OK);
    }

    @PostMapping("/like")
    public ResponseEntity<?> like(@RequestBody LikeUser likeUser) {
        User user = userService.findById(likeUser.getIdUser()).get();
        Song song = songService.findById(likeUser.getIdSong()).get();
        song.setCountLike(song.getCountLike() + 1);
        songService.save(song);
        Likes like = new Likes();
        like.setUser(user);
        like.setSong(song);
        likeService.save(like);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/like/{id}")
    public ResponseEntity<?> unlike(@PathVariable("id") Long idLike) {
        Likes like = likeService.findById(idLike).get();
        Song song = like.getSong();
        song.setCountLike(song.getCountLike() - 1);
        songService.save(song);
        likeService.deleteById(idLike);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/likes")
    public ResponseEntity<Page<Song>> likes() {
        Pageable pageable = PageRequest.of(0, 4, Sort.by("countLike").descending());
        Page<Song> songPage = songService.findAllByCount(pageable);
        return new ResponseEntity<>(songPage, HttpStatus.OK);
    }

    @GetMapping("/comment/{id}")
    public ResponseEntity<List<Comment>> fillAllComment(@PathVariable("id") Long idSong) {
        Song song = songService.findById(idSong).get();
        List<Comment> list = song.getCommentList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping ("/search/{searchKey}")
    public ResponseEntity<?> findSongByNameOrSinger(@PathVariable("searchKey") String name,Pageable pageable) {
        List<Song> songList= songService.findSongsBySingerNameorSongName(name, pageable).getContent();
        return new ResponseEntity<>(songList, HttpStatus.OK);
    }

    @GetMapping("/songs/{id}")
    public ResponseEntity<List<Song>> findAllSongByUser_Id(@PathVariable("id") Long idUser) {
        List<Song> songList;
        songList = (List<Song>) songService.findAllByUser_Id(idUser);
        return new ResponseEntity<>(songList, HttpStatus.OK);
    }
}

