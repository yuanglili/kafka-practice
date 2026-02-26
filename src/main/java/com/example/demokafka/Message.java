package com.example.demokafka;


public class Message {
    private int id;
    private String text;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    // Builder pattern
    public static final class Builder {
        private int id;
        private String text;

        private Builder() {}

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public Message build() {
            Message message = new Message();
            message.setId(id);
            message.setText(text);
            return message;
        }
    }

    public static Builder builder() {
        return Builder.newBuilder();
    }

    @Override
    public String toString() {
        return "Message{id=" + id + ", text='" + text + "'}";
    }
}