package br.gov.ufpr.convida.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.gov.ufpr.convida.domain.Event;
import br.gov.ufpr.convida.services.EventService;
import javassist.tools.rmi.ObjectNotFoundException;

@RestController
@RequestMapping(value = "/events")
public class EventResource {

    @Autowired
    private EventService service;
  

    @GetMapping
    public ResponseEntity<List<Event>> findAll() {
        List<Event> list = service.findAll();
        return ResponseEntity.ok().body(list);

    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Event> findById(@PathVariable String id) throws ObjectNotFoundException {
        Event obj = service.findById(id);
        return ResponseEntity.ok().body(obj);

    }

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody Event obj){
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@RequestBody Event event, @PathVariable String id){
    
        service.update(event);
        return ResponseEntity.status(200).build();
        
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id ) throws ObjectNotFoundException {

        service.delete(id);
        return ResponseEntity.status(200).build();
        
    }
    
    @GetMapping(value = "/namesearch")
    public ResponseEntity<List<Event>> findByName(@RequestParam(value = "text", defaultValue = "") String text){
        text = Search.decode(text);
        List<Event> events = service.findByName(text);
        return ResponseEntity.ok().body(events);

    }

    @GetMapping(value = "/typesearch")
    public ResponseEntity<List<Event>> findByType(@RequestParam(value = "text", defaultValue = "") String text){
        text = Search.decode(text);
        List<Event> events = service.findByType(text);
        return ResponseEntity.ok().body(events);
    }

    @GetMapping(value = "/nametypesearch")
    public ResponseEntity<List<Event>> findByNameinType(@RequestParam(value = "text", defaultValue = "") String text,
            @RequestParam(value = "type", defaultValue = "") String type){
            
        text = Search.decode(text);
        type = Search.decode(type);
        List<Event> events = service.findByNameType(text, type);
        return ResponseEntity.ok().body(events);           
    }

    @GetMapping(value = "/active")
    public ResponseEntity<List<Event>> findActiveEvent(){
        List<Event> list = service.findActiveEvents();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/today")
    public ResponseEntity<List<Event>> findToday(){
        List<Event> list = service.findEventToday();
        return ResponseEntity.ok().body(list);
    }


    @GetMapping(value = "/week")
    public ResponseEntity<List<Event>> findWeek(){
        List<Event> list = service.findEventWeek();
        return ResponseEntity.ok().body(list);
    }


    @GetMapping(value = "/multtype")
    public ResponseEntity<List<Event>> findByMultType(@RequestParam(value = "text", defaultValue = "") String text,
    @RequestParam(value = "text1", defaultValue = "") String text1,
    @RequestParam(value = "text2", defaultValue = "") String text2,
    @RequestParam(value = "text3", defaultValue = "") String text3,
    @RequestParam(value = "text4", defaultValue = "") String text4,
    @RequestParam(value = "text5", defaultValue = "") String text5){
            
        text = Search.decode(text);
        text1 = Search.decode(text1);
        text2 = Search.decode(text2);
        text3 = Search.decode(text3);
        text4 = Search.decode(text4);
        text5 = Search.decode(text5);

        if(text == ""){
            text = "x";
        }
        if(text1 == ""){
            text1 = "x";
        }
        if(text2 == ""){
            text2 = "x";
        }
        if(text3 == ""){
            text3 = "x";
        }
        if(text4 == ""){
            text4 = "x";
        }
        if(text5 == ""){
            text5 = "x";
        }

        
        List<Event> events = service.findByMultType(text, text1,text2,text3,text4,text5);
        return ResponseEntity.ok().body(events);           
    }

    @GetMapping(value = "/weektype")
    public ResponseEntity<List<Event>> findWeekType(@RequestParam(value = "text", defaultValue = "") String text,
    @RequestParam(value = "text1", defaultValue = "") String text1,
    @RequestParam(value = "text2", defaultValue = "") String text2,
    @RequestParam(value = "text3", defaultValue = "") String text3,
    @RequestParam(value = "text4", defaultValue = "") String text4,
    @RequestParam(value = "text5", defaultValue = "") String text5){
            
        text = Search.decode(text);
        text1 = Search.decode(text1);
        text2 = Search.decode(text2);
        text3 = Search.decode(text3);
        text4 = Search.decode(text4);
        text5 = Search.decode(text5);

        if(text == ""){
            text = "x";
        }
        if(text1 == ""){
            text1 = "x";
        }
        if(text2 == ""){
            text2 = "x";
        }
        if(text3 == ""){
            text3 = "x";
        }
        if(text4 == ""){
            text4 = "x";
        }
        if(text5 == ""){
            text5 = "x";
        }

        
        List<Event> events =  service.findWeekType(text, text1, text2, text3, text4, text5);
        return ResponseEntity.ok().body(events);           
    }

    @GetMapping(value = "/todaytype")
    public ResponseEntity<List<Event>> findTodayType(@RequestParam(value = "text", defaultValue = "") String text,
    @RequestParam(value = "text1", defaultValue = "") String text1,
    @RequestParam(value = "text2", defaultValue = "") String text2,
    @RequestParam(value = "text3", defaultValue = "") String text3,
    @RequestParam(value = "text4", defaultValue = "") String text4,
    @RequestParam(value = "text5", defaultValue = "") String text5){
            
        text = Search.decode(text);
        text1 = Search.decode(text1);
        text2 = Search.decode(text2);
        text3 = Search.decode(text3);
        text4 = Search.decode(text4);
        text5 = Search.decode(text5);

        if(text == ""){
            text = "x";
        }
        if(text1 == ""){
            text1 = "x";
        }
        if(text2 == ""){
            text2 = "x";
        }
        if(text3 == ""){
            text3 = "x";
        }
        if(text4 == ""){
            text4 = "x";
        }
        if(text5 == ""){
            text5 = "x";
        }

        
        List<Event> events =  service.findTodayType(text, text1, text2, text3, text4, text5);
        return ResponseEntity.ok().body(events);           
    }






}