package ao.co.tistech.exam.services;

import ao.co.tistech.exam.controllers.ExamController;
import ao.co.tistech.exam.dtos.ExamDto;
import ao.co.tistech.exam.models.AvailabilityModel;
import ao.co.tistech.exam.models.CandidateModel;
import ao.co.tistech.exam.models.ExamModel;
import ao.co.tistech.exam.repositories.ExamRepository;
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
@ContextConfiguration(classes = {ExamController.class})
@DisplayName("ExamServiceTest")
public class ExamServiceTest {

    private ObjectMapper objectMapper;
    private MockMvc mockMvc;
    private static final String BASE_URL = "/tistech/api/v1/exams";

    @Autowired
    private ExamController examController;
    @MockBean
    private ExamService examService;
    @MockBean
    private ExamRepository examRepository;

    @Before
    public void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(examController).build();
    }

    @Test
    public void create() throws Exception {
        ExamDto examDto = new ExamDto();
        examDto.setName("exam test");

        ExamModel examModel = new ExamModel(1L, "exam test", AvailabilityModel.builder().id(1L).build(), CandidateModel.builder().id(1L).build());

        Mockito.when(examRepository.existsByName(examDto.getName())).thenReturn(false);
        Mockito.when(examRepository.save(Mockito.any(ExamModel.class))).thenReturn(examModel);
        examRepository.save(examModel);
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                .content(objectMapper.writeValueAsString(examDto))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        Mockito.verify(examRepository, Mockito.times(1)).save(Mockito.any(ExamModel.class));
    }

    @Test
    public void read() throws Exception {
        ExamDto examDto = new ExamDto();
        examDto.setId(1L);

        ExamModel examModel = new ExamModel(1L, "exam test", AvailabilityModel.builder().id(1L).build(), CandidateModel.builder().id(1L).build());

        Mockito.when(examRepository.findById(examDto.getId())).thenReturn(Optional.of(examModel));
        examRepository.findById(examDto.getId());
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/{id}", 1)
                .content(objectMapper.writeValueAsString(examDto))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(examRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    public void update() throws Exception {
        ExamDto examDto = new ExamDto();
        examDto.setName("exam test");

        ExamModel examModel = new ExamModel(1L, "exam test", AvailabilityModel.builder().id(1L).build(), CandidateModel.builder().id(1L).build());

        Mockito.when(examRepository.findById(examDto.getId())).thenReturn(Optional.of(examModel));
        Mockito.when(examRepository.save(Mockito.any(ExamModel.class))).thenReturn(examModel);
        examRepository.save(examModel);
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                .content(objectMapper.writeValueAsString(examDto))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        Mockito.verify(examRepository, Mockito.times(1)).save(Mockito.any(ExamModel.class));
    }

    @Test
    public void delete() throws Exception {
        ExamModel examModel = new ExamModel(1L, "exam test", AvailabilityModel.builder().id(1L).build(), CandidateModel.builder().id(1L).build());
        examRepository.delete(examModel);
        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(examRepository, Mockito.times(1)).delete(Mockito.any(ExamModel.class));
    }
}
