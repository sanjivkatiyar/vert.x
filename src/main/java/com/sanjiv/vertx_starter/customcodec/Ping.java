package com.sanjiv.vertx_starter.customcodec;

public class Ping {

  private String message;
  private boolean eanbled;

  public Ping(String message, boolean eanbled) {
    this.message = message;
    this.eanbled = eanbled;
  }

  public String getMessage() {
    return message;
  }

  public boolean isEanbled() {
    return eanbled;
  }

  @Override
  public String toString() {
    return "Ping{" +
      "message='" + message + '\'' +
      ", eanbled=" + eanbled +
      '}';
  }
}
