package two_way.anonymous.FeedbackPlatform.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import two_way.anonymous.FeedbackPlatform.domain.FeedbackReply;
import two_way.anonymous.FeedbackPlatform.service.FeedbackReplyService;

@RestController
@RequestMapping("/api/feedback-replies")
public class FeedbackReplyController {
    private final FeedbackReplyService service;

    public FeedbackReplyController(FeedbackReplyService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(
            @RequestParam("feedbackSessionId") UUID feedbackSessionId,
            @RequestBody FeedbackReply reply) {
        String message = service.save(feedbackSessionId, reply);

        if (message.equals("The feedback session with that id does not exist.")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }

        if (message.equals(
                "Feedback replies are only for REVIEWED feedback sessions.")) {
            return ResponseEntity.badRequest().body(message);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/employee/{staffId}")
    public ResponseEntity<List<FeedbackReply>> findHistory(
            @PathVariable String staffId) {
        return ResponseEntity.ok(
                service.findHistoryByEmployeeStaffId(staffId));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = service.delete(id);

        if (message.equals(
                "The feedback reply with that id does not exist.")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }

        return ResponseEntity.ok(message);
    }
}