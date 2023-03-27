package swt6.implementation;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swt6.domain.Metric;
import swt6.interfaces.DataStatisticService;
import swt6.interfaces.MetricService;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
@Transactional
@Service("dataStatistic")
public class DataStatisticServiceImpl implements DataStatisticService {
    private MetricService metricService;

    @Autowired
    public DataStatisticServiceImpl(MetricService metricService){
        this.metricService = metricService;
    }


    @Transactional(readOnly=true)
    public Double getAvgValueByApplicationId(Long id) {
        List<Metric> metrics = metricService.getMetricsByApplicationId(id);
        if(metrics.isEmpty())
            return -1.0;
        else {
            int counter = 0;
            Double sum = 0.0;

            for(var m : metrics){
                for(var v : m.getValues()){
                    sum += v.getValue();
                    ++counter;
                }
            }

            return sum / counter;
        }
    }

    @Transactional(readOnly=true)
    public Double getAvgValueByApplicationIdAndInterval(Long id, LocalDateTime from, LocalDateTime to) {
        List<Metric> metrics = metricService.getMetricsByApplicationId(id);

        if(metrics.isEmpty())
            return -1.0;
        else {
            int counter = 0;
            double sum = 0.0;

            for(var m : metrics){
                for(var v : m.getValues()){
                    if(v.getTimeStamp().isBefore(to) && v.getTimeStamp().isAfter(from)) {
                        sum += v.getValue();
                        ++counter;
                    }
                }
            }
            return sum / counter;
        }
    }

    @Transactional(readOnly=true)
    public Double getStdDeviationByApplicationId(Long id) {
        double sum = 0.0;
        double avg = getAvgValueByApplicationId(id);
        int length = 0;
        List<Metric> metrics = metricService.getMetricsByApplicationId(id);

        for (var m : metrics) {
            length += m.getValues().size();
            for(var v : m.getValues()){
                sum += Math.pow(v.getValue() - avg, 2);
            }
        }

        double variance = sum / (length - 1);

        return Math.sqrt(variance);
    }

    @Transactional(readOnly=true)
    public Double getAvgValueByApplicationInstanceId(Long id) {
        List<Metric> metrics = metricService.getMetricsByApplicationInstanceId(id);
        if(metrics.isEmpty())
            return -1.0;
        else {
            int counter = 0;
            Double sum = 0.0;

            for(var m : metrics){
                for(var v : m.getValues()){
                    sum += v.getValue();
                    ++counter;
                }
            }

            return sum / counter;
        }
    }

    @Transactional(readOnly=true)
    public Double getAvgValueByApplicationInstanceIdAndInterval(Long id, LocalDateTime from, LocalDateTime to) {
        List<Metric> metrics = metricService.getMetricsByApplicationInstanceId(id);

        if(metrics.isEmpty())
            return -1.0;
        else {
            int counter = 0;
            double sum = 0.0;

            for(var m : metrics){
                for(var v : m.getValues()){
                    if(v.getTimeStamp().isBefore(to) && v.getTimeStamp().isAfter(from)) {
                        sum += v.getValue();
                        ++counter;
                    }
                }
            }
            return sum / counter;
        }
    }

    @Transactional(readOnly=true)
    public Double getStdDeviationByApplicationInstanceId(Long id) {
        double sum = 0.0;
        double avg = getAvgValueByApplicationId(id);
        int length = 0;
        List<Metric> metrics = metricService.getMetricsByApplicationInstanceId(id);

        for (var m : metrics) {
            length += m.getValues().size();
            for(var v : m.getValues()){
                sum += Math.pow(v.getValue() - avg, 2);
            }
        }

        double variance = sum / (length - 1);

        return Math.sqrt(variance);
    }

    @Transactional(readOnly=true)
    public Double getAvgValueByMetricId(Long id) {
        Optional<Metric> metric = metricService.getMetricById(id);

        if(metric.isEmpty())
            return -1.0;
        else {
            int counter = 0;
            Double sum = 0.0;
            for(var v : metric.get().getValues()){
                sum += v.getValue();
                ++counter;
            }
        return sum / counter;
        }
    }

    @Transactional(readOnly=true)
    public Double getAvgValueByMetricIdAndInterval(Long id, LocalDateTime from, LocalDateTime to) {
        Optional<Metric> metric = metricService.getMetricById(id);

        if(metric.isEmpty())
            return -1.0;
        else {
            int counter = 0;
            double sum = 0.0;

            for(var v : metric.get().getValues()){
                if(v.getTimeStamp().isBefore(to) && v.getTimeStamp().isAfter(from)) {
                    sum += v.getValue();
                    ++counter;
                }
            }
            return sum / counter;
        }
    }

    @Transactional(readOnly=true)
    public Double getStdDeviationByMetricId(Long id) {
        double sum = 0.0;
        double avg = getAvgValueByApplicationId(id);
        int length = 0;
        Optional<Metric> metric = metricService.getMetricById(id);

        if(metric.isEmpty())
            return -1.0;
        else {
            length += metric.get().getValues().size();
            for (var v : metric.get().getValues()) {
                sum += Math.pow(v.getValue() - avg, 2);
            }

            double variance = sum / (length - 1);

            return Math.sqrt(variance);
        }
    }
}
