package com.sanjiv.vertx_starter.worker;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkerVerticle extends AbstractVerticle {

  private static final Logger LOG = LoggerFactory.getLogger(WorkerExample.class);

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    LOG.debug("Deployed as worker verticle");
    startPromise.complete();
    Thread.sleep(5000);
  }
}
