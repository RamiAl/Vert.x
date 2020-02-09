package com.example.starter

import io.vertx.circuitbreaker.CircuitBreaker
import io.vertx.circuitbreaker.HystrixMetricHandler
import io.vertx.core.AsyncResult
import io.vertx.core.Vertx
import io.vertx.ext.web.Router
import io.vertx.servicediscovery.Record
import io.vertx.servicediscovery.ServiceDiscovery
import io.vertx.servicediscovery.ServiceDiscoveryOptions
import io.vertx.servicediscovery.types.HttpEndpoint


fun main() {
  val vertx = Vertx.vertx()

  vertx.deployVerticle(MainVerticle()) {
    println("ttttt")
    vertx.deployVerticle(RestClientVerticle())
  }
}
