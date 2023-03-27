package swt6.interfaces;

import swt6.domain.Application;
import swt6.domain.ApplicationInstance;

import java.util.List;
import java.util.Optional;

public interface ApplicationService {
    List<Application> getAllApplications();
    List<ApplicationInstance> getAllInstancesByApplicationId(Long id);
    Optional<Application> getApplicationById(Long id);
    void saveApplication(Application application);
}
