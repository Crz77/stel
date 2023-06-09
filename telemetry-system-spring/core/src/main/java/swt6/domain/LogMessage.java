package swt6.domain;

import swt6.domain.enums.LogLevel;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@DiscriminatorValue("LogData")
public class LogMessage extends TelemetryData{

    private static final long serialVersionUID = 1L;

    @Enumerated(EnumType.STRING)
    private LogLevel logLevel;
    private String message;

    public LogMessage(Long senderID, String name, LocalDateTime timestamp, String commonName, boolean isLogMessage,
                      LogLevel logLevel, String message) {
        super(senderID, name, timestamp, commonName, isLogMessage);

        this.logLevel = logLevel;
        this.message = message;
    }
}
