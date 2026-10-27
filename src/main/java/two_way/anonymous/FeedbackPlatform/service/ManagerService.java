package two_way.anonymous.FeedbackPlatform.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import two_way.anonymous.FeedbackPlatform.domain.FeedbackArea;
import two_way.anonymous.FeedbackPlatform.domain.Manager;
import two_way.anonymous.FeedbackPlatform.repository.FeedbackAreaRepository;
import two_way.anonymous.FeedbackPlatform.repository.FeedbackSessionRepository;
import two_way.anonymous.FeedbackPlatform.repository.ManagerRepository;

@Service
public class ManagerService {
    @Autowired
    private ManagerRepository repository;
    
    @Autowired
    private FeedbackAreaRepository feedbackAreaRepository;

    @Autowired
    private FeedbackSessionRepository feedbackSessionRepository;

    public List<Manager> getAll() {
        return repository.findAll();
    }

    public Optional<Manager> getById(UUID id) {
        return repository.findById(id);
    }

    public String save(Manager manager) {
        repository.save(manager);
        return "Manager saved successfully";
    }

    public String update(UUID id, Manager changes) {
        Optional<Manager> result = repository.findById(id);
        if (result.isEmpty()) {
            return "The manager with that id does not exist.";
        }

        Manager manager = result.get();
        manager.setFirstName(changes.getFirstName());
        manager.setLastName(changes.getLastName());
        manager.setEmail(changes.getEmail());
        manager.setTeam(changes.getTeam());
        repository.save(manager);
        return "Manager updated successfully";
    }

    public String delete(UUID id) {
        Optional<Manager> result = repository.findById(id);
        if (result.isEmpty()) {
            return "The manager with that id does not exist.";
        }

        if (feedbackSessionRepository.existsByManager_Id(id)) {
            return "Cannot delete manager because feedback sessions reference it.";
        }

        repository.delete(result.get());
        return "Manager deleted successfully";
    }


    @Transactional
    public String addFeedbackArea(UUID managerId, UUID feedbackAreaId) {
        Optional<Manager> managerResult = repository.findById(managerId);
        if (managerResult.isEmpty()) {
            return "The manager with that id does not exist.";
        }
    
        Optional<FeedbackArea> areaResult =
                feedbackAreaRepository.findById(feedbackAreaId);
        if (areaResult.isEmpty()) {
            return "That feedback area does not exist.";
        }
    
        Manager manager = managerResult.get();
        FeedbackArea area = areaResult.get();
    
        boolean alreadyAssigned = manager.getFeedbackAreas().stream()
                .anyMatch(existing -> existing.getId().equals(feedbackAreaId));
    
        if (alreadyAssigned) {
            return "The manager already has that feedback area.";
        }
    
        manager.getFeedbackAreas().add(area);
        repository.save(manager);
        return "Feedback area added to the manager.";
    }


    public List<Manager> findByFeedbackAreaName(String name) {
        return repository.findByFeedbackAreaName(name);
    }
}