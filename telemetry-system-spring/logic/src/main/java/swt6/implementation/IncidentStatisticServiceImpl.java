package swt6.implementation;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swt6.domain.Application;
import swt6.domain.ApplicationInstance;
import swt6.domain.Incident;
import swt6.interfaces.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Transactional
@Service("incidentStatistic")
public class IncidentStatisticServiceImpl implements IncidentStatisticService {

    private IncidentService incidentService;
    private ApplicationService applicationService;
    private ApplicationInstanceService instanceService;

    @Autowired
    public IncidentStatisticServiceImpl(ApplicationInstanceService instanceService,
                                        IncidentService incidentService,
                                        ApplicationService applicationService){
        this.instanceService = instanceService;
        this.incidentService = incidentService;
        this.applicationService = applicationService;
    }

    @Override
    public Application getApplicationWithMostIncidents() {
        List<Application> apps = applicationService.getAllApplications();
        if(apps.isEmpty())
            return null;
        else {
            Application resApp = apps.get(0);
            for(var a : apps){
                int newCount = incidentService.getAllIncidentsByApplicationId(a.getId()).size();
                int oldCount = incidentService.getAllIncidentsByApplicationId(resApp.getId()).size();
                if(newCount > oldCount)
                    resApp = a;
            }
            return resApp;
        }
    }

    @Override
    public Application getApplicationWithMostIncidentsInInterval(LocalDateTime from, LocalDateTime to) {
        List<Application> apps = applicationService.getAllApplications();
        if(apps.isEmpty())
            return null;
        else {
            int resCount = 0;
            Application resApp = apps.get(0);

            for(var a : apps){
                List<Incident> incidents = incidentService.getAllIncidentsByApplicationId(a.getId());
                if(!incidents.isEmpty()){
                    int tmpCount = 0;
                    for(var i : incidents){
                        if(i.getNode().getTimeStamp().isBefore(to) && i.getNode().getTimeStamp().isAfter(from))
                            tmpCount++;
                    }
                    if(tmpCount > resCount)
                        resApp = a;
                }
            }
            return resApp;
        }
    }

    @Override
    public ApplicationInstance getApplicationInstanceWithMostIncidents() {
        List<ApplicationInstance> instances = instanceService.getAllInstances();
        if(instances.isEmpty())
            return null;
        else {
            ApplicationInstance resApp = instances.get(0);
            for(var i : instances){
                int newCount = incidentService.getAllIncidentsByApplicationInstanceId(i.getId()).size();
                int oldCount = incidentService.getAllIncidentsByApplicationInstanceId(resApp.getId()).size();
                if(newCount > oldCount)
                    resApp = i;
            }
            return resApp;
        }
    }

    @Override
    public ApplicationInstance getApplicationInstanceWithMostIncidentsInInterval(LocalDateTime from, LocalDateTime to) {
        List<ApplicationInstance> instances = instanceService.getAllInstances();
        if(instances.isEmpty())
            return null;
        else {
            int resCount = 0;
            ApplicationInstance resApp = instances.get(0);

            for(var a : instances){
                List<Incident> incidents = incidentService.getAllIncidentsByApplicationInstanceId(a.getId());
                if(!incidents.isEmpty()){
                    int tmpCount = 0;
                    for(var i : incidents){
                        if(i.getNode().getTimeStamp().isBefore(to) && i.getNode().getTimeStamp().isAfter(from))
                            tmpCount++;
                    }
                    if(tmpCount > resCount)
                        resApp = a;
                }
            }
            return resApp;
        }
    }
}
