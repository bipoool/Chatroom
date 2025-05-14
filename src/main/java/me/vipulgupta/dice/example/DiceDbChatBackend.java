package me.vipulgupta.dice.example;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import me.vipulgupta.dice.DiceDbManager;
import me.vipulgupta.dice.Exceptions.DiceDbException;
import me.vipulgupta.dice.Reponse.Response;

class DiceDbChatBackend {

  BlockingQueue<Response> messageQueue;
  DiceDbManager diceDbManager;
  String username;
  String topic;

  DiceDbChatBackend(String username, String topic) throws DiceDbException, InterruptedException {
    this.diceDbManager = new DiceDbManager("localhost", 7379, 2, 2);
    this.username = username;
    this.topic = topic;
  }

  public BlockingQueue<Response> register() throws DiceDbException, InterruptedException {
    String key = "last_message_" + topic;
    messageQueue = diceDbManager.watch("GET.WATCH", List.of(key));
    messageQueue.take(); // consume the first message
    return this.messageQueue;
  }

  public void broadcast(String message, String topic) throws DiceDbException {
    String key = "last_message_" + topic;
    this.diceDbManager.fire("SET", List.of(key, message));
  }

  public void close() {
    diceDbManager.close();
  }
}
