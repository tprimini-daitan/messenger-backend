package com.daitangroup.repo;

import com.daitangroup.entity.MessageTransmission;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageTransmissionRepository extends MongoRepository<MessageTransmission, String> {

}
