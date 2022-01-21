package com.example.meodihia_backend.service.user;

import com.example.meodihia_backend.model.User;
import com.example.meodihia_backend.service.IGeneralService;

import java.util.Optional;

public interface IUserService extends IGeneralService<User> {
    Optional<User> findByUsername(String name); //Tim kiem User co ton tai trong DB khong?
    Optional<User> findById(Long id);
    Boolean existsByUsername(String username); //username da co trong DB chua, khi tao du lieu
    Boolean existsByEmail(String email); //email da co trong DB chua
    Boolean existsByPhoneNumber(String phoneNumber);
    Boolean existsByFullName(String fullName);


}
