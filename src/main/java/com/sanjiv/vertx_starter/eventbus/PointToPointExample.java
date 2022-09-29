package com.sanjiv.vertx_starter.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PointToPointExample extends AbstractVerticle{
  private static final Logger LOG = LoggerFactory.getLogger(PointToPointExample.class);

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new Sender());
    vertx.deployVerticle(new Receiver());
  }
}

class Sender extends AbstractVerticle {
  private static final Logger LOG = LoggerFactory.getLogger(Sender.class);

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    startPromise.complete();
    vertx.setPeriodic(1000, id->{
      vertx.eventBus().send(Sender.class.getName(), "Sending a message...");
    });
  }
}

class Receiver extends AbstractVerticle {
  private static final Logger LOG = LoggerFactory.getLogger(Receiver.class);

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    startPromise.complete();
    vertx.eventBus().<String>consumer(Sender.class.getName(), message ->{
      LOG.debug("Received message: " + message.body());
    });
  }
}
