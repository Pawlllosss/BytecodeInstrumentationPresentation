package pl.oczadly.example.webapp.matter.boundary;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.oczadly.example.webapp.matter.boundary.dto.MatterDTO;
import pl.oczadly.example.webapp.matter.boundary.dto.MatterDTOConverter;
import pl.oczadly.example.webapp.matter.control.MatterService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("matters")
public class MatterRestController {

    private final MatterService matterService;
    private final MatterDTOConverter matterDTOConverter;

    public MatterRestController(MatterService matterService, MatterDTOConverter matterDTOConverter) {
        this.matterService = matterService;
        this.matterDTOConverter = matterDTOConverter;
    }

    @GetMapping
    public ResponseEntity<List<MatterDTO>> getMatters() {
        List<MatterDTO> mattersDTO = matterService.getMatters()
                .stream()
                .map(matterDTOConverter::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(mattersDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatterDTO> getMatterById(@PathVariable long id) {
        Optional<MatterDTO> matterDTO = matterService.getMatterById(id)
                .map(matterDTOConverter::toDTO);

        return ResponseEntity.of(matterDTO);
    }
}
