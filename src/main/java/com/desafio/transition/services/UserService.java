package com.desafio.transition.services;

import com.desafio.transition.domain.user.User;
import com.desafio.transition.domain.user.UserType;
import com.desafio.transition.dtos.UserDTO;
import com.desafio.transition.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void validateTransaction(User sender, BigDecimal amount) throws Exception {

        if (sender.getUserType() == UserType.MERCHANT) {
            throw new Exception("Usuário do tipo lojista não esta´autorizado a realizar transações!");
        }

        if (sender.getBalance().compareTo(amount) < 0) {
            throw new Exception("Saldo insuficiente!");
        }
    }

    public User findUserById(Long id) throws Exception {
        return this.userRepository.findUserById(id).orElseThrow(() -> new Exception("Usuario não encontrado!"));
    }

    public User createUser(UserDTO data) {
        User newUser = new User(data);
        this.saveUser(newUser);
        return newUser;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();

    }

    public void saveUser(User user) {
        this.userRepository.save(user);
    }
}
