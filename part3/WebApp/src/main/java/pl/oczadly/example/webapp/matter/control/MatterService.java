package pl.oczadly.example.webapp.matter.control;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.oczadly.example.webapp.annotation.DetailedLogs;
import pl.oczadly.example.webapp.matter.entity.Matter;

import java.util.List;
import java.util.Optional;

@Service
public class MatterService {

    private static final Matter MATTER_1 = new Matter(1L, "Supplying misleading evidence", "Criminal");
    private static final Matter MATTER_2 = new Matter(2L, "Acquisition of company ACME", "Contract");

    private static final Logger logger = LoggerFactory.getLogger(MatterService.class);

    public List<Matter> getMatters() {
        return List.of(MATTER_1, MATTER_2);
    }

    @DetailedLogs
    public Optional<Matter> getMatterById(Long id) {
        logger.info("Trying to retrieve matter with id: {}", id);
        return Optional.of(MATTER_1);
    }
}
