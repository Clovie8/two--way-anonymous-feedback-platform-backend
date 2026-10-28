package two_way.anonymous.FeedbackPlatform.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import two_way.anonymous.FeedbackPlatform.domain.Manager;
import two_way.anonymous.FeedbackPlatform.service.ManagerService;

@RestController
@RequestMapping("/api/managers")
public class ManagerController {
    private final ManagerService service;

    public ManagerController(ManagerService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Manager manager) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(manager));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Manager>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        return service.getById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404)
                        .body("The manager with that id does not exist."));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(
            @PathVariable UUID id, @RequestBody Manager manager) {
        String message = service.update(id, manager);
        if (message.startsWith("The manager")) {
            return ResponseEntity.status(404).body(message);
        }
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = service.delete(id);
        if (message.startsWith("The manager")) {
            return ResponseEntity.status(404).body(message);
        }

        if (message.startsWith("Cannot delete")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }
        return ResponseEntity.ok(message);
    }

    @PutMapping("/{managerId}/feedback-areas/{feedbackAreaId}")
    public ResponseEntity<String> addFeedbackArea(
            @PathVariable UUID managerId,
            @PathVariable UUID feedbackAreaId) {
        String message = service.addFeedbackArea(managerId, feedbackAreaId);
    
        if (message.equals("The manager with that id does not exist.") ||
                message.equals("That feedback area does not exist.")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    
        if (message.equals("The manager already has that feedback area.")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }
    
        return ResponseEntity.ok(message);
    }


    @GetMapping("/by-feedback-area")
    public ResponseEntity<List<Manager>> findByFeedbackAreaName(
            @RequestParam("name") String name) {
        return ResponseEntity.ok(service.findByFeedbackAreaName(name));
    }

    @PatchMapping("/{managerId}/team")
    public ResponseEntity<String> moveToTeam(
            @PathVariable UUID managerId,
            @RequestParam("teamNumber") int teamNumber) {
        String message = service.moveToTeam(managerId, teamNumber);
    
        if (message.equals("The manager with that id does not exist.") ||
                message.equals("The team with that teamNumber does not exist.")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    
        if (message.equals("The team with that teamNumber is taken by another manager.")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }
    
        return ResponseEntity.ok(message);
    }
}