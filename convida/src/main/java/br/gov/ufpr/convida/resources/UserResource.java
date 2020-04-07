package br.gov.ufpr.convida.resources;

import java.net.URI;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.gov.ufpr.convida.config.SendEmail;
import br.gov.ufpr.convida.domain.AccountCredentials;
import br.gov.ufpr.convida.domain.Bfav;
import br.gov.ufpr.convida.domain.Event;
import br.gov.ufpr.convida.domain.User;
import br.gov.ufpr.convida.services.EventService;
import br.gov.ufpr.convida.services.UserService;
import javassist.tools.rmi.ObjectNotFoundException;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

    @Autowired
    private UserService service;

    @Autowired
    private EventService eserv;

    @Autowired
    PasswordEncoder bcrypt;

    @Autowired
    private SendEmail email;

   


    @GetMapping
    public ResponseEntity<List<User>> findAll(){
        List <User> users = service.findAll();
        return ResponseEntity.ok().body(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable String id) throws ObjectNotFoundException {
        User obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping 
    public ResponseEntity<Void> insert(@RequestBody User user){
        user = service.insert(user);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getGrr()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@RequestBody User user, @PathVariable String id){
        user.setGrr(id);
        user = service.update(user);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/checkpass")
    public ResponseEntity<Boolean> passcheck(@RequestBody AccountCredentials user) throws ObjectNotFoundException {
        User u = service.findById(user.getUsername());
        Boolean x =  bcrypt.matches(user.getPassword(), u.getPassword());
        return ResponseEntity.ok().body(x);  

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteuser(@PathVariable String id){
        service.delete(id);
        return ResponseEntity.status(200).build();
    }
    



    @PostMapping(value = "/fav")
    public ResponseEntity<Void> updateFav(@RequestBody Bfav ids) throws ObjectNotFoundException {
        
        User obj = service.findById(ids.getGrr());
        Event event = eserv.findById(ids.getId());

        obj.getFav().add(event);
        service.insertFav(obj);

        return ResponseEntity.noContent().build();

    }

    @PostMapping(value="/isfav")
    
    public ResponseEntity<Void> containsEvent(@RequestBody Bfav ids) throws ObjectNotFoundException {
       
         User user = service.findById(ids.getGrr());
        Event ev = eserv.findById(ids.getId());

       if(user.getFav().contains(ev)){
           return ResponseEntity.ok().build();

       }else{
           return ResponseEntity.status(404).build();
       }
        
    }

    @PostMapping(value ="/recovery")
    public ResponseEntity<Void> recovery(@RequestBody AccountCredentials a) throws ObjectNotFoundException{

        User u = service.findById(a.getUsername());

        if(u!= null){
                Random r = new Random();
                Integer nPass = r.nextInt(99999);
                String s = "convida" + nPass.toString();


                email.sendEmail(u.getEmail(), s);

                u.setPassword(s);
                service.update(u);

                return ResponseEntity.noContent().build();
        }else{
            System.out.println("-------------- o erro ta nesse outro if, o de fora --------------");
            return ResponseEntity.status(401).build();
        }
    }

    @PostMapping(value = "/rfav")
    public ResponseEntity<Void> delete(@RequestBody Bfav ids) throws ObjectNotFoundException {

        User user = service.findById(ids.getGrr());
        Event event = eserv.findById(ids.getId());

        user.getFav().remove(event);
        service.insertFav(user);

        return ResponseEntity.noContent().build();
    }
    
    @GetMapping(value = "/fav/{id}")
    public ResponseEntity<List<Event>> findFav(@PathVariable String id) throws ObjectNotFoundException {
        
        User user = service.findById(id);
        return ResponseEntity.ok().body(user.getFav());
    }

    @GetMapping(value ="/myevents")
    public ResponseEntity<List<Event>> myEvents(@RequestParam(value = "text", defaultValue = "") String text){
       text = Search.decode(text);
       List<Event> events = eserv.findMyEvents(text);
       return ResponseEntity.ok().body(events);

    }

}