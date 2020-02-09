package com.example.starter

import io.vertx.core.AbstractVerticle
import io.vertx.core.AsyncResult
import io.vertx.core.Vertx
import io.vertx.servicediscovery.Record
import io.vertx.servicediscovery.ServiceDiscovery

fun main(){
  Vertx.vertx().deployVerticle(RestClientVerticle())
}

class RestClientVerticle: AbstractVerticle() {

  override fun start() {
    println("hhhhh")
    val discovery = ServiceDiscovery.create(vertx)

    discovery.getRecord({ it.name == "some-http-service" }) { ar: AsyncResult<Record?> ->
      if (ar.succeeded()) {
        if (ar.result() != null) { // we have a record
          println("we have a record")

        } else { // the lookup succeeded, but no matching service
          println("the lookup succeeded, but no matching service")

        }
      } else { // lookup failed
      }
    }
    discovery.close()

  }
}
