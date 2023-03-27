package swt6.implementation;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swt6.domain.ApplicationInstance;
import swt6.domain.LogMessage;
import swt6.domain.enums.LogLevel;
import swt6.interfaces.LogMessageService;
import swt6.repository.ApplicationInstanceRepository;
import swt6.repository.LogMessageRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@NoArgsConstructor
@Transactional
@Service("logMessage")
public class LogMessageServiceImpl implements LogMessageService {
    private LogMessageRepository logMessageRepo;
    private ApplicationInstanceRepository instanceRepo;

    @Autowired
    public LogMessageServiceImpl(LogMessageRepository logMessageRepo, ApplicationInstanceRepository instanceRepo){
        this.logMessageRepo = logMessageRepo;
        this.instanceRepo = instanceRepo;
    }

    @Transactional(readOnly=true)
    public List<LogMessage> getAllLogMessages() {
        return  logMessageRepo.findAll();
    }

    @Transactional(readOnly=true)
    public List<LogMessage> getAllByApplicationId(Long id) {
        return logMessageRepo.getLogMessagesByApplicationId(id);
    }

    @Transactional(readOnly=true)
    public List<LogMessage> getAllByApplicationInstanceId(Long id) {
        return logMessageRepo.getLogMessagesByApplicationInstanceId(id);
    }

    @Async
    @Transactional
    public CompletableFuture<Void> createRandomLogMessages(Long instanceId, String commonName, int durationInSec, int intervalInSec) {
        if(intervalInSec > durationInSec) {
            System.out.println("Duration must be bigger than interval!");
            return CompletableFuture.completedFuture(null);
        }

        Optional<ApplicationInstance> instance = instanceRepo.findById(instanceId);
        if(instance.isEmpty()){
            System.out.println("Wrong instance ID");
            return CompletableFuture.completedFuture(null);
        }

        List<String> messages = new ArrayList<>(
                List.of("Take a break lil girl",
                        "Work harder",
                        "Pay your mate a coffee",
                        "Take your phone away faggot!",
                        "No holidays for you"));

        List<LogLevel> logLevels = new ArrayList<>(
                List.of(LogLevel.ERROR, LogLevel.TRACE, LogLevel.WARNING));

        Random random = new Random();

        long endTime = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(durationInSec);
        while (System.currentTimeMillis() < endTime) {
            int randomIndexMessages = random.nextInt(messages.size());
            String message = messages.get(randomIndexMessages);
            int randomIndexLvl = random.nextInt(logLevels.size());
            LogLevel logLevel = logLevels.get(randomIndexLvl);

            LogMessage logMessage = new LogMessage(instanceId, "initMessage", LocalDateTime.now(),
                                                   commonName, true, logLevel, message);
            logMessageRepo.saveAndFlush(logMessage);
            instance.get().addTelemetryData(logMessage);
            instanceRepo.save(instance.get());

            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(intervalInSec));
            } catch (InterruptedException ex) {
                // Do nothing
            }
        }
        return CompletableFuture.completedFuture(null);
    }

    @Transactional
    public void saveLogMessage(LogMessage logMessage) {
        logMessageRepo.save(logMessage);
    }

    @Transactional
    public void deleteByApplicationId(Long id) {
        logMessageRepo.deleteByApplicationId(id);
    }

    @Transactional
    public void deleteByApplicationInstanceId(Long id) {
        logMessageRepo.deleteByApplicationInstanceId(id);
    }

    @Transactional
    public void deleteByTimestampBefore(LocalDateTime timestamp) {
        logMessageRepo.deleteByTimestampBefore(timestamp);
    }

    @Transactional
    public void deleteByTimestampAfter(LocalDateTime timestamp) {
        logMessageRepo.deleteByTimestampAfter(timestamp);
    }
}
