package com.example.meodihia_backend.controller;

import com.example.meodihia_backend.dto.response.ResponeMessage;
import com.example.meodihia_backend.model.Singer;
import com.example.meodihia_backend.model.User;
import com.example.meodihia_backend.security.userprincal.UserDetailServices;
import com.example.meodihia_backend.service.singer.SingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping
public class SingerController {
    @Autowired
    UserDetailServices userDetailServices;
    @Autowired
    SingerService singerService;

    @GetMapping("/list-singer")
    public ResponseEntity<?> pageSinger(@PageableDefault(sort = "name", direction = Sort.Direction.ASC) Pageable pageable){
        Page<Singer> singerPage = singerService.findAll(pageable);
        if(singerPage.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(singerPage, HttpStatus.OK);
    }

    @PostMapping("/create-singer")
    public ResponseEntity<?> createSong(@RequestBody Singer singer){
        User user = userDetailServices.getCurrentUser();
        if(user.getUsername().equals("Anonymous")){
            return new ResponseEntity<>(new ResponeMessage("Please login!"), HttpStatus.OK);
        }
        if (singerService.existsByName(singer.getName())) {
            return new ResponseEntity<>(new ResponeMessage("name_singer_exist"), HttpStatus.OK);
        }
        if (singer.getAvatar()==null) {
            return new ResponseEntity<>(new ResponeMessage("no_avatar_singer"), HttpStatus.OK);
        }
        singerService.save(singer);
        return new ResponseEntity<>(new ResponeMessage("yes"), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete-singer/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        User user = userDetailServices.getCurrentUser();
        if(user.getUsername().equals("Anonymous")){
            return new ResponseEntity<>(new ResponeMessage("Please login!"), HttpStatus.OK);
        }
        singerService.deleteById(id);
        return new ResponseEntity<>(new ResponeMessage("Done Delete!"),HttpStatus.OK);
    }

    @GetMapping("/find-singer/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        return new ResponseEntity<>(singerService.findById(id).get(), HttpStatus.OK);
    }

    @PutMapping("/edit-singer/{id}")
    public ResponseEntity<?> editSong(@RequestBody Singer singer, @PathVariable Long id){
        User user = userDetailServices.getCurrentUser();
        if(user.getUsername().equals("Anonymous")){
            return new ResponseEntity<>(new ResponeMessage("Please login!"), HttpStatus.OK);
        }
        Optional<Singer> singer1 = singerService.findById(id);
        if (!singer1.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (singerService.existsByName(singer.getName())) {
            if (!singer.getAge().equals(singer1.get().getAge())) {
                singer1.get().setAge(singer.getAge());
                singerService.save(singer1.get());
                return new ResponseEntity<>(new ResponeMessage("yes"), HttpStatus.OK);
            }
            if (!singer.getAvatar().equals(singer1.get().getAvatar())) {
                singer1.get().setAvatar(singer.getAvatar());
                singerService.save(singer1.get());
                return new ResponseEntity<>(new ResponeMessage("yes"), HttpStatus.OK);
            }
            if (!singer.getCountryside().equals(singer1.get().getCountryside())) {
                singer1.get().setCountryside(singer.getCountryside());
                singerService.save(singer1.get());
                return new ResponseEntity<>(new ResponeMessage("yes"), HttpStatus.OK);
            }
            return new ResponseEntity<>(new ResponeMessage("no_name_singer"), HttpStatus.OK);
        }
        singer1.get().setName(singer.getName());
        singer1.get().setAge(singer.getAge());
        singer1.get().setCountryside(singer.getCountryside());
        singer1.get().setAvatar(singer.getAvatar());
        singerService.save(singer1.get());
        return new ResponseEntity<>(new ResponeMessage("Done Edit!"), HttpStatus.OK);
    }

    @GetMapping("/find-singer-by-name/{name}")
    public ResponseEntity<?> findSongByName(@PathVariable("name") String name){
        Pageable pageable = PageRequest.of(0,10,Sort.by("id").descending());
        Page<Singer> singerPage = singerService.findSingerByNameContaining(name,pageable);
        List<Singer> list = singerPage.getContent();
        return new ResponseEntity<>(list,HttpStatus.OK);
    }
    @GetMapping("/singer-list")
    public ResponseEntity<?> showListSongNew() {
        List<Singer> singerList = singerService.findAll();
        if (singerList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(singerList, HttpStatus.OK);
    }
}
