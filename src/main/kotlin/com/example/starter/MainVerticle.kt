package com.example.starter

import io.vertx.circuitbreaker.HystrixMetricHandler
import io.vertx.core.AbstractVerticle
import io.vertx.core.AsyncResult
import io.vertx.core.Promise
import io.vertx.core.Vertx
import io.vertx.ext.web.Router
import io.vertx.servicediscovery.Record
import io.vertx.servicediscovery.ServiceDiscovery
import io.vertx.servicediscovery.types.HttpEndpoint


fun main(){
  Vertx.vertx().deployVerticle(MainVerticle())
}
class MainVerticle : AbstractVerticle() {

  override fun start(startPromise: Promise<Void>) {

    val server = vertx.createHttpServer()
    val router = Router.router(vertx)

    router.get("/hystrix-metrics").handler(HystrixMetricHandler.create(vertx));
    router.get("/api/some").handler { routingContext ->

      // This handler will be called for every request
      val response = routingContext.response()
      response.putHeader("content-type", "text/plain")

      // Write to the response and end it
      response.end("Hello World from Vert.x-Web!")
    }

    server.requestHandler(router).listen(8081)

    //publishService()

  }

  fun publishService() {
    val discovery = ServiceDiscovery.create(vertx)

    val record = HttpEndpoint.createRecord(
      "some-http-service", // The service name
      "localhost", // The host
      8081, // the port
      "/api" // the root of the service
    );

    discovery.publish(record) { ar: AsyncResult<Record?> ->
      if (ar.succeeded()) { // publication succeeded
        val publishedRecord = ar.result()
        println(publishedRecord)
        println("Hello World!")

      } else { // publication failed
        println("could not publishedRecord")
      }
    }

  }
}
