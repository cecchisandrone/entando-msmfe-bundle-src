package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.ConferenceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Conference} and its DTO {@link ConferenceDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ConferenceMapper extends EntityMapper<ConferenceDTO, Conference> {}
