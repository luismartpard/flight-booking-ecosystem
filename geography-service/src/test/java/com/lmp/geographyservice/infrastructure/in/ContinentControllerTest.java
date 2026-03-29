package com.lmp.geographyservice.infrastructure.in;

import com.lmp.flightbookingcommon.infrastructure.pagination.PageQuery;
import com.lmp.flightbookingcommon.infrastructure.pagination.PageResult;
import com.lmp.flightbookingcommon.testutil.PageResultTestFactory;
import com.lmp.geographyservice.application.port.in.GetContinentByCodeUseCase;
import com.lmp.geographyservice.application.port.in.GetContinentsUseCase;
import com.lmp.geographyservice.domain.model.Continent;
import com.lmp.geographyservice.infrastructure.in.web.ContinentController;
import com.lmp.geographyservice.infrastructure.in.web.dto.ContinentResponse;
import com.lmp.geographyservice.infrastructure.in.web.mapper.ContinentWebMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ContinentController.class)
class ContinentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GetContinentsUseCase getContinentsUseCase;

    @MockitoBean
    private GetContinentByCodeUseCase getContinentByCodeUseCase;

    @MockitoBean
    private ContinentWebMapper continentWebMapper;

    @Test
    void getContinents_ValidCode_returns200() throws Exception {

        Continent continent = Continent.of("EU", "Europe");

        when(getContinentByCodeUseCase.getContinentByCode("EU"))
                .thenReturn(continent);

        when(continentWebMapper.toResponse(continent))
                .thenReturn(new ContinentResponse(continent.getCode(), continent.getName()));

        mockMvc.perform(get("/api/v1/continents/code/EU"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("EU"));

        verify(getContinentByCodeUseCase).getContinentByCode("EU");
        verifyNoMoreInteractions(getContinentByCodeUseCase);

    }

    @Test
    void getContinents_withAscSort_returns200() throws Exception {

        Continent continent = Continent.of("EU", "Europe");

        ContinentResponse response = new ContinentResponse(
                continent.getCode(), continent.getName()
        );

        PageResult<Continent> pageResponse =
                PageResultTestFactory.page(List.of(continent));

        when(getContinentsUseCase.getContinents(any(PageQuery.class)))
                .thenReturn(pageResponse);

        when(continentWebMapper.toResponse(continent))
                .thenReturn(response);

        mockMvc.perform(get("/api/v1/continents")
                        .param("page", "0")
                        .param("size", "20")
                        .param("sort", "ASC"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.content[0].code").value("EU"))
                .andExpect(jsonPath("$.content[0].name").value("Europe"));

        verify(getContinentsUseCase).getContinents(any(PageQuery.class));
        verifyNoMoreInteractions(getContinentByCodeUseCase);

    }

    @Test
    void getContinents_withDescSort_returns200() throws Exception {

        Continent continent = Continent.of("EU", "Europe");

        ContinentResponse response = new ContinentResponse(
                continent.getCode(), continent.getName()
        );

        PageResult<Continent> pageResponse =
                PageResultTestFactory.page(List.of(continent));

        when(getContinentsUseCase.getContinents(any(PageQuery.class)))
                .thenReturn(pageResponse);

        when(continentWebMapper.toResponse(continent))
                .thenReturn(response);

        mockMvc.perform(get("/api/v1/continents")
                        .param("page", "0")
                        .param("size", "20")
                        .param("sort", "DESC"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.content[0].code").value("EU"))
                .andExpect(jsonPath("$.content[0].name").value("Europe"));

        verify(getContinentsUseCase).getContinents(any(PageQuery.class));
        verifyNoMoreInteractions(getContinentByCodeUseCase);

    }

}
