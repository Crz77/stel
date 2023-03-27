package swt6.interfaces;

import swt6.domain.Application;
import swt6.domain.ApplicationInstance;

import java.util.List;
import java.util.Optional;

public interface ApplicationInstanceService {
    public List<ApplicationInstance> getAllInstances();
    public Optional<ApplicationInstance> getApplicationInstanceById(Long id);
    public Application getApplicationByInstance(Long id);
    public void saveApplicationInstance(ApplicationInstance applicationInstance);


}
