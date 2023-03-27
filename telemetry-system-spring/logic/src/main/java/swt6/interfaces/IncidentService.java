package swt6.interfaces;

import swt6.domain.Incident;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IncidentService {
    Optional<Incident> getById(Long id);
    List<Incident> getAllIncidents();
    List<Incident> getAllIncidentsByApplicationId(Long id);
    List<Incident> getAllIncidentsByApplicationInstanceId(Long id);

    void saveIncident(Incident incident);
    void saveAndFlush(Incident incident);

    void deleteById(Long id);
    void deleteAllByApplicationId(Long id);
    void deleteAllByApplicationIdAndTimestampBefore(Long id, LocalDateTime timestamp);
    void deleteAllByApplicationIdAndTimestampAfter(Long id, LocalDateTime timestamp);
    void deleteAllByApplicationInstanceId(Long id);
    void deleteAllByApplicationInstanceIdAndTimestampBefore(Long id, LocalDateTime timestamp);
    void deleteAllByApplicationInstanceIdAndTimestampAfter(Long id, LocalDateTime timestamp);

}
