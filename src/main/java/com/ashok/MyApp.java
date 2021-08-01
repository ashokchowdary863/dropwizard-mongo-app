package com.ashok;

import com.ashok.guice.AppGuiceModule;
import com.ashok.health.DefaultCheck;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.dropwizard.DropwizardExports;
import io.prometheus.client.exporter.MetricsServlet;
import ru.vyarus.dropwizard.guice.GuiceBundle;

public class MyApp extends Application<AppConfig> {

    public static void main(final String[] args) throws Exception {
        new MyApp().run(args);
    }

    @Override
    public String getName() {
        return "User Mgmt App";
    }

    @Override
    public void initialize(final Bootstrap<AppConfig> bootstrap) {
        GuiceBundle guiceBundle =  GuiceBundle.builder().
                modules(new AppGuiceModule()).build();
        bootstrap.addBundle(guiceBundle);

        bootstrap.addBundle(new SwaggerBundle<AppConfig>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(AppConfig configuration) {
                return configuration.getSwaggerBundleConfiguration();
            }
        });
    }

    @Override
    public void run(final AppConfig configuration,
                    final Environment environment) {
        registerMetrics(environment);
    }

    private void registerMetrics(Environment environment) {
        CollectorRegistry collectorRegistry = new CollectorRegistry();
        collectorRegistry.register(new DropwizardExports(environment.metrics()));
        environment.admin().addServlet("metrics", new MetricsServlet(collectorRegistry))
                .addMapping("/metrics");
    }

}
