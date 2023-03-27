package swt6.interfaces;

import swt6.domain.Detector;
import swt6.repository.DetectorRepository;

import java.util.List;
import java.util.Optional;

public interface DetectorService {
    Optional<Detector> getById(Long id);
    List<Detector> getAllDetectors();
    List<Detector> getAllDetectorsByApplicationId(Long id);
    List<Detector> getAllDetectorsByApplicationInstanceId(Long id);
    List<Detector> getAllDetectorsByMetricId(Long id);

    void save(Detector detector);
    void saveAndFlush(Detector detector);

  //  void startDetector(Detector detector);
    void scheduleDetector(Long metricId, String name, double min, double max, double interval);

}
