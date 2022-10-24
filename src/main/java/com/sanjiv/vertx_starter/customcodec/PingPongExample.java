package com.sanjiv.vertx_starter.customcodec;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PingPongExample {

  private static final Logger LOG = LoggerFactory.getLogger(PingPongExample.class);

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new PingVerticle(), PingPongExample::logOnError);
    vertx.deployVerticle(new PongVerticle(), PingPongExample::logOnError);
  }

  private static void logOnError(AsyncResult<String> ar) {
    if (ar.failed()) {
      LOG.error("Error: ", ar.cause());
    }
  }
}

class PingVerticle extends AbstractVerticle{

  private static final Logger LOG = LoggerFactory.getLogger(PingVerticle.class);
  static String ADDRESS = PingVerticle.class.getName();

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    var eventBus = vertx.eventBus();
    eventBus.registerDefaultCodec(Ping.class, new LocalMessageCodec(Ping.class));
    final Ping message = new Ping("Hello", true);
    LOG.debug("Sending message: " + message);
    eventBus.<Pong>request(ADDRESS, message, reply -> {
      if(reply.failed()){
        LOG.error("Error", reply.cause());
        return;
      }
      LOG.debug("Response: " + reply.result().body());
    });
    startPromise.complete();
  }
}

class PongVerticle extends AbstractVerticle{

  private static final Logger LOG = LoggerFactory.getLogger(PongVerticle.class);

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    var eventBus = vertx.eventBus();
    eventBus.registerDefaultCodec(Pong.class, new LocalMessageCodec(Pong.class));
    vertx.eventBus().<Ping>consumer(PingVerticle.ADDRESS, message ->{
      LOG.debug("Received message: " + message.body());
      message.reply(new Pong(1));
    }).exceptionHandler(error ->{
      LOG.error("Error: ", error);
    });
    startPromise.complete();
  }
}
