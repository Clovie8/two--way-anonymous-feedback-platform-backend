package two_way.anonymous.FeedbackPlatform.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import two_way.anonymous.FeedbackPlatform.domain.Team;
import two_way.anonymous.FeedbackPlatform.service.TeamService;

@RestController
@RequestMapping("/api/teams")
public class TeamController {
    private final TeamService service;

    public TeamController(TeamService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Team team) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(team));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Team>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        return service.getById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404)
                        .body("The team with that id does not exist."));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(
            @PathVariable UUID id, @RequestBody Team team) {
        String message = service.update(id, team);
        if (message.startsWith("The team")) {
            return ResponseEntity.status(404).body(message);
        }
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = service.delete(id);
        if (message.startsWith("The team")) {
            return ResponseEntity.status(404).body(message);
        }

        if (message.startsWith("Cannot delete")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }
        return ResponseEntity.ok(message);
    }
}