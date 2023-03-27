package swt6.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import swt6.domain.LogMessage;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LogMessageRepository extends JpaRepository<LogMessage, Long> {
    @Query("select lm from LogMessage lm where lm.applicationInstance.application.id=:id")
    List<LogMessage> getLogMessagesByApplicationId(@Param("id")Long id);
    @Query("select lm from LogMessage lm where lm.applicationInstance.id=:id")
    List<LogMessage> getLogMessagesByApplicationInstanceId(@Param("id")Long id);

    @Modifying
    @Query("delete from LogMessage l where l in (select l from LogMessage where l.applicationInstance.application.id=:id) ")
    void deleteByApplicationId(@Param("id")Long id);
    @Modifying
    @Query("delete from LogMessage l where l in (select l from LogMessage where l.applicationInstance.id=:id) ")
    void deleteByApplicationInstanceId(@Param("id")Long id);
    @Modifying
    @Query("delete from LogMessage l where l in (select l from LogMessage where l.timestamp<:timestamp) ")
    void deleteByTimestampBefore(@Param("timestamp")LocalDateTime timestamp);
    @Modifying
    @Query("delete from LogMessage l where l in (select l from LogMessage where l.timestamp>:timestamp) ")
    void deleteByTimestampAfter(@Param("timestamp")LocalDateTime timestamp);
}
