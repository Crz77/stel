package swt6.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import swt6.domain.Application;
import swt6.domain.ApplicationInstance;

import java.util.List;
import java.util.Optional;


@Repository
public interface ApplicationInstanceRepository extends JpaRepository<ApplicationInstance, Long> {
    @Query("select a.application from ApplicationInstance a where a.id=:id")
    Application getApplicationByInstance(@Param("id")Long id);
    Optional<List<ApplicationInstance>> getApplicationInstanceByApplication_Id(Long id);

}
