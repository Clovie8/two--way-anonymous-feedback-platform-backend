package two_way.anonymous.FeedbackPlatform.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import two_way.anonymous.FeedbackPlatform.domain.Employee;
import two_way.anonymous.FeedbackPlatform.service.EmployeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Employee employee) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(employee));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Employee>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        return service.getById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404)
                        .body("The employee with that id does not exist."));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(
            @PathVariable UUID id, @RequestBody Employee employee) {
        String message = service.update(id, employee);
        if (message.startsWith("The employee")) {
            return ResponseEntity.status(404).body(message);
        }
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = service.delete(id);
        if (message.startsWith("The employee")) {
            return ResponseEntity.status(404).body(message);
        }

        if (message.startsWith("Cannot delete")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }
        return ResponseEntity.ok(message);
    }


    @GetMapping("/by-last-name")
    public ResponseEntity<List<Employee>> findByLastName(
            @RequestParam("name") String name) {
        return ResponseEntity.ok(service.findByLastName(name));
    }


    @GetMapping("/of-manager/{managerId}")
    public ResponseEntity<?> getEmployeesOfManager(
            @PathVariable UUID managerId) {
        return service.getEmployeesOfManager(managerId)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("The manager with that id does not exist."));
    }
}