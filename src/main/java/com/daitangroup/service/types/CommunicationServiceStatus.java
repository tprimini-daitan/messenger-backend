package com.daitangroup.service.types;

public enum CommunicationServiceStatus {
    SRC_USER_NOT_FOUND("SOURCE USER NOT FOUND"),
    SRC_GROUP_NOT_FOUND("SOURCE GROUP NOT FOUND"),
    DST_USER_NOT_FOUND("DESTINATION USER NOT FOUND"),
    DST_GROUP_NOT_FOUND("DESTINATION GROUP NOT FOUND"),
    ERROR_CREATING_MSG("CANNOT CREATE MESSAGE IN DB"),
    ERROR_SENDING_MSG("CANNOT_SEND_MESSAGE"),
    OK("OK");

    private String codeDescription;

    CommunicationServiceStatus(String codeDescription) {
        this.codeDescription = codeDescription;
    }
}
