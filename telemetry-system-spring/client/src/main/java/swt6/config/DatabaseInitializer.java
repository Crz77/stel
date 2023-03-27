package swt6.config;

import org.springframework.scheduling.annotation.Async;
import swt6.domain.*;
import swt6.domain.helpers.CPUService;
import swt6.domain.helpers.MetricNode;
import swt6.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import swt6.domain.enums.OperatingSystem;
import swt6.domain.enums.Platform;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Order(-1)
@Component
public class DatabaseInitializer implements CommandLineRunner {

    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private ApplicationInstanceService instanceService;
    @Autowired
    private LogMessageService logMessageService;
    @Autowired
    private MetricService metricService;
    @Autowired
    private DetectorService detectorService;
    @Autowired
    private IncidentService incidentService;


    private CPUService cpuService = new CPUService();

    @Async
    @Override
    public void run(String... args) throws Exception {
        Application app1 = new Application(Platform.JAVA, new ArrayList<>(Arrays.asList(OperatingSystem.Windows, OperatingSystem.IOs)));
        Application app2 = new Application(Platform.DOT_NET, new ArrayList<>(Arrays.asList(OperatingSystem.Linux, OperatingSystem.IOs)));
        applicationService.saveApplication(app1);
        applicationService.saveApplication(app2);

        ApplicationInstance instance1 = new ApplicationInstance(UUID.randomUUID(), app1);
        ApplicationInstance instance2 = new ApplicationInstance(UUID.randomUUID(), app1);
        ApplicationInstance instance3 = new ApplicationInstance(UUID.randomUUID(), app2);
        instanceService.saveApplicationInstance(instance1);
        instanceService.saveApplicationInstance(instance2);
        instanceService.saveApplicationInstance(instance3);
        applicationService.saveApplication(app1);
        applicationService.saveApplication(app2);

        logMessageService.createRandomLogMessages(3L, "initData", 20, 5);


        Metric metric1 = new Metric(1L, "CPU-Metric", LocalDateTime.now(), "initData", false, "CPU-Usage");
        Metric metric2 = new Metric(2L, "Memory-Metric", LocalDateTime.now(), "initData", false, "Free-Memory-Space");
        metricService.saveMetric(metric1);
        metricService.saveMetric(metric2);
        instance1.addTelemetryData(metric1);
        instance2.addTelemetryData(metric2);
        instanceService.saveApplicationInstance(instance1);
        instanceService.saveApplicationInstance(instance2);

        metricService.createMetricData(1L, metric1, 30, 5);
        metricService.createMetricData(2L, metric2, 30, 5);

        detectorService.scheduleDetector(2L, "MemDetector", 20, 50, 10);

        Optional<Detector> detector = detectorService.getById(1L);
        Incident incident1 = new Incident(detector.get(), new MetricNode(11.0, LocalDateTime.now().minusDays(10)));
        Incident incident2 = new Incident(detector.get(), new MetricNode(12.0, LocalDateTime.now().minusDays(8)));
        Incident incident3 = new Incident(detector.get(), new MetricNode(13.0, LocalDateTime.now().minusDays(6)));
        Incident incident4 = new Incident(detector.get(), new MetricNode(14.0, LocalDateTime.now().minusDays(4)));
        Incident incident5 = new Incident(detector.get(), new MetricNode(15.0, LocalDateTime.now().minusDays(2)));
        incidentService.saveAndFlush(incident1);
        incidentService.saveAndFlush(incident2);
        incidentService.saveAndFlush(incident3);
        incidentService.saveAndFlush(incident4);
        incidentService.saveAndFlush(incident5);
        detectorService.saveAndFlush(detector.get());
    }
}
