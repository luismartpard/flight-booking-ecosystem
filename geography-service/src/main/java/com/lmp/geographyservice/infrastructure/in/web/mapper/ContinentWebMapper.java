package com.lmp.geographyservice.infrastructure.in.web.mapper;

import com.lmp.geographyservice.domain.model.Continent;
import com.lmp.geographyservice.infrastructure.in.web.dto.ContinentResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContinentWebMapper {

    ContinentResponse toResponse(Continent continent);

}
