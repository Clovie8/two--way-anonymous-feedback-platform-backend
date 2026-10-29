package two_way.anonymous.FeedbackPlatform.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import two_way.anonymous.FeedbackPlatform.domain.SessionStatus;
import two_way.anonymous.FeedbackPlatform.repository.EmployeeRepository;
import two_way.anonymous.FeedbackPlatform.repository.FeedbackReplyRepository;
import two_way.anonymous.FeedbackPlatform.repository.FeedbackSessionRepository;
import two_way.anonymous.FeedbackPlatform.repository.ManagerRepository;

@Service
public class DashboardService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private TeamService teamService;

    @Autowired
    private FeedbackSessionRepository feedbackSessionRepository;

    @Autowired
    private FeedbackReplyRepository feedbackReplyRepository;

    public Map<String, Long> getSummary() {
        Map<String, Long> summary = new LinkedHashMap<>();

        summary.put("employees", employeeRepository.count());
        summary.put("managers", managerRepository.count());
        summary.put("freeTeams", (long) teamService.getFreeTeams().size());
        summary.put("opened",
                feedbackSessionRepository.countByStatus(SessionStatus.OPENED));
        summary.put("replies", feedbackReplyRepository.count());

        return summary;
    }
}