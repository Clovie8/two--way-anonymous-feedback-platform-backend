package two_way.anonymous.FeedbackPlatform.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import two_way.anonymous.FeedbackPlatform.domain.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
}
