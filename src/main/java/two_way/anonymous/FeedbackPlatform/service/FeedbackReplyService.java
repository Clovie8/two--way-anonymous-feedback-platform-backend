package two_way.anonymous.FeedbackPlatform.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import two_way.anonymous.FeedbackPlatform.domain.FeedbackReply;
import two_way.anonymous.FeedbackPlatform.domain.FeedbackSession;
import two_way.anonymous.FeedbackPlatform.domain.SessionStatus;
import two_way.anonymous.FeedbackPlatform.repository.FeedbackReplyRepository;
import two_way.anonymous.FeedbackPlatform.repository.FeedbackSessionRepository;

@Service
public class FeedbackReplyService {
    @Autowired
    private FeedbackReplyRepository replyRepository;

    @Autowired
    private FeedbackSessionRepository sessionRepository;

    public String save(UUID feedbackSessionId, FeedbackReply reply) {
        Optional<FeedbackSession> result =
                sessionRepository.findById(feedbackSessionId);

        if (result.isEmpty()) {
            return "The feedback session with that id does not exist.";
        }

        FeedbackSession session = result.get();

        if (session.getStatus() != SessionStatus.REVIEWED) {
            return "Feedback replies are only for REVIEWED feedback sessions.";
        }

        reply.setFeedbackSession(session);
        reply.setRepliedDate(LocalDate.now());
        replyRepository.save(reply);

        return "Feedback reply saved successfully.";
    }

    public List<FeedbackReply> findHistoryByEmployeeStaffId(String staffId) {
        return replyRepository.findHistoryByEmployeeStaffId(staffId);
    }

    public String delete(UUID id) {
        Optional<FeedbackReply> result = replyRepository.findById(id);

        if (result.isEmpty()) {
            return "The feedback reply with that id does not exist.";
        }

        replyRepository.delete(result.get());
        return "Feedback reply deleted successfully.";
    }
}