package com.example.meodihia_backend.controller;

import com.example.meodihia_backend.dto.request.*;
import com.example.meodihia_backend.dto.response.JwtResponse;
import com.example.meodihia_backend.dto.response.ResponeMessage;
import com.example.meodihia_backend.model.Role;
import com.example.meodihia_backend.model.RoleName;
import com.example.meodihia_backend.model.User;
import com.example.meodihia_backend.security.jwt.JwtProvider;
import com.example.meodihia_backend.security.jwt.JwtTokenFilter;
import com.example.meodihia_backend.security.userprincal.UserDetailServices;
import com.example.meodihia_backend.security.userprincal.UserPrinciple;
import com.example.meodihia_backend.service.role.RoleService;
import com.example.meodihia_backend.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("")
@CrossOrigin(origins = "*")
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private UserDetailServices userDetailServices;

    @PostMapping("/signup")
    public ResponseEntity<?> register(@Valid @RequestBody SignUpForm signUpForm) {
        if (userService.existsByUsername(signUpForm.getUsername())) {
            return new ResponseEntity<>(new ResponeMessage("no_user"), HttpStatus.OK);
        }
        if (userService.existsByPhoneNumber(signUpForm.getPhoneNumber())) {
            return new ResponseEntity<>(new ResponeMessage("no_phoneNumber"), HttpStatus.OK);
        }
        if (signUpForm.getAvatar() == null || signUpForm.getAvatar().trim().isEmpty()) {
            signUpForm.setAvatar("https://firebasestorage.googleapis.com/v0/b/chinhbeo-18d3b.appspot.com/o/avatar.png?alt=media&token=3511cf81-8df2-4483-82a8-17becfd03211");
        }
        User user = new User(signUpForm.getUsername(), signUpForm.getPhoneNumber(), passwordEncoder.encode(signUpForm.getPassword()),
                signUpForm.getAvatar());
        Set<String> strRoles = signUpForm.getRoles();
        Set<Role> roles = new HashSet<>();
        strRoles.forEach(role -> {
            switch (role) {
                case "admin":
                    Role adminRole = roleService.findByName(RoleName.ADMIN).orElseThrow(
                            () -> new RuntimeException("Role not found")
                    );
                    roles.add(adminRole);
                    break;
                case "pm":
                    Role pmRole = roleService.findByName(RoleName.PM).orElseThrow(() -> new RuntimeException("Role not found"));
                    roles.add(pmRole);
                    break;
                default:
                    Role userRole = roleService.findByName(RoleName.USER).orElseThrow(() -> new RuntimeException("Role not found"));
                    roles.add(userRole);
            }
        });
        user.setRoles(roles);
        userService.save(user);
        return new ResponseEntity<>(new ResponeMessage("yes"), HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> login(@Valid @RequestBody SignInForm signInForm) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInForm.getUsername(), signInForm.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.createToken(authentication);
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtResponse(userPrinciple.getId(), token, userPrinciple.getUsername(),userPrinciple.getFullName(), userPrinciple.getAvatar(), userPrinciple.getAddress(),userPrinciple.getEmail(),userPrinciple.getPhoneNumber(), userPrinciple.getAuthorities()));
    }

    @PutMapping("/change-avatar")
    public ResponseEntity<?> updateAvatar(@RequestBody ChangeFile changeFile) {
        User user = userDetailServices.getCurrentUser();
        if (user.getUsername().equals("Anonymous")) {
            return new ResponseEntity<>(new ResponeMessage("Please login!"), HttpStatus.OK);
        }
        user.setAvatar(changeFile.getFile());
        userService.save(user);
        return new ResponseEntity<>(new ResponeMessage("yes"), HttpStatus.OK);
    }

    @PutMapping("/change-profile")
    public ResponseEntity<?> editProfile(HttpServletRequest request,@Valid @RequestBody ChangeUser changeUser) {
        String jwt = JwtTokenFilter.getJwt(request);
        String username = jwtProvider.getUerNameFromToken(jwt);
        User user;
        try {
            if (userService.existsByFullName(changeUser.getFullName())) {
                return new ResponseEntity<>(new ResponeMessage("nofullname"), HttpStatus.OK);
            }
            if (userService.existsByEmail(changeUser.getEmail())) {
                return new ResponseEntity<>(new ResponeMessage("noemail"), HttpStatus.OK);
            }
            if (userService.existsByPhoneNumber(changeUser.getPhoneNumber())) {
                return new ResponseEntity<>(new ResponeMessage("nophonenumber"), HttpStatus.OK);
            }
            user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("user not found with -> username " + username));
            user.setFullName(changeUser.getFullName());
            user.setEmail(changeUser.getEmail());
            user.setAddress(changeUser.getAddress());
            user.setPhoneNumber(changeUser.getPhoneNumber());
            userService.save(user);
            return new ResponseEntity<>(new ResponeMessage("yes"), HttpStatus.OK);
        } catch (UsernameNotFoundException exception) {
            return new ResponseEntity<>(new ResponeMessage(exception.getMessage()), HttpStatus.NOT_FOUND);
        }

    }
        @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(HttpServletRequest request, @Valid @RequestBody ChangePassword changePassword){
        String jwt = JwtTokenFilter.getJwt(request);
        String username = jwtProvider.getUerNameFromToken(jwt);
        User user;
        try {
            user = userService.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User Not Found with -> username"+username));
            boolean matches = passwordEncoder.matches(changePassword.getOldPassword(), user.getPassword());
            if(changePassword.getNewPassword() != null && changePassword.getNewPassword().equals(changePassword.getRe_newPassword())){
                if(matches){
                    user.setPassword(passwordEncoder.encode(changePassword.getNewPassword()));
                    user.setRe_password(passwordEncoder.encode(changePassword.getRe_newPassword()));
                    userService.save(user);
                    return new ResponseEntity<>(new ResponeMessage("yes"), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(new ResponeMessage("no"), HttpStatus.BAD_REQUEST);
                }
            }
            return new ResponseEntity<>(new ResponeMessage("yes"), HttpStatus.BAD_REQUEST);
        } catch (UsernameNotFoundException exception){
            return new ResponseEntity<>(new ResponeMessage(exception.getMessage()), HttpStatus.NOT_FOUND);
        }
    }
}
