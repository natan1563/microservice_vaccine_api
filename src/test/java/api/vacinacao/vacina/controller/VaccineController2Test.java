package api.vacinacao.vacina.controller;

import api.vacinacao.vacina.entity.Vaccine;
import api.vacinacao.vacina.exception.RegisterBadRequestException;
import api.vacinacao.vacina.services.VaccineService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.assertj.core.internal.bytebuddy.ClassFileVersion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@AutoConfigureMockMvc
public class VaccineController2Test {
    @Autowired
    MockMvc mockMvc;

    @InjectMocks
    private VaccineController vaccineController;

    @MockBean
    VaccineService vaccineService;

    @Autowired
    ObjectMapper objectMapper = new ObjectMapper();

    private static final ObjectMapper mapper = createObjectMapper();

    @BeforeEach
    public void setUp() {
        objectMapper.registerModule(new JavaTimeModule());
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve retornar uma lista de vacinas no endpoint de listagem '/vaccine'")
    void testShouldBeCreateAnVaccine() throws RegisterBadRequestException, Exception {
        Vaccine vaccine = new Vaccine(
                "Pfizer",
                "PF12345",
                LocalDate.of(2023, 12, 31),
                2,
                21
        );
        vaccine.setId("teste123456");

        Mockito.when(vaccineService.registerVaccine(vaccine)).thenReturn(vaccine);
//        String payload = objectMapper.createObjectMapper(vaccine);
        byte[] payload = mapper.writeValueAsBytes(vaccine);

        mockMvc.perform(
                    MockMvcRequestBuilders
                            .post("/vaccine")
                            .content(payload)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(vaccine.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.manufacturer").value(vaccine.getManufacturer()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.batch").value(vaccine.getBatch()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.validateDate").value(vaccine.getValidateDate()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amountOfDose").value(vaccine.getAmountOfDose()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.intervalBetweenDoses").value(vaccine.getIntervalBetweenDoses()));

        verify(vaccineService, times(1)).registerVaccine(vaccine);
    }

    private static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }
}
