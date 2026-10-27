package two_way.anonymous.FeedbackPlatform.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import two_way.anonymous.FeedbackPlatform.domain.Employee;
import two_way.anonymous.FeedbackPlatform.repository.EmployeeRepository;
import two_way.anonymous.FeedbackPlatform.repository.FeedbackSessionRepository;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository repository;

    @Autowired
    private FeedbackSessionRepository feedbackSessionRepository;

    public List<Employee> getAll() {
        return repository.findAll();
    }

    public Optional<Employee> getById(UUID id) {
        return repository.findById(id);
    }

    public String save(Employee employee) {
        repository.save(employee);
        return "Employee saved successfully";
    }

    public String update(UUID id, Employee changes) {
        Optional<Employee> result = repository.findById(id);
        if (result.isEmpty()) {
            return "The employee with that id does not exist.";
        }

        Employee employee = result.get();
        employee.setFirstName(changes.getFirstName());
        employee.setLastName(changes.getLastName());
        employee.setStaffId(changes.getStaffId());
        employee.setEmail(changes.getEmail());
        repository.save(employee);
        return "Employee updated successfully";
    }

    public String delete(UUID id) {
        Optional<Employee> result = repository.findById(id);
        if (result.isEmpty()) {
            return "The employee with that id does not exist.";
        }

        if (feedbackSessionRepository.existsByEmployee_Id(id)) {
            return "Cannot delete employee because feedback sessions reference it.";
        }

        repository.delete(result.get());
        return "Employee deleted successfully";
    }
}