package com.tklk;

import io.prometheus.metrics.core.metrics.Gauge;
import io.prometheus.metrics.exporter.httpserver.HTTPServer;
import io.prometheus.metrics.model.snapshots.Unit;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {

  public static void main(String[] args) throws InterruptedException, IOException {
    Gauge temperature = Gauge.builder()
        .name("home_temperature_in_celsius")
        .help("current temperature at home")
        .labelNames("location")
        .unit(Unit.CELSIUS)
        .register();

    // Not really needing a label for this simple example as we've a fixed location
    var location = "Home";

    // Define the label and set a starting value
    temperature.labelValues(location).set(15.0);

    Runnable temperatureCalculator = () -> {
      var random = new Random();
      var temp = random.nextDouble(-10.0, 30.1);
      temperature.labelValues(location).set(temp);
    };

    // Calculate the temperature periodically
    ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    executor.scheduleAtFixedRate(temperatureCalculator, 0, 5, TimeUnit.SECONDS);

    HTTPServer server = HTTPServer.builder()
        .port(9400)
        .buildAndStart();

    System.out.println(
        "HTTPServer listening on port http://localhost:" + server.getPort() + "/metrics");

    Thread.currentThread().join(); // sleep forever
  }

}
