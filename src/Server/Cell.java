package Server;

import java.util.HashSet;

public class Cell {

    HashSet<Message> messages;

    public Cell() {
        this.messages = new HashSet<Message>();
    }

    public void add(Message m){
        messages.add(m);
    }

    public HashSet<Message> getMessages() {
        return messages;
    }

    public void setMessages(HashSet<Message> messages) {
        this.messages = messages;
    }
}
