package swt6.interfaces;

import org.springframework.data.repository.query.Param;
import swt6.domain.Metric;
import swt6.domain.helpers.MetricNode;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface MetricService {
    List<Metric> getAllMetrics();
    List<Metric> getMetricsByApplicationId(Long id);
    List<Metric> getMetricsByApplicationInstanceId(Long id);
    Optional<Metric> getMetricById(Long id);
    Optional<Metric> getMetricByName(String name);
    Optional<List<MetricNode>> getValuesFromMetricById(Long id);

    void saveMetric(Metric metric);
    void addValuesToMetric(Long id, List<MetricNode> values);
    void addValueToMetric(Long id, MetricNode value);

    void deleteByApplicationId(Long id);
    void deleteByApplicationInstanceId(Long id);
    void deleteByTimestampBefore(LocalDateTime timestamp);
    void deleteByTimestampAfter(LocalDateTime timestamp);
    CompletableFuture<Void> createMetricData(Long instanceId, Metric metric, int durationInSec, int intervalInSec);

}
