package pl.oczadly.example.webapp.matter.boundary.dto;

import org.springframework.stereotype.Component;
import pl.oczadly.example.webapp.matter.entity.Matter;

@Component
public class MatterDTOConverter {

    public MatterDTO toDTO(Matter matter) {
        var matterDTO = new MatterDTO();
        matterDTO.setId(matter.getId());
        matterDTO.setName(matter.getName());
        matterDTO.setAreaOfLaw(matter.getArea());

        return matterDTO;
    }
}
