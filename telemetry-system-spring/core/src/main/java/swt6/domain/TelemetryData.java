package swt6.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.FetchMode;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DATA_TYPE")
@DiscriminatorValue("TelData")
public abstract class TelemetryData {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private Long senderID;
    private String name;
    private LocalDateTime timestamp;
    private String commonName;
    private boolean isLogMessage;

    @org.hibernate.annotations.Fetch(FetchMode.JOIN)
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private ApplicationInstance applicationInstance;


    public TelemetryData(Long senderID, String name, LocalDateTime timestamp, String commonName, boolean isLogMessage) {
        this.senderID = senderID;
        this.name = name;
        this.timestamp = timestamp;
        this.commonName = commonName;
        this.isLogMessage = isLogMessage;
    }

}
