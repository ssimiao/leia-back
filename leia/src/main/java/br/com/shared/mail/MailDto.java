package br.com.shared.mail;

public class MailDto {

    private MessageMailDto message;

    public MailDto(MessageMailDto message) {
        this.message = message;
    }

    public MessageMailDto getMessage() {
        return message;
    }

    public void setMessage(MessageMailDto message) {
        this.message = message;
    }
}
