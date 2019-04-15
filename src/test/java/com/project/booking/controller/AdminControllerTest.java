package com.project.booking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.booking.entity.Room;
import com.project.booking.repository.EmployeerRepository;
import com.project.booking.repository.RoomRepository;
import com.project.booking.repository.TimeLineRepository;
import com.project.booking.service.AdminService;
import com.project.booking.service.EmployeerService;
import com.project.booking.service.EmployeerServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(AdminController.class)
@AutoConfigureMockMvc(secure = false)
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private RoomRepository roomRepository;

    @MockBean
    private TimeLineRepository timeLineRepository;

    @MockBean
    private EmployeerRepository employeerRepository;

   // @Autowired
    @MockBean
    private AdminService adminService;

    @MockBean
    private EmployeerService employeerService;

    @MockBean
    private AdminController adminController;

    @Before
    public void setUp(){
        employeerService = new EmployeerServiceImpl(employeerRepository, passwordEncoder);
        adminService = new AdminService(roomRepository, timeLineRepository);
        adminController = new AdminController(adminService, employeerService);
    }

    @Test
    public void testAddRoomStatusOk() throws Exception {
        Room room = new Room();
        room.setName("room two");

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRoom = objectMapper.writeValueAsString(room);

        given(adminService.addRoom(room)).willReturn(room);

        mockMvc.perform(MockMvcRequestBuilders.post("/admin/rooms")
                .contentType("application/json")
                .content(jsonRoom))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    public void testShowAllRoomStatusOk() throws Exception {
        Room room = new Room();
        room.setId(2l);
        room.setName("room two");

        List<Room> roomList = new ArrayList<>();
        roomList.add(room);

        ResponseEntity<List<Room>> responseEntity = ResponseEntity.ok().body(roomList);

        given(adminService.findAllRoom()).willReturn(roomList);
//      given(adminController.showRooms()).willReturn(responseEntity);

        mockMvc.perform(MockMvcRequestBuilders.get("/admin/rooms").contentType("application/json"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
