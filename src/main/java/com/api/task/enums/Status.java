package com.api.task.enums;

public enum Status {
    
    CREATED("CREATED"),
    PROCESSING("PROCESSING"),
    DONE("DONE"),
    ERROR("ERROR");

    private final String description;

    private Status(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}
