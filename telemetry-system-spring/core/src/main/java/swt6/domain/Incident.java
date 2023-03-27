package swt6.domain;

import swt6.domain.helpers.MetricNode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.FetchMode;


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Incident {
    @Id
    @GeneratedValue
    private Long id;
    @org.hibernate.annotations.Fetch(FetchMode.JOIN)
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Detector detector;

    @ToString.Exclude
    private MetricNode node;

    public Incident(Detector detector, MetricNode node){
        this.detector = detector;
        this.node = node;

        this.detector.getIncidents().add(this);
    }

}
