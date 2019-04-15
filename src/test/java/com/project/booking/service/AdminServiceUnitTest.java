package com.project.booking.service;

import com.project.booking.repository.RoomRepository;
import com.project.booking.repository.TimeLineRepository;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class AdminServiceUnitTest {

    private AdminService adminService;

    private RoomRepository roomRepository;
    private TimeLineRepository timeLineRepository;

    @Before
    public void setUp(){
        roomRepository = mock(RoomRepository.class);
        timeLineRepository = mock(TimeLineRepository.class);

        adminService = new AdminService(roomRepository, timeLineRepository);
    }

    @Test
    public void testAddRoom(){
        
    }
}
