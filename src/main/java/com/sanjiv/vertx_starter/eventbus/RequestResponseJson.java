package com.sanjiv.vertx_starter.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.jar.JarEntry;

public class RequestResponseJson {

  private static final Logger LOG = LoggerFactory.getLogger(RequestResponseJson.class);

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new JsonRequestVerticle());
    vertx.deployVerticle(new JsonResponseVerticle());
  }
}

class JsonRequestVerticle extends AbstractVerticle{

  private static final Logger LOG = LoggerFactory.getLogger(JsonRequestVerticle.class);
  static String ADDRESS = "my.request.address";

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    startPromise.complete();
    var eventBus = vertx.eventBus();
    JsonObject message = new JsonObject().put("msg", "Hello World!");
    LOG.debug("Sending message: " + message);
    eventBus.<JsonArray>request(ADDRESS, message, reply -> {
      LOG.debug("Response: " + reply.result().body());
    });
  }
}

class JsonResponseVerticle extends AbstractVerticle{

  private static final Logger LOG = LoggerFactory.getLogger(JsonResponseVerticle.class);

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    startPromise.complete();
    var eventBus = vertx.eventBus();
    vertx.eventBus().consumer(JsonRequestVerticle.ADDRESS, message ->{
      LOG.debug("Received message: " + message.body());
      message.reply(new JsonArray().add("Obj1").add("Obj2").add("Obj3"));
    });
  }
}
