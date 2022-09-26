package com.sanjiv.vertx_starter.verticles.worker;

import com.sanjiv.vertx_starter.verticles.MainVerticle;
import com.sanjiv.vertx_starter.verticles.VerticleA;
import com.sanjiv.vertx_starter.verticles.VerticleB;
import com.sanjiv.vertx_starter.verticles.VerticleN;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class WorkerExample extends AbstractVerticle {

  private static final Logger LOG = LoggerFactory.getLogger(WorkerExample.class);

  public static void main(String[] args) {
    final var vertx = Vertx.vertx();
    vertx.deployVerticle(new WorkerExample());
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    LOG.debug("Start {}", getClass().getName());
    startPromise.complete();
    vertx.executeBlocking(event -> {
      try{
        LOG.debug("Executing blocking code");
        Thread.sleep(500);
        event.complete();
      }catch(InterruptedException ex){
        LOG.error("Failed : "+ ex.getMessage());
        event.fail(ex);

      }
    }, result->{
      if(result.succeeded()){
        LOG.debug("Blocking call done.");
      } else{
        LOG.debug("Blocking call failed" + result.cause());
      }
    });
  }
}
