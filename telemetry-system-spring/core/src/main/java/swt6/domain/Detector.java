package swt6.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import swt6.domain.helpers.MetricNode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Detector {
    @Id
    @GeneratedValue
    private int id;

    private String name;
    @Column(name = "minValue")
    private Double min;
    @Column(name = "maxValue")
    private Double max;
    @Value("${interval}")
    private Double interval;
    @OneToOne
    @ToString.Exclude
    private Metric metric;
    @OneToMany(mappedBy = "detector", fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Incident> incidents = new ArrayList<>();

    @Transient
    private int indexLastNode;


    public Detector(String name, Double min, Double max, Double interval) {
        this.name = name;
        this.min = min;
        this.max = max;
        this.interval = interval;
    }

    public void addIncident(Incident incident){
        if(incident == null)
            throw new IllegalArgumentException("Incident is null");

        if(incident.getDetector() != null)
            incident.getDetector().getIncidents().remove(incident);

        this.getIncidents().add(incident);
        incident.setDetector(this);
    }

    public void addMetric(Metric metric) {
        if(metric == null)
            throw new IllegalArgumentException("Metric is null");

        if(metric.getDetector() != null)
            metric.getDetector().setMetric(null);

        this.metric = metric;
        this.metric.setDetector(this);
        this.indexLastNode = 0;
    }


}
