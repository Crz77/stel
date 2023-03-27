package swt6.implementation;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swt6.domain.Detector;
import swt6.domain.Incident;
import swt6.domain.Metric;
import swt6.domain.helpers.MetricNode;
import swt6.interfaces.DetectorService;
import swt6.repository.DetectorRepository;
import swt6.repository.IncidentRepository;
import swt6.repository.MetricRepository;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
@Transactional
@Service("detector")
public class DetectorServiceImpl implements DetectorService {
    private DetectorRepository detectorRepository;
    private IncidentRepository incidentRepository;
    private MetricRepository metricRepository;
    @Autowired
    private TaskScheduler taskScheduler;

    @Autowired
    public DetectorServiceImpl(DetectorRepository detectorRepository, IncidentRepository incidentRepository,
                               MetricRepository metricRepository){
        this.detectorRepository = detectorRepository;
        this.incidentRepository = incidentRepository;
        this.metricRepository = metricRepository;
    }


    @Override
    public Optional<Detector> getById(Long id) {
        return detectorRepository.findById(id);
    }

    @Transactional(readOnly=true)
    public List<Detector> getAllDetectors() {
        return detectorRepository.findAll();
    }

    @Transactional(readOnly=true)
    public List<Detector> getAllDetectorsByApplicationId(Long id) {
        return detectorRepository.getDetectorsByApplicationId(id);
    }

    @Transactional(readOnly=true)
    public List<Detector> getAllDetectorsByApplicationInstanceId(Long id) {
        return detectorRepository.getDetectorsByApplicationInstanceId(id);
    }

    @Transactional(readOnly=true)
    public List<Detector> getAllDetectorsByMetricId(Long id) {
        return detectorRepository.getDetectorsByMetricId(id);
    }

    @Transactional
    public void save(Detector detector) {
        detectorRepository.save(detector);
    }

    @Transactional
    public void saveAndFlush(Detector detector) {
        detectorRepository.saveAndFlush(detector);
    }

    @Transactional
    public void startDetector(Detector detector) {
        // comment in for presentation
        //System.out.println("Detector <" + detector.getName() + "> started at: " + LocalDateTime.now());

        List<MetricNode> values = detector.getMetric().getValues();
        int start = detector.getIndexLastNode();

        if(start <= values.size()) {
            for (int i = start; i < values.size(); ++i) {
                if (values.get(i).getValue() < detector.getMin() || values.get(i).getValue() > detector.getMax()) {
                    Incident incident = new Incident(detector, values.get(i));
                    incidentRepository.saveAndFlush(incident);
                    detector.addIncident(incident);
                    detectorRepository.saveAndFlush(detector);
                }
                detector.setIndexLastNode(i + 1);
                detectorRepository.saveAndFlush(detector);
            }
        }
    }

    @Transactional
    public void scheduleDetector(Long metricId, String name, double min, double max, double interval){
        Optional<Metric> metric = metricRepository.findById(metricId);
        if(metric.isEmpty()) {
            System.out.println("There is no Metric for ID " + metricId);
            return;
        }

        Detector detector = new Detector(name, min, max, interval);
        detector.addMetric(metric.get());
        detectorRepository.saveAndFlush(detector);
        metricRepository.saveAndFlush(metric.get());
        taskScheduler.scheduleAtFixedRate(() -> startDetector(detector), Duration.ofSeconds((long)interval));
    }
}
