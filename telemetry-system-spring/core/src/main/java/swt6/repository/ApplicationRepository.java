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
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    @Query("select a.instances from Application a where a.id=:id")
    List<ApplicationInstance> getAllInstancesByApplicationId(@Param("id")Long id);
    @Query("update Application a set a=:application where a.id=:id")
    boolean updateApplication(@Param("application")Application application, @Param("id")Long id);

}
