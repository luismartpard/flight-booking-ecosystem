package com.lmp.geographyservice.infrastructure.in.web.mapper;

import com.lmp.geographyservice.domain.model.Country;
import com.lmp.geographyservice.infrastructure.in.web.dto.CountryResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CountryWebMapper {

    CountryResponse toResponse(Country country);

}
