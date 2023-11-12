package api.vacinacao.vacina.controller;

import api.vacinacao.vacina.entity.Vaccine;
import api.vacinacao.vacina.exception.RegisterBadRequestException;
import api.vacinacao.vacina.exception.ResourceNotFoundException;
import api.vacinacao.vacina.services.VaccineService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
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

@SpringBootTest
@AutoConfigureMockMvc
public class VaccineControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    VaccineService vaccineService;

    @InjectMocks
    private VaccineController vaccineController;

    @Autowired
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        objectMapper.registerModule(new JavaTimeModule());
        MockitoAnnotations.openMocks(this);
    }

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
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].validateDate.length()").value(3)) // TO DO: Melhorar
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].amountOfDose").value(vaccine1.getAmountOfDose()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].intervalBetweenDoses").value(vaccine1.getIntervalBetweenDoses()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].manufacturer").value(vaccine2.getManufacturer()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].batch").value(vaccine2.getBatch()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].validateDate.length()").value(3)) // TO DO: Melhorar
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.validateDate.length()").value(3))// TO DO: Melhorar
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

    @Test
    @DisplayName("Deve retornar uma lista de vacinas no endpoint de listagem '/vaccine'")
    void testShouldBeCreateAnVaccine() throws RegisterBadRequestException, Exception {
        Vaccine vaccine = new Vaccine();
        vaccine.setValidateDate(LocalDate.of(2023, 12, 31));
        vaccine.setManufacturer("Pfizer");
        vaccine.setAmountOfDose(2);
        vaccine.setBatch("PF12345");
        vaccine.setIntervalBetweenDoses(21);

        Mockito.when(vaccineService.registerVaccine(Mockito.any(Vaccine.class))).thenReturn(vaccine);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/vaccine")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(vaccine))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(vaccine.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.manufacturer").value(vaccine.getManufacturer()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.batch").value(vaccine.getBatch()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.validateDate.length()").value(3)) // TO DO: Verificar a melhor forma de fazer
                .andExpect(MockMvcResultMatchers.jsonPath("$.amountOfDose").value(vaccine.getAmountOfDose()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.intervalBetweenDoses").value(vaccine.getIntervalBetweenDoses()));

        verify(vaccineService, times(1)).registerVaccine(Mockito.any(Vaccine.class));
    }

    @Test
    @DisplayName("Deve atualizar uma vacina")
    void testAtualizarVacina() throws Exception, ResourceNotFoundException {
        Vaccine vaccine = new Vaccine();
        vaccine.setId("teste");
        vaccine.setManufacturer("Pfizer");
        vaccine.setBatch("LD245");
        vaccine.setValidateDate(LocalDate.of(2023, 12, 25));
        vaccine.setAmountOfDose(2);
        vaccine.setIntervalBetweenDoses(15);

        Mockito.when(vaccineService.update(Mockito.any(Vaccine.class),Mockito.anyString())).thenReturn(vaccine);

        mockMvc.perform(MockMvcRequestBuilders.put("/vaccine/" + vaccine.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vaccine)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.manufacturer").value(vaccine.getManufacturer()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.batch").value(vaccine.getBatch()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.validateDate.length()").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amountOfDose").value(vaccine.getAmountOfDose()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.intervalBetweenDoses").value(vaccine.getIntervalBetweenDoses()));

        verify(vaccineService, times(1)).update(Mockito.any(Vaccine.class),Mockito.eq(vaccine.getId()));
    }

    @Test
    @DisplayName("Deve excluir uma vacina pelo id")
    void testExcluirVacina() throws Exception, ResourceNotFoundException {
        Vaccine vaccine = new Vaccine();
        vaccine.setId("teste");
        vaccine.setManufacturer("Pfizer");
        vaccine.setBatch("LD245");
        vaccine.setValidateDate(LocalDate.of(2023, 12, 25));
        vaccine.setAmountOfDose(2);
        vaccine.setIntervalBetweenDoses(15);

        Mockito.doNothing().when(vaccineService).delete(vaccine.getId());

        mockMvc.perform(MockMvcRequestBuilders.delete("/vaccine/" + vaccine.getId()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        verify(vaccineService, times(1)).delete(vaccine.getId());
    }

}
