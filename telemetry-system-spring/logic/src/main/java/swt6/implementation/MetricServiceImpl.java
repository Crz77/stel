package swt6.implementation;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swt6.domain.ApplicationInstance;
import swt6.domain.Metric;
import swt6.domain.helpers.CPUService;
import swt6.domain.helpers.MetricNode;
import swt6.interfaces.MetricService;
import swt6.repository.ApplicationInstanceRepository;
import swt6.repository.MetricRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@NoArgsConstructor
@Transactional
@Service("metric")
public class MetricServiceImpl implements MetricService {
    private MetricRepository metricRepository;
    private ApplicationInstanceRepository instanceRepo;
    private CPUService cpuService = new CPUService();
    @Autowired
    public MetricServiceImpl(MetricRepository metricRepository, ApplicationInstanceRepository instanceRepo){
        this.metricRepository = metricRepository;
        this.instanceRepo = instanceRepo;
    }

    @Transactional(readOnly=true)
    public List<Metric> getAllMetrics() {
        return metricRepository.findAll();
    }

    @Transactional(readOnly=true)
    public List<Metric> getMetricsByApplicationId(Long id) {
        List<Metric> metrics = metricRepository.findAll();
        if(metrics.isEmpty())
            return null;
        else {
            List<Metric> appMetrics = new ArrayList<>();
            for(var m : metrics){
                if(m.getApplicationInstance().getApplication().getId() == id)
                    appMetrics.add(m);
            }
            return appMetrics;
        }
    }

    @Transactional(readOnly=true)
    public List<Metric> getMetricsByApplicationInstanceId(Long id) {
        return metricRepository.getMetricsByApplicationInstance_Id(id);
    }

    @Transactional(readOnly=true)
    public Optional<Metric> getMetricById(Long id) {
        return metricRepository.findById(id);
    }

    @Transactional(readOnly=true)
    public Optional<Metric> getMetricByName(String name) {
        return metricRepository.getMetricByName(name);
    }

    @Transactional(readOnly=true)
    public Optional<List<MetricNode>> getValuesFromMetricById(Long id) {
        return Optional.ofNullable(metricRepository.getValuesFromMetricById(id));
    }

    @Transactional
    public void saveMetric(Metric metric) {
        metricRepository.save(metric);
    }

    @Transactional
    public void addValuesToMetric(Long id, List<MetricNode> values) {
        Optional<Metric> metric = metricRepository.findById(id);
        if(metric.isPresent()){
            for(var v : values){
                metric.get().addMetricNode(v);
            }
        }
        Metric m2 = metric.get();
        metricRepository.save(m2);
    }

    @Transactional
    public void addValueToMetric(Long id, MetricNode value) {
        Optional<Metric> metric = metricRepository.findById(id);
        metric.ifPresent(m -> m.addMetricNode(value));

        metricRepository.save(metric.get());
    }

    @Transactional
    public void deleteByApplicationId(Long id) {
        metricRepository.deleteByApplicationId(id);
    }

    @Transactional
    public void deleteByApplicationInstanceId(Long id) {
        metricRepository.deleteByApplicationInstanceId(id);
    }

    @Transactional
    public void deleteByTimestampBefore(LocalDateTime timestamp) {
        metricRepository.deleteByTimestampBefore(timestamp);
    }

    @Transactional
    public void deleteByTimestampAfter(LocalDateTime timestamp) {
        metricRepository.deleteByTimestampAfter(timestamp);
    }

    @Transactional
    public int deleteMetricNodesOlderThan(Long metricId, LocalDateTime timestamp) {
        Optional<Metric> metric = metricRepository.findById(metricId);
        int count = 0;
        if(metric.isPresent()){
            List<MetricNode> values = metric.get().getValues();
            for(var v : values){
                if(v.getTimeStamp().isBefore(timestamp)){
                    values.remove(v);
                    count++;
                }
            }
        }
        if(count != 0) {
            metricRepository.save(metric.get());
        }
        return count;
    }

    @Async
    @Transactional
    public CompletableFuture<Void> createMetricData(Long instanceId, Metric metric, int durationInSec, int intervalInSec) {
        if(intervalInSec > durationInSec) {
            System.out.println("Duration must be bigger than interval!");
            return CompletableFuture.completedFuture(null);
        }

        Optional<ApplicationInstance> instance = instanceRepo.findById(instanceId);
        if(instance.isEmpty()){
            System.out.println("Wrong instance ID");
            return CompletableFuture.completedFuture(null);
        }

        if(metric == null){
            System.out.println("Metric is null");
            return CompletableFuture.completedFuture(null);
        }

        long endTime = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(durationInSec);
        while (System.currentTimeMillis() < endTime) {
            if(Objects.equals(metric.getName(), "CPU-Metric")){
                metric.addMetricNode(new MetricNode(cpuService.getCpuUsage(), LocalDateTime.now()));
            } else if(Objects.equals(metric.getName(), "Memory-Metric")){
                metric.addMetricNode(new MetricNode(cpuService.getFreeMemorySpace(), LocalDateTime.now()));
            }

            metricRepository.saveAndFlush(metric);
            instanceRepo.saveAndFlush(instance.get());

            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(intervalInSec));
            } catch (InterruptedException ex) {
                // Do nothing
            }
        }
        return CompletableFuture.completedFuture(null);
    }
}
