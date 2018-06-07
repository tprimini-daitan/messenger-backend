package com.daitangroup.repo;

import com.daitangroup.entity.MessageTransmission;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface MessageTransmissionRepository extends MongoRepository<MessageTransmission, String> {

}
