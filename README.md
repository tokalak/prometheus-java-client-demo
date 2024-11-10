# Prometheus Client Demo

A java client which collects metrics and provides it to Prometheus. 

Start the demo, then open the URL `http://localhost:9400/metrics` in your browser of choice. 
You should see the temperature metrics.

## Prometheus Server Integration

To allow the Prometheus server to scrape the metrics, download the [latest Prometheus server release](https://github.com/prometheus/prometheus/releases), and configure the `prometheus.yml` file as follows:

```yml
scrape_configs:
  - job_name: "prometheus-java-client-demo"
    static_configs:

      - targets: ["localhost:9400"]
```
Run Prometheus. If you open the Prometheus Web-UI by pointing your browser to `http://localhost:9090`, then your client should be listed there.

You can query your data, display the data as a chart etc.
