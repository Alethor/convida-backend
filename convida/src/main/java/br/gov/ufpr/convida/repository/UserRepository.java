package br.gov.ufpr.convida.repository;

import br.gov.ufpr.convida.domain.User;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends MongoRepository<User, String>{

    User findByEmail(String email);

}