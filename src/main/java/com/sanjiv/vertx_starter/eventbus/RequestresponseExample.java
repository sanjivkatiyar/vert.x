package com.sanjiv.vertx_starter.eventbus;

import com.sanjiv.vertx_starter.worker.WorkerExample;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.DeliveryOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestresponseExample {

  private static final Logger LOG = LoggerFactory.getLogger(RequestresponseExample.class);

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new RequestVerticle());
    vertx.deployVerticle(new ResponseVerticle());
  }
}

class RequestVerticle extends AbstractVerticle{

  private static final Logger LOG = LoggerFactory.getLogger(RequestVerticle.class);
  static String ADDRESS = "my.request.address";

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    startPromise.complete();
    var eventBus = vertx.eventBus();
    String message = "Hello World!";
    LOG.debug("Sending message: " + message);
    eventBus.<String>request(ADDRESS, message, reply -> {
      LOG.debug("Response: " + reply.result().body());
    });
  }
}

class ResponseVerticle extends AbstractVerticle{

  private static final Logger LOG = LoggerFactory.getLogger(ResponseVerticle.class);

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    startPromise.complete();
    var eventBus = vertx.eventBus();
    vertx.eventBus().consumer(RequestVerticle.ADDRESS, message ->{
      LOG.debug("Received message: " + message.body());
      message.reply("Received your message, Thanks! " + new DeliveryOptions().setSendTimeout(1000));
    });
  }
}
