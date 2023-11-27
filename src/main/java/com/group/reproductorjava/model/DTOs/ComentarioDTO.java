package com.group.reproductorjava.model.DTOs;

public class ComentarioDTO {
    private String name;
    private String message;

    public ComentarioDTO() {}
    public ComentarioDTO(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    @Override
    public String toString() {
        return "ComentarioDTO{" +
                "name='" + name + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
