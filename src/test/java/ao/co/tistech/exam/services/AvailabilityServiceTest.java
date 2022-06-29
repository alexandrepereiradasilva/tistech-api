package ao.co.tistech.exam.services;

import ao.co.tistech.exam.controllers.AvailabilityController;
import ao.co.tistech.exam.dtos.AvailabilityDto;
import ao.co.tistech.exam.models.AvailabilityModel;
import ao.co.tistech.exam.repositories.AvailabilityRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AvailabilityController.class})
@DisplayName("AvailabilityServiceTest")
public class AvailabilityServiceTest {

    @MockBean
    private ObjectMapper objectMapper;
    private MockMvc mockMvc;
    private static final String BASE_URL = "/tistech/api/v1/availabilities";

    @Autowired
    private AvailabilityController availabilityController;
    @MockBean
    private AvailabilityService availabilityService;
    @MockBean
    private AvailabilityRepository availabilityRepository;

    @Before
    public void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(availabilityController).build();
    }

    @Test
    public void create() throws Exception {
        AvailabilityDto availabilityDto = new AvailabilityDto();
        availabilityDto.setName("availability test");

        AvailabilityModel availabilityModel = new AvailabilityModel(1L, "availability test");

        Mockito.when(availabilityRepository.existsByName(availabilityDto.getName())).thenReturn(false);
        Mockito.when(availabilityRepository.save(Mockito.any(AvailabilityModel.class))).thenReturn(availabilityModel);
        availabilityRepository.save(availabilityModel);
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                .content(objectMapper.writeValueAsString(availabilityDto))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        Mockito.verify(availabilityRepository, Mockito.times(1)).save(Mockito.any(AvailabilityModel.class));
    }

    @Test
    public void read() throws Exception {
        AvailabilityDto availabilityDto = new AvailabilityDto();
        availabilityDto.setId(1L);

        AvailabilityModel availabilityModel = new AvailabilityModel(1L, "availability test");

        Mockito.when(availabilityRepository.findById(availabilityDto.getId())).thenReturn(Optional.of(availabilityModel));
        availabilityRepository.findById(availabilityDto.getId());
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/{id}", 1)
                .content(objectMapper.writeValueAsString(availabilityDto))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(availabilityRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    public void update() throws Exception {
        AvailabilityDto availabilityDto = new AvailabilityDto();
        availabilityDto.setName("availability test");

        AvailabilityModel availabilityModel = new AvailabilityModel(1L, "availability test");

        Mockito.when(availabilityRepository.findById(availabilityDto.getId())).thenReturn(Optional.of(availabilityModel));
        Mockito.when(availabilityRepository.save(Mockito.any(AvailabilityModel.class))).thenReturn(availabilityModel);
        availabilityRepository.save(availabilityModel);
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                .content(objectMapper.writeValueAsString(availabilityDto))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        Mockito.verify(availabilityRepository, Mockito.times(1)).save(Mockito.any(AvailabilityModel.class));
    }

    @Test
    public void delete() throws Exception {
        AvailabilityModel availabilityModel = new AvailabilityModel(1L, "availability test");
        availabilityRepository.delete(availabilityModel);
        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(availabilityRepository, Mockito.times(1)).delete(Mockito.any(AvailabilityModel.class));
    }
}
