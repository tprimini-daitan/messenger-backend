package com.daitangroup;

public class MessageRequestData {
    private String sourceId;
    private String destinationId;
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

    public String getSourceId() {
        return sourceId;
    }

    public String getDestinationId() {
        return destinationId;
    }

    public String getMessagePayload() {
        return messagePayload;
    }
}
