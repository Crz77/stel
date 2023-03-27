package swt6.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import swt6.domain.Detector;
import swt6.domain.Incident;

import java.util.List;

@Repository
public interface DetectorRepository extends JpaRepository<Detector, Long> {
    @Query("select d from Detector d where d.metric.applicationInstance.application.id=:id")
    List<Detector> getDetectorsByApplicationId(@Param("id")Long id);
    @Query("select d from Detector d where d.metric.applicationInstance.id=:id")
    List<Detector> getDetectorsByApplicationInstanceId(@Param("id")Long id);
    List<Detector> getDetectorsByMetricId(@Param("id")Long id);
    @Query("select d.incidents from Detector d where d.id=:id")
    List<Incident> getIncidentByDetectorId(@Param("id")Long id);
}
