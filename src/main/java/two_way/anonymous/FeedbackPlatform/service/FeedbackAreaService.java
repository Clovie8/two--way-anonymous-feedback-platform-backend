package two_way.anonymous.FeedbackPlatform.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import two_way.anonymous.FeedbackPlatform.domain.FeedbackArea;
import two_way.anonymous.FeedbackPlatform.repository.FeedbackAreaRepository;
import two_way.anonymous.FeedbackPlatform.repository.ManagerRepository;

@Service
public class FeedbackAreaService {
    @Autowired
    private FeedbackAreaRepository repository;

    @Autowired
    private ManagerRepository managerRepository;

    public List<FeedbackArea> getAll() {
        return repository.findAll();
    }

    public Optional<FeedbackArea> getById(UUID id) {
        return repository.findById(id);
    }

    public String save(FeedbackArea area) {
        repository.save(area);
        return "Feedback area saved successfully";
    }

    public String update(UUID id, FeedbackArea changes) {
        Optional<FeedbackArea> result = repository.findById(id);
        if (result.isEmpty()) {
            return "The feedback area with that id does not exist.";
        }

        FeedbackArea area = result.get();
        area.setName(changes.getName());
        repository.save(area);
        return "Feedback area updated successfully";
    }

    public String delete(UUID id) {
        Optional<FeedbackArea> result = repository.findById(id);
        if (result.isEmpty()) {
            return "The feedback area with that id does not exist.";
        }

        if (managerRepository.existsByFeedbackAreas_Id(id)) {
            return "Cannot delete feedback area because managers reference it.";
        }

        repository.delete(result.get());
        return "Feedback area deleted successfully";
    }
}
