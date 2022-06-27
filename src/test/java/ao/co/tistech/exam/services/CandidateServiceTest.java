package ao.co.tistech.exam.services;

import ao.co.tistech.exam.controllers.CandidateController;
import ao.co.tistech.exam.dtos.CandidateDto;
import ao.co.tistech.exam.models.CandidateModel;
import ao.co.tistech.exam.repositories.CandidateRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {CandidateController.class})
@DisplayName("CandidateServiceTest")
public class CandidateServiceTest {

    private ObjectMapper objectMapper;
    private MockMvc mockMvc;
    private static final String BASE_URL = "/tistech/api/v1/candidates";

    @Autowired
    private CandidateController candidateController;
    @MockBean
    private CandidateService candidateService;
    @MockBean
    private CandidateRepository candidateRepository;

    @Before
    public void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(candidateController).build();
    }

    @Test
    public void create() throws Exception {
        CandidateDto candidateDto = new CandidateDto();
        candidateDto.setName("candidate test");
        candidateDto.setEmail("emailteste@email.com");

        CandidateModel candidateModel = new CandidateModel(1L, "candidate test", "emailteste@email.com");

        Mockito.when(candidateRepository.existsByName(candidateDto.getName())).thenReturn(false);
        Mockito.when(candidateRepository.save(Mockito.any(CandidateModel.class))).thenReturn(candidateModel);
        candidateRepository.save(candidateModel);
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                .content(objectMapper.writeValueAsString(candidateDto))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        Mockito.verify(candidateRepository, Mockito.times(1)).save(Mockito.any(CandidateModel.class));
    }

    @Test
    public void read() throws Exception {
        CandidateDto candidateDto = new CandidateDto();
        candidateDto.setId(1L);

        CandidateModel candidateModel = new CandidateModel(1L, "candidate test", "emailteste@email.com");

        Mockito.when(candidateRepository.findById(candidateDto.getId())).thenReturn(Optional.of(candidateModel));
        candidateRepository.findById(candidateDto.getId());
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/{id}", 1)
                .content(objectMapper.writeValueAsString(candidateDto))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(candidateRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    public void update() throws Exception {
        CandidateDto candidateDto = new CandidateDto();
        candidateDto.setName("candidate test");
        candidateDto.setEmail("emailteste@email.com");

        CandidateModel candidateModel = new CandidateModel(1L, "candidate test", "emailteste@email.com");

        Mockito.when(candidateRepository.findById(candidateDto.getId())).thenReturn(Optional.of(candidateModel));
        Mockito.when(candidateRepository.save(Mockito.any(CandidateModel.class))).thenReturn(candidateModel);
        candidateRepository.save(candidateModel);
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                .content(objectMapper.writeValueAsString(candidateDto))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        Mockito.verify(candidateRepository, Mockito.times(1)).save(Mockito.any(CandidateModel.class));
    }

    @Test
    public void delete() throws Exception {
        CandidateModel candidateModel = new CandidateModel(1L, "candidate test", "emailteste@email.com");
        candidateRepository.delete(candidateModel);
        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(candidateRepository, Mockito.times(1)).delete(Mockito.any(CandidateModel.class));
    }
}
