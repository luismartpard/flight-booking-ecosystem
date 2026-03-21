package com.lmp.geographyservice.application.port.out;

import com.lmp.flightbookingcommon.infrastructure.pagination.PageQuery;
import com.lmp.flightbookingcommon.infrastructure.pagination.PageResult;
import com.lmp.geographyservice.application.query.ContinentSortField;
import com.lmp.geographyservice.domain.model.Continent;

import java.util.Optional;

public interface ContinentRepositoryPort {

    Optional<Continent> findByCode(String code);

    PageResult<Continent> findAll(PageQuery<ContinentSortField> pageQuery);

}
