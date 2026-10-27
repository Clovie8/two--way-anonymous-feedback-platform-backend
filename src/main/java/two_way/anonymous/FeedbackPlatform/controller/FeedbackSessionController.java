package two_way.anonymous.FeedbackPlatform.controller;

import java.util.List;
import java.util.UUID;
import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import two_way.anonymous.FeedbackPlatform.domain.FeedbackSession;
import two_way.anonymous.FeedbackPlatform.service.FeedbackSessionService;
import two_way.anonymous.FeedbackPlatform.domain.SessionStatus;

@RestController
@RequestMapping("/api/feedback-sessions")
public class FeedbackSessionController {
    private final FeedbackSessionService service;

    public FeedbackSessionController(FeedbackSessionService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody FeedbackSession session) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(session));
    }

    @GetMapping("/all")
    public ResponseEntity<List<FeedbackSession>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        return service.getById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404)
                        .body("The feedback session with that id does not exist."));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(
            @PathVariable UUID id, @RequestBody FeedbackSession session) {
        String message = service.update(id, session);
        if (message.startsWith("The feedback session")) {
            return ResponseEntity.status(404).body(message);
        }
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = service.delete(id);
        if (message.startsWith("The feedback session")) {
            return ResponseEntity.status(404).body(message);
        }

        if (message.equals("That feedback session has feedback replies, delete them first.")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }
        return ResponseEntity.ok(message);
    }


    @GetMapping("/by-status")
    public ResponseEntity<List<FeedbackSession>> findByStatus(
            @RequestParam("status") SessionStatus status) {
        return ResponseEntity.ok(service.findByStatus(status));
    }

    @GetMapping("/between")
    public ResponseEntity<List<FeedbackSession>> findBetweenDates(
            @RequestParam("start") String start,
            @RequestParam("end") String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
    
        return ResponseEntity.ok(service.findBetweenDates(startDate, endDate));
    }
}
