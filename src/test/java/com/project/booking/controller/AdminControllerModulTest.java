package com.project.booking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.booking.entity.Room;
import com.project.booking.entity.TimeLine;
import com.project.booking.service.AdminService;
import com.project.booking.service.EmployeerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ui.ExtendedModelMap;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AdminControllerModulTest {

    private final List<Room> rooms = new ArrayList<>();
    private final List<TimeLine> timeLines = new ArrayList<>();

    private AdminService adminService = Mockito.mock(AdminService.class);
    private EmployeerService employeerService = mock(EmployeerService.class);

    private AdminController adminController = new AdminController(adminService, employeerService);

    @Before
    public void initLists() {
        Room room = new Room();
        room.setId(1l);
        room.setName("room one");
        rooms.add(room);

        TimeLine timeLine = new TimeLine();
        timeLine.setRoom(room);
        timeLine.setId(1l);
        timeLine.setTimeFrom("12:00");
        timeLine.setTimeTo("13:00");

        timeLines.add(timeLine);
    }

    @Test
    public void testRooms() {
        when(adminService.findAllRoom()).thenReturn(rooms);

        ReflectionTestUtils.setField(adminController, "adminService", adminService);

        ExtendedModelMap uiModel = new ExtendedModelMap();
        uiModel.addAttribute("rooms",  adminController.showRooms().getBody());

        assertEquals(rooms, uiModel.get("rooms"));
    }

    @Test
    public void testTimelines() {
        when(adminService.findAllTimeLine()).thenReturn(timeLines);

        ReflectionTestUtils.setField(adminController, "adminService", adminService);

        ExtendedModelMap uiModel = new ExtendedModelMap();
        uiModel.addAttribute("timelines", adminController.showTimeLines().getBody());

        assertEquals(timeLines, uiModel.get("timelines"));
    }

    @Test
    public void testRoom() {
        Room newRoom = new Room();
        newRoom.setId(2l);
        newRoom.setName("room two");

        ObjectMapper objectMapper = new ObjectMapper();


        when(adminService.saveRoom(newRoom)).thenAnswer( (InvocationOnMock invocationOnMock) -> {
            rooms.add(newRoom);
            return newRoom;
        });

        ReflectionTestUtils.setField(adminController, "adminService", adminService);
        Room room = adminController.saveRoom(newRoom).getBody();

        assertEquals(Long.valueOf(2l), room.getId());
        assertEquals("room two", room.getName());
        assertEquals(2, rooms.size());
    }

    @Test
    public void testTimeline() {
        TimeLine newTimeLine = new TimeLine();
        newTimeLine.setId(2l);
        newTimeLine.setTimeFrom("13:00");
        newTimeLine.setTimeTo("14:00");

        when(adminService.addTimeLine(newTimeLine)).thenAnswer( (InvocationOnMock invocationOnMock) -> {
           timeLines.add(newTimeLine);
           return newTimeLine;
        });

        ReflectionTestUtils.setField(adminController, "adminService", adminService);
        TimeLine timeLine = adminController.addTimeLines(newTimeLine, rooms.get(0)).getBody();

        assertEquals(Long.valueOf(2l), timeLine.getId());
        assertEquals("13:00", timeLine.getTimeFrom());
        assertEquals(2, timeLines.size());
    }





}
