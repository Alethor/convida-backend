package br.gov.ufpr.convida.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.gov.ufpr.convida.repository.UserRepository;
import br.gov.ufpr.convida.domain.Event;
import br.gov.ufpr.convida.domain.User;

import javassist.tools.rmi.ObjectNotFoundException;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;
    @Autowired
    private PasswordEncoder bcrypt;


    public List<User> findAll() {
        return repo.findAll();
    }

    public User findById(String id) throws ObjectNotFoundException {
        User user = repo.findById(id).orElseThrow(() -> new ObjectNotFoundException("Usuário não cadastrado"));
        return user;

    }

    public User insert(User user) {
        
        user.setPassword(bcrypt.encode(user.getPassword()));
        return repo.insert(user);

    }

    public User insertFav(User user){
         return repo.save(user);
    }

    public void deleteFav(User user, Event event){
        user.getFav().remove(event);

    }

    public User update(User user){
        User newUser = repo.findById(user.getGrr()).orElse(null);
        updateData(newUser, user);
        return repo.save(newUser);
    }

    public void updateData(User newUser, User user){
        newUser.setName(user.getName());
        newUser.setLastName(user.getLastName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(bcrypt.encode(user.getPassword()));
        newUser.setBirth(user.getBirth());
    }

    public void delete(String id){
        repo.deleteById(id);
    }

    public void turnadm(User user){
        repo.save(user);
    }




}