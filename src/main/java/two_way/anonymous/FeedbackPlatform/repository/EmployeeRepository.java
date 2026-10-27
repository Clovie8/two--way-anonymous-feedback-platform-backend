package two_way.anonymous.FeedbackPlatform.repository;

import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import two_way.anonymous.FeedbackPlatform.domain.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
    List<Employee> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);

    @Query("""
        SELECT DISTINCT employee FROM FeedbackSession session
        JOIN session.employee employee
        WHERE session.manager.id = :managerId
        """)
    List<Employee> findEmployeesOfManager(@Param("managerId") UUID managerId);
}
