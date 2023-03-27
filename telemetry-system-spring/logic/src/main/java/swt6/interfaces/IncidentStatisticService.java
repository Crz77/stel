package swt6.interfaces;

import swt6.domain.Application;
import swt6.domain.ApplicationInstance;

import java.time.LocalDateTime;

public interface IncidentStatisticService {
    Application getApplicationWithMostIncidents();
    Application getApplicationWithMostIncidentsInInterval(LocalDateTime from, LocalDateTime to);


    ApplicationInstance getApplicationInstanceWithMostIncidents();
    ApplicationInstance getApplicationInstanceWithMostIncidentsInInterval(LocalDateTime from, LocalDateTime to);

}
