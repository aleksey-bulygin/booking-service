package com.project.booking.service;

import com.project.booking.entity.Room;
import com.project.booking.entity.TimeLine;
import com.project.booking.repository.RoomRepository;
import com.project.booking.repository.TimeLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    private final RoomRepository roomRepository;
    private final TimeLineRepository timeLineRepository;

    @Autowired
    public AdminService(RoomRepository roomRepository, TimeLineRepository timeLineRepository) {
        this.roomRepository = roomRepository;
        this.timeLineRepository = timeLineRepository;
    }

    public Room addRoom(@NotNull Room room) {
        return roomRepository.save(room);
    }

    public Optional<Room> saveRoom(Room room) {
        return Optional.of(roomRepository.save(room));
    }

    public TimeLine addTimeLine(@NotNull TimeLine timeLine) {
        return timeLineRepository.save(timeLine);
    }

    public List<Room> findAllRoom() {
        return roomRepository.findAll();
    }

    public List<TimeLine> findAllTimeLine() {
        return timeLineRepository.findAll();
    }

    public void deleteRoomById(Long id) {
        roomRepository.deleteById(id);
    }

    public void deleteTimeLineById(Long id) {
        Optional<TimeLine> byId = timeLineRepository.findById(id);

        if (byId.isPresent()) {
            TimeLine timeLine = byId.get();
            timeLine.setRoom(null);
            timeLineRepository.saveAndFlush(timeLine);

            timeLineRepository.deleteById(id);
        }

    }
}
