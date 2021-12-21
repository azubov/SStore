package ru.lanit.errors;

public enum ErrorType {
    IMAGE_NOT_SAVED("Image saving went wrong. No image found. {}"),
    NAME_ALREADY_EXISTS("Such name already exists");

    private final String description;

    ErrorType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
