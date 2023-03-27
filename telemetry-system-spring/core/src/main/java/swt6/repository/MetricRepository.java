package swt6.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import swt6.domain.Metric;
import swt6.domain.helpers.MetricNode;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MetricRepository extends JpaRepository<Metric, Long> {
    List<Metric> getMetricsByApplicationInstance_Id(Long id);
    Optional<Metric> getMetricByName(String name);
    @Query("select m.values from Metric m where m.id=:id")
    List<MetricNode> getValuesFromMetricById(@Param("id")Long id);

    @Modifying
    @Query("delete from Metric m where m in (select m from Metric where m.applicationInstance.application.id=:id) ")
    void deleteByApplicationId(@Param("id")Long id);
    @Modifying
    @Query("delete from Metric m where m in (select m from Metric where m.applicationInstance.id=:id) ")
    void deleteByApplicationInstanceId(@Param("id")Long id);
    @Modifying
    @Query("delete from Metric m where m in (select m from Metric where m.timestamp<:timestamp) ")
    void deleteByTimestampBefore(@Param("timestamp") LocalDateTime timestamp);
    @Modifying
    @Query("delete from Metric m where m in (select m from Metric where m.timestamp>:timestamp) ")
    void deleteByTimestampAfter(@Param("timestamp") LocalDateTime timestamp);
}

