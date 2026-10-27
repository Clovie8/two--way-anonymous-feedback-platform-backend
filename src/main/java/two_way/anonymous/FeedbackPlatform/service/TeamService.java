package two_way.anonymous.FeedbackPlatform.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import two_way.anonymous.FeedbackPlatform.domain.Team;
import two_way.anonymous.FeedbackPlatform.repository.ManagerRepository;
import two_way.anonymous.FeedbackPlatform.repository.TeamRepository;

@Service
public class TeamService {
    @Autowired
    private TeamRepository repository;

    @Autowired
    private ManagerRepository managerRepository;

    public List<Team> getAll() {
        return repository.findAll();
    }

    public Optional<Team> getById(UUID id) {
        return repository.findById(id);
    }

    public String save(Team team) {
        repository.save(team);
        return "Team saved successfully";
    }

    public String update(UUID id, Team changes) {
        Optional<Team> result = repository.findById(id);
        if (result.isEmpty()) {
            return "The team with that id does not exist.";
        }

        Team team = result.get();
        team.setTeamNumber(changes.getTeamNumber());
        team.setName(changes.getName());
        repository.save(team);
        return "Team updated successfully";
    }

    public String delete(UUID id) {
        Optional<Team> result = repository.findById(id);
        if (result.isEmpty()) {
            return "The team with that id does not exist.";
        }

        if (managerRepository.existsByTeam_Id(id)) {
            return "Cannot delete team because managers reference it.";
        }

        repository.delete(result.get());
        return "Team deleted successfully";
    }
}