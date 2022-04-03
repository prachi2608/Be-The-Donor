package com.beTheDonor.service;


import com.beTheDonor.entity.ApplicationUser;
import com.beTheDonor.entity.UserConfirmationToken;
//import com.beTheDonor.repository.PasswordTokenRepository;
import com.beTheDonor.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class ApplicationUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND = " User with email %s not found ";
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
//    private final PasswordTokenRepository passwordTokenRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        return userRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException(String.format(USER_NOT_FOUND,email)));
//
    }

    public String signUpUser(ApplicationUser applicationUser)
    {
        boolean userExist = userRepository.findByEmail(applicationUser.getEmail()).isPresent();
        if(userExist)
        {
            throw new IllegalStateException("Email already exist");
        }
        String encodedPassword = bCryptPasswordEncoder.encode(applicationUser.getPassword());
        applicationUser.setPassword(encodedPassword);


        userRepository.save(applicationUser);

        String token =  UUID.randomUUID().toString();

        UserConfirmationToken userConfirmationToken = new UserConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                applicationUser
        );

        confirmationTokenService.saveConfirmationToken(userConfirmationToken);

        return token ;
    }

    public void enableApplicationUser(String email) {
        userRepository.enableApplicationUser(email);

    }

    public List<ApplicationUser> findAll(){
        return userRepository.findAll();
    }
    public Optional<ApplicationUser> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public ApplicationUser findUserByEmail(String email)
    {
        return userRepository.getByEmail(email);
    }

    public void updateResetPasswordToken(String token, String email)
    {
        ApplicationUser applicationUser = userRepository.getByEmail(email);
        if(applicationUser != null)
        {
            applicationUser.setResetPasswordToken(token);
            userRepository.save(applicationUser);
        }
        else
        {
            throw new UsernameNotFoundException("User with gven email not found");
        }
    }

    public ApplicationUser getByResetPasswordToken(String token)
    {
        return userRepository.findByResetPasswordToken(token);
    }

    public void updatePassword(ApplicationUser applicationUser, String newPassword)
    {
        String encodedPassword = bCryptPasswordEncoder.encode(newPassword);
        applicationUser.setPassword(encodedPassword);
        applicationUser.setResetPasswordToken(null);
        userRepository.save(applicationUser);
    }


}
