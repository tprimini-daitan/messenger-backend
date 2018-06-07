package com.daitangroup.service.impl;

import com.daitangroup.service.types.MessageRequestData;
import com.daitangroup.entity.Group;
import com.daitangroup.entity.Message;
import com.daitangroup.entity.MessageTransmission;
import com.daitangroup.entity.User;
import com.daitangroup.repo.MessageRepository;
import com.daitangroup.repo.MessageTransmissionRepository;
import com.daitangroup.service.CommunicationService;
import com.daitangroup.service.GroupService;
import com.daitangroup.service.UserService;
import com.daitangroup.service.types.CommunicationServiceStatus;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CommunicationServiceImpl implements CommunicationService {

    private final MessageRepository messageRepository;
    private final MessageTransmissionRepository messageTransmissionRepository;
    private final UserService userService;
    private final GroupService groupService;

    public CommunicationServiceImpl(MessageRepository messageRepository,
                                    MessageTransmissionRepository messageTransmissionRepository,
                                    UserService userService,
                                    GroupService groupService) {
        this.messageRepository = messageRepository;
        this.messageTransmissionRepository = messageTransmissionRepository;
        this.userService = userService;
        this.groupService = groupService;
    }

    public CommunicationServiceStatus sendMessage(MessageRequestData messageRequestData) {
        MessageTransmission messageTransmission = new MessageTransmission();

        if (messageRequestData.getSourceType().equals("user")) {
            User sourceUser = userService.findById(messageRequestData.getSourceId());
            if (sourceUser == null) {
                return CommunicationServiceStatus.SRC_USER_NOT_FOUND;
            }
            messageTransmission.setFromId(sourceUser.getId());
            messageTransmission.setFromType("user");
        } else if (messageRequestData.getSourceType().equals("group")) {
            Group sourceGroup = groupService.findById(messageRequestData.getSourceId());
            if (sourceGroup == null) {
                return CommunicationServiceStatus.SRC_GROUP_NOT_FOUND;
            }
            messageTransmission.setFromId(sourceGroup.getId());
            messageTransmission.setFromType("group");
        }

        if (messageRequestData.getDestinationType().equals("user")) {
            User destinationUser = userService.findById(messageRequestData.getDestinationId());
            if (destinationUser == null) {
                return CommunicationServiceStatus.DST_USER_NOT_FOUND;
            }
            messageTransmission.setToId(destinationUser.getId());
            messageTransmission.setToType("user");
        } else if (messageRequestData.getDestinationType().equals("group")) {
            Group destinationGroup = groupService.findById(messageRequestData.getDestinationId());
            if (destinationGroup == null) {
                return CommunicationServiceStatus.DST_GROUP_NOT_FOUND;
            }
            messageTransmission.setToId(destinationGroup.getId());
            messageTransmission.setToType("group");
        }

        Message message = new Message();
        message.setPayload(messageRequestData.getMessagePayload());
        message.setCreatedAt(new Date());

        Message addedMessage;

        try {
            addedMessage = messageRepository.insert(message);
            messageTransmission.setMessageId(addedMessage.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return CommunicationServiceStatus.ERROR_CREATING_MSG;
        }

        try {
            messageTransmissionRepository.insert(messageTransmission);
        } catch (Exception e) {
            e.printStackTrace();
            return CommunicationServiceStatus.ERROR_SENDING_MSG;
        }

        return CommunicationServiceStatus.OK;
    }
}
