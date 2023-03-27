package swt6.interfaces;

import swt6.domain.LogMessage;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface LogMessageService {
    List<LogMessage> getAllLogMessages();
    List<LogMessage> getAllByApplicationId(Long id);
    List<LogMessage> getAllByApplicationInstanceId(Long id);


    CompletableFuture<Void> createRandomLogMessages(Long instanceId, String commonName, int durationInSec, int intervalInSec);
    void saveLogMessage(LogMessage logMessage);

    void deleteByApplicationId(Long id);
    void deleteByApplicationInstanceId(Long id);
    void deleteByTimestampBefore(LocalDateTime timestamp);
    void deleteByTimestampAfter(LocalDateTime timestamp);
}
