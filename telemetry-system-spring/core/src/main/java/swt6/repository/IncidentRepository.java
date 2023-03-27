package swt6.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import swt6.domain.Incident;
import swt6.domain.helpers.MetricNode;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IncidentRepository extends JpaRepository<Incident, Long> {
    @Query("select i from Incident i where i.detector.metric.applicationInstance.application.id=:id")
    List<Incident> findAllByApplicationId(@Param("id")Long id);
    @Query("select i from Incident i where i.detector.metric.applicationInstance.id=:id")
    List<Incident> findAllByApplicationInstanceId(@Param("id")Long id);


    @Modifying
    @Query("delete from Incident i where i in (select i from Incident where i.detector.metric.applicationInstance.application.id=:id) ")
    void deleteAllByApplicationId(@Param("id")Long id);
    @Modifying
    @Query("delete from Incident i where i in (select i from Incident where i.detector.metric.applicationInstance.application.id=:id and i.node.timeStamp<:timestamp) ")
    void deleteAllByApplicationIdAndTimestampBefore(@Param("id")Long id, @Param("timestamp")LocalDateTime timestamp);
    @Modifying
    @Query("delete from Incident i where i in (select i from Incident where i.detector.metric.applicationInstance.application.id=:id and i.node.timeStamp>:timestamp) ")
    void deleteAllByApplicationIdAndTimestampAfter(@Param("id")Long id, @Param("timestamp")LocalDateTime timestamp);
    @Modifying
    @Query("delete from Incident i where i in (select i from Incident where i.detector.metric.applicationInstance.id=:id) ")
    void deleteAllByApplicationInstanceId(@Param("id")Long id);
    @Modifying
    @Query("delete from Incident i where i in (select i from Incident where i.detector.metric.applicationInstance.id=:id and i.node.timeStamp<:timestamp) ")
    void deleteAllByApplicationInstanceIdAndTimestampBefore(@Param("id")Long id, @Param("timestamp")LocalDateTime timestamp);
    @Modifying
    @Query("delete from Incident i where i in (select i from Incident where i.detector.metric.applicationInstance.id=:id and i.node.timeStamp>:timestamp) ")
    void deleteAllByApplicationInstanceIdAndTimestampAfter(@Param("id")Long id, @Param("timestamp")LocalDateTime timestamp);
}
