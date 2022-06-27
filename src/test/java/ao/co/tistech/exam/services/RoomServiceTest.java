package ao.co.tistech.exam.services;

import ao.co.tistech.exam.controllers.RoomController;
import ao.co.tistech.exam.dtos.AvailabilityDto;
import ao.co.tistech.exam.dtos.RoomDto;
import ao.co.tistech.exam.models.AvailabilityModel;
import ao.co.tistech.exam.models.RoomModel;
import ao.co.tistech.exam.repositories.RoomRepository;
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

import java.util.Arrays;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RoomController.class})
@DisplayName("RoomServiceTest")
public class RoomServiceTest {

    private ObjectMapper objectMapper;
    private MockMvc mockMvc;
    private static final String BASE_URL = "/tistech/api/v1/rooms";

    @Autowired
    private RoomController roomController;
    @MockBean
    private RoomService roomService;
    @MockBean
    private RoomRepository roomRepository;

    @Before
    public void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(roomController).build();
    }

    @Test
    public void create() throws Exception {
        RoomDto roomDto = new RoomDto();
        roomDto.setName("room test");
        roomDto.setAvailabilities(Arrays.asList(AvailabilityDto.builder().id(1L).build()));

        RoomModel roomModel = new RoomModel(1L, "room test", Arrays.asList(AvailabilityModel.builder().id(1L).build()));

        Mockito.when(roomRepository.existsByName(roomDto.getName())).thenReturn(false);
        Mockito.when(roomRepository.save(Mockito.any(RoomModel.class))).thenReturn(roomModel);
        roomRepository.save(roomModel);
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                .content(objectMapper.writeValueAsString(roomDto))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        Mockito.verify(roomRepository, Mockito.times(1)).save(Mockito.any(RoomModel.class));
    }

    @Test
    public void read() throws Exception {
        RoomDto roomDto = new RoomDto();
        roomDto.setId(1L);

        RoomModel roomModel = new RoomModel(1L, "room test", Arrays.asList(AvailabilityModel.builder().id(1L).build()));

        Mockito.when(roomRepository.findById(roomDto.getId())).thenReturn(Optional.of(roomModel));
        roomRepository.findById(roomDto.getId());
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/{id}", 1)
                .content(objectMapper.writeValueAsString(roomDto))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(roomRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    public void update() throws Exception {
        RoomDto roomDto = new RoomDto();
        roomDto.setName("room test");
        roomDto.setAvailabilities(Arrays.asList(AvailabilityDto.builder().id(1L).build()));

        RoomModel roomModel = new RoomModel(1L, "room test", Arrays.asList(AvailabilityModel.builder().id(1L).build()));

        Mockito.when(roomRepository.findById(roomDto.getId())).thenReturn(Optional.of(roomModel));
        Mockito.when(roomRepository.save(Mockito.any(RoomModel.class))).thenReturn(roomModel);
        roomRepository.save(roomModel);
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                .content(objectMapper.writeValueAsString(roomDto))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        Mockito.verify(roomRepository, Mockito.times(1)).save(Mockito.any(RoomModel.class));
    }

    @Test
    public void delete() throws Exception {
        RoomModel roomModel = new RoomModel(1L, "room test", Arrays.asList(AvailabilityModel.builder().id(1L).build()));
        roomRepository.delete(roomModel);
        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(roomRepository, Mockito.times(1)).delete(Mockito.any(RoomModel.class));
    }
}
