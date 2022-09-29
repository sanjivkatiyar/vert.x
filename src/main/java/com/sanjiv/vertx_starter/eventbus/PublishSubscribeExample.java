package com.sanjiv.vertx_starter.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PublishSubscribeExample extends AbstractVerticle {

  private static final Logger LOG = LoggerFactory.getLogger(PublishSubscribeExample.class);

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new Publisher());
    vertx.deployVerticle(new Subscriber1());
    vertx.deployVerticle(new Subscriber2());
  }


}

class Publisher extends AbstractVerticle{

  private static final Logger LOG = LoggerFactory.getLogger(Publisher.class);

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    startPromise.complete();
    LOG.debug("Publishing message to All");
    vertx.eventBus().publish(Publisher.class.getName(), "Hello");
  }
}

class Subscriber1 extends AbstractVerticle{
  private static final Logger LOG = LoggerFactory.getLogger(Subscriber1.class);

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    startPromise.complete();

    vertx.eventBus().<String>consumer(Publisher.class.getName(), message ->{
      LOG.debug("Received Message: " + message.body());
    });
  }
}

class Subscriber2 extends AbstractVerticle{
  private static final Logger LOG = LoggerFactory.getLogger(Subscriber2.class);

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    startPromise.complete();

    vertx.eventBus().<String>consumer(Publisher.class.getName(), message ->{
      LOG.debug("Received Message: " + message.body());
    });
  }
}
