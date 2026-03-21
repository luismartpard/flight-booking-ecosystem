package com.lmp.geographyservice.application.port.in;

import com.lmp.flightbookingcommon.infrastructure.pagination.PageQuery;
import com.lmp.flightbookingcommon.infrastructure.pagination.PageResult;
import com.lmp.geographyservice.application.query.ContinentSortField;
import com.lmp.geographyservice.domain.model.Continent;

public interface GetContinentsUseCase {

    PageResult<Continent> getContinents(PageQuery<ContinentSortField> pageQuery);

}
