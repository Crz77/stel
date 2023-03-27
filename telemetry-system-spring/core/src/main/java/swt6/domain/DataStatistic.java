package swt6.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class DataStatistic {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Double value;

    public DataStatistic(Long id, String name, Double value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }
}
