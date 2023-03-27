package swt6.implementation;

import swt6.domain.TelemetryData;
import swt6.interfaces.ApplicationService;
import org.springframework.transaction.annotation.Transactional;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swt6.domain.Application;
import swt6.domain.ApplicationInstance;
import swt6.repository.ApplicationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
@Transactional
@Service("application")
public class ApplicationServiceImpl implements ApplicationService {
    private ApplicationRepository applicationRepo;

    @Autowired
    public ApplicationServiceImpl(ApplicationRepository applicationRepo){
        this.applicationRepo = applicationRepo;
    }

    @Transactional(readOnly=true)
    public List<Application> getAllApplications() {
        return applicationRepo.findAll();
    }

    @Transactional(readOnly=true)
    public List<ApplicationInstance> getAllInstancesByApplicationId(Long id) {
        return applicationRepo.getAllInstancesByApplicationId(id);
    }

    @Transactional(readOnly=true)
    public Optional<Application> getApplicationById(Long id) {
        return applicationRepo.findById(id);
    }

    @Transactional
    public void saveApplication(Application application) {
        applicationRepo.save(application);
    }


}
