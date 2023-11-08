package api.vacinacao.vacina.controller;

import api.vacinacao.vacina.entity.Vaccine;
import api.vacinacao.vacina.exception.ResourceNotFoundException;
import api.vacinacao.vacina.services.VaccineService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class VaccineControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    VaccineService vaccineService;

    @Test
    @DisplayName("Deve retornar uma lista de vacinas cadastradas")
    void testObterTodas() throws Exception {
        Vaccine vaccine1 = new Vaccine();
        vaccine1.setManufacturer("Pfizer");
        vaccine1.setBatch("LD245");
        vaccine1.setValidateDate(LocalDate.of(2023, 12, 25));
        vaccine1.setAmountOfDose(2);
        vaccine1.setIntervalBetweenDoses(15);

        Vaccine vaccine2 = new Vaccine();
        vaccine2.setManufacturer("Pfizer");
        vaccine2.setBatch("LD245");
        vaccine2.setValidateDate(LocalDate.of(2023, 12, 25));
        vaccine2.setAmountOfDose(2);
        vaccine2.setIntervalBetweenDoses(15);

        List<Vaccine> vaccineList = Arrays.asList(vaccine1, vaccine2);

        Mockito.when(vaccineService.getAllVaccines()).thenReturn(vaccineList);

        mockMvc.perform(MockMvcRequestBuilders.get("/vaccine"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].manufacturer").value(vaccine1.getManufacturer()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].batch").value(vaccine1.getBatch()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].validateDate").value(vaccine1.getValidateDate().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].amountOfDose").value(vaccine1.getAmountOfDose()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].intervalBetweenDoses").value(vaccine1.getIntervalBetweenDoses()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].manufacturer").value(vaccine2.getManufacturer()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].batch").value(vaccine2.getBatch()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].validateDate").value(vaccine2.getValidateDate().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].amountOfDose").value(vaccine2.getAmountOfDose()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].intervalBetweenDoses").value(vaccine2.getIntervalBetweenDoses()));

        verify(vaccineService, times(1)).getAllVaccines();
    }

    @Test
    @DisplayName("Deve retornar um Array vazio quando busca não retornar vacinas")
    void testObterListaEmBranco() throws Exception {
        List<Vaccine> vaccines = new ArrayList<>();

        Mockito.when(vaccineService.getAllVaccines()).thenReturn(vaccines);

        mockMvc.perform(MockMvcRequestBuilders.get("/vaccine"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(0));

        verify(vaccineService, times(1)).getAllVaccines();
    }

    @Test
    @DisplayName("Deve retornar uma vacina ao buscar pelo id")
    void testObterPeloId() throws Exception, ResourceNotFoundException {
        Vaccine vaccine = new Vaccine();
        vaccine.setId("teste");
        vaccine.setManufacturer("Pfizer");
        vaccine.setBatch("LD245");
        vaccine.setValidateDate(LocalDate.of(2023, 12, 25));
        vaccine.setAmountOfDose(2);
        vaccine.setIntervalBetweenDoses(15);

        Mockito.when(vaccineService.getVaccineById(vaccine.getId())).thenReturn(vaccine);

        mockMvc.perform(MockMvcRequestBuilders.get("/vaccine/" + vaccine.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.manufacturer").value(vaccine.getManufacturer()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.batch").value(vaccine.getBatch()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.validateDate").value(vaccine.getValidateDate().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amountOfDose").value(vaccine.getAmountOfDose()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.intervalBetweenDoses").value(vaccine.getIntervalBetweenDoses()));

        verify(vaccineService, times(1)).getVaccineById(vaccine.getId());
    }

    @Test
    @DisplayName("Deve retornar um ResourceNotFoundException ao buscar um id inexistente")
    void testObterPorUmIdInvalido() throws Exception, ResourceNotFoundException {
        String id = "teste";

        Mockito.when(vaccineService.getVaccineById(id)).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/vaccine/" + id))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(vaccineService, times(1)).getVaccineById(id);
    }

}
