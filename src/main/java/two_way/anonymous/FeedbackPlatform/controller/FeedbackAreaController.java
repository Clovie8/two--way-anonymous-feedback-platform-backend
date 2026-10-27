package two_way.anonymous.FeedbackPlatform.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import two_way.anonymous.FeedbackPlatform.domain.FeedbackArea;
import two_way.anonymous.FeedbackPlatform.service.FeedbackAreaService;

@RestController
@RequestMapping("/api/feedback-areas")
public class FeedbackAreaController {
    private final FeedbackAreaService service;

    public FeedbackAreaController(FeedbackAreaService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody FeedbackArea area) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(area));
    }

    @GetMapping("/all")
    public ResponseEntity<List<FeedbackArea>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        return service.getById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404)
                        .body("The feedback area with that id does not exist."));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(
            @PathVariable UUID id, @RequestBody FeedbackArea area) {
        String message = service.update(id, area);
        if (message.startsWith("The feedback area")) {
            return ResponseEntity.status(404).body(message);
        }
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = service.delete(id);
        if (message.startsWith("The feedback area")) {
            return ResponseEntity.status(404).body(message);
        }

        if (message.startsWith("Cannot delete")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }
        return ResponseEntity.ok(message);
    }


    @GetMapping("/unused")
    public ResponseEntity<List<FeedbackArea>> getUnusedAreas() {
        return ResponseEntity.ok(service.getUnusedAreas());
    }
}