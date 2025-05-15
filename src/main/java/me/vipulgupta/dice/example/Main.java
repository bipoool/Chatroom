package me.vipulgupta.dice.example;

import java.io.IOException;
import me.vipulgupta.dice.Exceptions.DiceDbException;

public class Main {

  public static void main(String[] args) throws DiceDbException, InterruptedException, IOException {

    String username = args.length > 0 ? args[0] : "Anonymous";
    String topic = args.length > 1 ? args[1] : "General";
    DiceDbChatBackend chatBackend = new DiceDbChatBackend(username,topic);
    ChatRoom chatRoom = new ChatRoom(username, topic, chatBackend);
    Runtime.getRuntime().addShutdownHook(new Thread(chatRoom::close));

  }

}
