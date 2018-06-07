package com.daitangroup.repo;

import com.daitangroup.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface UserRepository extends MongoRepository<User, String> {
    List<User> findByName(String name);
}
