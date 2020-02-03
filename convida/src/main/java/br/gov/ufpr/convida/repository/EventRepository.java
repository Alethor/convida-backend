package br.gov.ufpr.convida.repository;

import br.gov.ufpr.convida.domain.Event;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface EventRepository extends MongoRepository<Event, String>{

    @Query("{'name':{ $regex: ?0, $options: 'i'} }")
    List<Event> findByName(String text);

    @Query("{'type':{ $regex: ?0, $options: 'i'} }")
    List<Event> findByType(String text);

    List<Event> findMyEventsByAuthor(String text);

    @Query("{$and: [{'name':{$regex: ?0, $options: 'i'}}, {'type': {$regex: ?1, $options: 'i'}}]}")
    List<Event> findByNameType(String text, String type);

    //@Query("{'dateEnd' : {$gte : ?0}}")
    List<Event> findByDateEndGreaterThanEqual(Date date);

    @Query("{$and: [{'dateStart':{$lte: ?0}}, {'dateEnd': {$gte: ?0}}]}")
    List<Event> findToday(Date date);

    @Query("{$or:[{'type':{$regex: ?0}},{'type': {$regex: ?1}},{'type': {$regex: ?2}},{'type': {$regex: ?3}},{'type': {$regex: ?4}},{'type': {$regex: ?5}}]}")
    List<Event> findByMultType(String text, String text1, String text2, String text3, String text4, String text5);

    @Query("{$and: [{'dateStart':{$lt: ?1}}, {'dateStart':{$gte: ?0}}, {'dateEnd': {$lte: ?1}}]}")
    List<Event> findWeek(Date minDate, Date maxDate);

    @Query("{$and: [{$and: [{'dateStart':{$lt: ?1}}, {'dateStart':{$gte: ?0}}, {'dateEnd': {$lte: ?1}}]},{$or:[{'type':{$regex: ?2}},{'type': {$regex: ?3}},{'type': {$regex: ?4}},{'type': {$regex: ?5}},{'type': {$regex: ?6}},{'type': {$regex: ?7}}]}]}")
    List<Event> findWeekType(Date minDate, Date maxDate, String text, String text1, String text2, String text3, String text4, String text5);

    @Query("{$and: [{$and: [{'dateStart':{$lte: ?0}}, {'dateEnd': {$gte: ?0}}]},{$or:[{'type':{$regex: ?1}},{'type': {$regex: ?2}},{'type': {$regex: ?3}},{'type': {$regex: ?4}},{'type': {$regex: ?5}},{'type': {$regex: ?6}}]}]}")
    List<Event> findTodayType(Date minDate, String text, String text1, String text2, String text3, String text4, String text5);

}