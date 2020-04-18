package br.gov.ufpr.convida.repository;

import br.gov.ufpr.convida.domain.User;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.Query;
import java.lang.String;
import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String>{


}