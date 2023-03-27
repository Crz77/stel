package swt6.interfaces;

import java.time.LocalDateTime;

public interface DataStatisticService {
    Double getAvgValueByApplicationId(Long id);
    Double getAvgValueByApplicationIdAndInterval(Long id, LocalDateTime from, LocalDateTime to);
    Double getStdDeviationByApplicationId(Long id);

    Double getAvgValueByApplicationInstanceId(Long id);
    Double getAvgValueByApplicationInstanceIdAndInterval(Long id, LocalDateTime from, LocalDateTime to);
    Double getStdDeviationByApplicationInstanceId(Long id);

    Double getAvgValueByMetricId(Long id);
    Double getAvgValueByMetricIdAndInterval(Long id, LocalDateTime from, LocalDateTime to);
    Double getStdDeviationByMetricId(Long id);
}
