package org.example.searadarapp;

public class Message {
    private int id_message;
    private String input_message;
    private String output_message;
    private String type_of_protocol;
    private String type_of_message;

    public Message(String input_message, int id_message, String output_message, String type_of_protocol, String type_of_message) {
        this.input_message = input_message;
        this.id_message = id_message;
        this.output_message = output_message;
        this.type_of_protocol = type_of_protocol;
        this.type_of_message = type_of_message;
    }

    public int getId_message() {
        return id_message;
    }

    public String getInput_message() {
        return input_message;
    }

    public String getOutput_message() {
        return output_message;
    }

    public String getType_of_protocol() {
        return type_of_protocol;
    }

    public String getType_of_message() {
        return type_of_message;
    }

    public void setId_message(int id_message) {
        this.id_message = id_message;
    }

    public void setInput_message(String input_message) {
        this.input_message = input_message;
    }

    public void setOutput_message(String output_message) {
        this.output_message = output_message;
    }

    public void setType_of_protocol(String type_of_protocol) {
        this.type_of_protocol = type_of_protocol;
    }

    public void setType_of_message(String type_of_message) {
        this.type_of_message = type_of_message;
    }
}
