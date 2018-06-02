package com.daitangroup.repo;

import com.daitangroup.entity.Group;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GroupRepository extends MongoRepository<Group, String> {

}
