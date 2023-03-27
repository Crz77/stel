package swt6.implementation;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swt6.domain.Incident;
import swt6.interfaces.IncidentService;
import swt6.repository.IncidentRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
@Transactional
@Service("incident")
public class IncidentServiceImpl implements IncidentService {
    private IncidentRepository incidentRepository;

    @Autowired
    public IncidentServiceImpl(IncidentRepository incidentRepository){
        this.incidentRepository = incidentRepository;
    }

    @Transactional(readOnly=true)
    public Optional<Incident> getById(Long id) {
        return incidentRepository.findById(id);
    }

    @Transactional(readOnly=true)
    public List<Incident> getAllIncidents() {
        return incidentRepository.findAll();
    }

    @Transactional(readOnly=true)
    public List<Incident> getAllIncidentsByApplicationId(Long id) {
        return incidentRepository.findAllByApplicationId(id);
    }

    @Transactional(readOnly=true)
    public List<Incident> getAllIncidentsByApplicationInstanceId(Long id) {
        return incidentRepository.findAllByApplicationInstanceId(id);
    }

    @Transactional
    public void saveIncident(Incident incident) {
        incidentRepository.save(incident);
    }

    @Transactional
    public void saveAndFlush(Incident incident) {
        incidentRepository.saveAndFlush(incident);
    }

    @Transactional
    public void deleteById(Long id) {
        incidentRepository.deleteById(id);
    }

    @Transactional
    public void deleteAllByApplicationId(Long id) {
        incidentRepository.deleteAllByApplicationId(id);
    }

    @Transactional
    public void deleteAllByApplicationIdAndTimestampBefore(Long id, LocalDateTime timestamp) {
        incidentRepository.deleteAllByApplicationIdAndTimestampBefore(id, timestamp);
    }

    @Transactional
    public void deleteAllByApplicationIdAndTimestampAfter(Long id, LocalDateTime timestamp) {
        incidentRepository.deleteAllByApplicationIdAndTimestampAfter(id, timestamp);
    }

    @Transactional
    public void deleteAllByApplicationInstanceId(Long id) {
        incidentRepository.deleteAllByApplicationInstanceId(id);
    }

    @Transactional
    public void deleteAllByApplicationInstanceIdAndTimestampBefore(Long id, LocalDateTime timestamp) {
        incidentRepository.deleteAllByApplicationInstanceIdAndTimestampBefore(id, timestamp);
    }

    @Override
    public void deleteAllByApplicationInstanceIdAndTimestampAfter(Long id, LocalDateTime timestamp) {
        incidentRepository.deleteAllByApplicationInstanceIdAndTimestampAfter(id, timestamp);
    }

}
