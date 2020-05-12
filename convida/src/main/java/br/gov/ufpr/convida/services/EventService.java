package br.gov.ufpr.convida.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import br.gov.ufpr.convida.domain.Event;
import br.gov.ufpr.convida.domain.Report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import br.gov.ufpr.convida.repository.EventRepository;
import javassist.tools.rmi.ObjectNotFoundException;

@Service
public class EventService{

    @Autowired
    private EventRepository repo;

    public List<Event> findAll(){
        return repo.findAll();
    }

    public Event findById(String id) throws ObjectNotFoundException {
        Optional <Event> event = repo.findById(id);
        return event.orElseThrow(() -> new ObjectNotFoundException("Este evento não existe em nossa base de dados"));

    }

    public Event insert(Event obj){
        return repo.insert(obj);
    }

    public Event update(Event event){
        Event newObj = repo.findById(event.getId()).orElse(null);
        updateData(newObj, event);
        return repo.save(newObj);
        
    }

    public void updateData(Event newObj, Event event) {
        newObj.setDateStart(event.getDateStart());
        newObj.setDateEnd(event.getDateEnd());
        newObj.setDesc(event.getDesc());
        newObj.setStartSub(event.getStartSub());
        newObj.setEndSub(event.getEndSub());
        newObj.setLink(event.getLink());
        newObj.setName(event.getName());
        newObj.setComplement(event.getComplement());
        newObj.setTarget(event.getTarget());
        newObj.setType(event.getType());
        newObj.setAuthor(event.getAuthor());
        newObj.setHrStart(event.getHrStart());
        newObj.setHrEnd(event.getHrEnd());
        newObj.setAddress(event.getAddress());
        newObj.setOnline(event.getOnline());
    }

    public void delete (String id) throws ObjectNotFoundException {
    
        repo.deleteById(id);
    }

    public void report(String id, Report report) throws ObjectNotFoundException{
        
        Event newEvent = repo.findById(id).orElse(null);

        newEvent.setReported(true);
        newEvent.getReports().add(report);
        repo.save(newEvent);


    }

    public void deactivate(Event ev){
        repo.save(ev);

    }

    public List<Event> findByName(String text){
        return repo.findByName(text);
    }

    public List<Event> findByType(String text){
        return repo.findByType(text);
    }

    public List<Event> findByNameType(String text, String type){
       
        return repo.findByNameTypeOrderByDateStart(text,type);
    }

    public List<Event> findByNameTypeOnline(String text, String type){
       
        return repo.findByNameTypeOnline(text,type);
    }




    public List<Report> findReports(String id){
        
        Event ne = repo.findById(id).orElse(null);
        return ne.getReports();
    }

    public List<Event> findReported(){
        return repo.findByReportsNotNull();
    }

    public List<Event> findMyEvents(String text){
        return repo.findMyEventsByAuthor(text);
    }

    public List<Event> findActiveEvents(){
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
        Date maxDate = new Date();
        maxDate = new Date(maxDate.getTime());
        try{
            String data = sdf.format(maxDate);
            maxDate = sdf.parse(data);

        }catch(ParseException e){
        
        }
        return repo.findByDateEndGreaterThanEqual(maxDate);
    }

   public List<Event> findEventToday(){
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
        Date maxDate = new Date();
        maxDate = new Date(maxDate.getTime() + 24 * 60 *60 * 1000);
        try{
            String data = sdf.format(maxDate);
            maxDate = sdf.parse(data);

        }catch(ParseException e){
        
        }
        return repo.findToday(maxDate);
    }

    public List<Event> findEventWeek(){
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
        Date maxDate = new Date();
        Date minDate = new Date();
        maxDate = new Date(maxDate.getTime() + 24 * 7 * 60 * 60 * 1000);
        try{
            String data = sdf.format(maxDate);
            maxDate = sdf.parse(data);

        }catch(ParseException e){
        
        }

        minDate = new Date(minDate.getTime());
        try{
            String data = sdf.format(minDate);
            minDate = sdf.parse(data);

        }catch(ParseException e){
        
        }
      
        return repo.findWeek(minDate, maxDate);
    }
    
    public List<Event> findByMultType(String text, String text1,String text2,String text3,String text4,String text5, String text6){
       
        return repo.findByMultType(text,text1,text2, text3, text4,text5,text6);
    }

    public List<Event> findWeekType(String text, String text1,String text2,String text3,String text4,String text5,String text6){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
        Date maxDate = new Date();
        Date minDate = new Date();
        maxDate = new Date(maxDate.getTime() + 24 * 7 * 60 * 60 * 1000);
        try{
            String data = sdf.format(maxDate);
            maxDate = sdf.parse(data);

        }catch(ParseException e){
        
        }

        minDate = new Date(minDate.getTime());
        try{
            String data = sdf.format(minDate);
            minDate = sdf.parse(data);

        }catch(ParseException e){
        
        }

        return repo.findWeekType(minDate, maxDate, text, text1, text2, text3, text4, text5, text6);
    }

    public List<Event> findTodayType(String text, String text1,String text2,String text3,String text4,String text5, String text6){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date minDate = new Date();
        Date maxDate = new Date();

        minDate = new Date(minDate.getTime());
        try{
            String data = sdf.format(minDate);
            minDate = sdf.parse(data);

        }catch(ParseException e){
        
        }
        maxDate = new Date(maxDate.getTime() - 24 * 60 * 60 * 1000);
        try{
            String data = sdf.format(maxDate);
            maxDate = sdf.parse(data);

        }catch(ParseException e){
        
        }

        
        System.out.println(" -------------- DATA:  " + maxDate);

        return repo.findTodayType(minDate, maxDate, text, text1, text2, text3, text4, text5, text6);
    }

    
    

}