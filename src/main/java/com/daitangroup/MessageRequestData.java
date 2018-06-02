package com.daitangroup;

public class MessageRequestData {
    private String sourceId;
    private String sourceType;
    private String destinationId;
    private String destinationType;
    private String messagePayload;

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public void setDestinationId(String destinationId) {
        this.destinationId = destinationId;
    }

    public void setMessagePayload(String messagePayload) {
        this.messagePayload = messagePayload;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public void setDestinationType(String destinationType) {
        this.destinationType = destinationType;
    }

    public String getSourceId() {
        return sourceId;
    }

    public String getDestinationId() {
        return destinationId;
    }

    public String getMessagePayload() {
        return messagePayload;
    }

    public String getSourceType() {
        return sourceType;
    }

    public String getDestinationType() {
        return destinationType;
    }
}
