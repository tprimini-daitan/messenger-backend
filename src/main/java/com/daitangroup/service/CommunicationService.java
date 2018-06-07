package com.daitangroup.service;

import com.daitangroup.service.types.MessageRequestData;
import com.daitangroup.service.types.CommunicationServiceStatus;

public interface CommunicationService {
    CommunicationServiceStatus sendMessage(MessageRequestData messageRequestData);
}
