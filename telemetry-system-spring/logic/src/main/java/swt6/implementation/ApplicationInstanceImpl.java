package swt6.implementation;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swt6.domain.Application;
import swt6.domain.ApplicationInstance;
import swt6.interfaces.ApplicationInstanceService;
import swt6.repository.ApplicationInstanceRepository;

import java.util.List;
import java.util.Optional;

@NoArgsConstructor
@Transactional
@Service("applicationInstance")
public class ApplicationInstanceImpl implements ApplicationInstanceService {
    private ApplicationInstanceRepository instanceRepo;

    @Autowired
    public ApplicationInstanceImpl(ApplicationInstanceRepository instanceRepo){
        this.instanceRepo = instanceRepo;
    }

    @Transactional(readOnly=true)
    public List<ApplicationInstance> getAllInstances() {
        return instanceRepo.findAll();
    }

    @Transactional(readOnly=true)
    public Optional<ApplicationInstance> getApplicationInstanceById(Long id) {
        return instanceRepo.findById(id);
    }

    @Transactional(readOnly=true)
    public Application getApplicationByInstance(Long id) {
        return instanceRepo.getApplicationByInstance(id);
    }

    @Transactional
    public void saveApplicationInstance(ApplicationInstance applicationInstance) {
        instanceRepo.save(applicationInstance);
    }
}
