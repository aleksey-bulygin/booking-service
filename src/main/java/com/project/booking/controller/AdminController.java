package com.project.booking.controller;

import com.project.booking.entity.Employeer;
import com.project.booking.entity.Room;
import com.project.booking.entity.TimeLine;
import com.project.booking.exception.BadRequestException;
import com.project.booking.exception.NotAcceptableException;
import com.project.booking.exception.NotFoundException;
import com.project.booking.service.AdminService;
import com.project.booking.service.EmployeerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    private static final Logger logger = LogManager.getLogger(AdminController.class.getName());

    private final AdminService adminService;
    private final EmployeerService employeerService;

    @Autowired
    public AdminController(AdminService adminService, EmployeerService employeerService) {
        this.adminService = adminService;
        this.employeerService = employeerService;
    }

    @RequestMapping(value = "/rooms", method = RequestMethod.GET, consumes = {"application/json"})
    public ResponseEntity<List<Room>> showRooms() {
        logger.info("Enter in showRooms method");
        List<Room> rooms = adminService.findAllRoom();

        if (!rooms.isEmpty())
            return ResponseEntity.ok().body(rooms);
        else
           throw new NotFoundException("rooms is't found");
    }

    @RequestMapping(value = "/timelines", method = RequestMethod.GET, consumes = {"application/json"})
    public ResponseEntity<List<TimeLine>> showTimeLines() {
        List<TimeLine> timeLines = adminService.findAllTimeLine();

        if (!timeLines.isEmpty())
            return ResponseEntity.ok().body(timeLines);
        else
            throw new NotFoundException("timelines is't found");
    }

    /**
    @RequestMapping(value = "/rooms", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<Room> addRoom(@RequestBody Room room) {

        if (room == null)
            throw new BadRequestException("room is null");

        if (room.getName().trim().isEmpty())
            throw new NotAcceptableException("Room's name is empty");
        else
            adminService.addRoom(room);



        return ResponseEntity.ok().body(room);
    }
    **/
    @RequestMapping(value = "/rooms", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<Room> saveRoom(@RequestBody Room room) {
        return adminService.saveRoom(room).map( room1 -> ResponseEntity.ok().body(room1) )
                                    .orElseThrow( () -> new BadRequestException("room is null"));
    }

    @RequestMapping(value = "timelines/{room_id}", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<TimeLine> addTimeLines(@RequestBody TimeLine timeLine, @PathVariable("room_id") Room room) {
        if (room == null)
            throw new BadRequestException("room is null");

        String regxTime = "[0-9]{1,2}\\:[0-9]{2}";

        if (timeLine.getTimeTo().matches(regxTime) && timeLine.getTimeFrom().matches(regxTime)) {
            timeLine.setRoom(room);
            adminService.addTimeLine(timeLine);
        } else
            throw new NotAcceptableException("Input time_from : " + timeLine.getTimeFrom() + " /time_from : "
                    + timeLine.getTimeTo() + " in format : hh:mm");

        return ResponseEntity.ok().body(timeLine);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/rooms/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Room room) {
        if (room == null)
            throw new BadRequestException("room is null");
        else
            adminService.deleteRoomById(room.getId());

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/timelines/{id}")
    public ResponseEntity<Void> deleteTimeLine(@PathVariable TimeLine timeLine) {
        if (timeLine == null)
            throw new BadRequestException("timeline is null");
        else
            adminService.deleteTimeLineById(timeLine.getId());

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = "/employeers", method = RequestMethod.GET)
    public ResponseEntity<List<Employeer>> showEmployeers(){
        List<Employeer> employeers = employeerService.findAll();

        if (employeers.isEmpty())
            throw new NotFoundException("employeers is empty");

        return ResponseEntity.ok().body(employeers);
    }

    @RequestMapping(value = "/employeers/{id}", method = RequestMethod.GET)
    public ResponseEntity<Employeer> showEmployeer(@PathVariable Long id) {
        Employeer employeer = employeerService.findById(id).get();

        if (employeer == null)
            throw new NotFoundException("employeer is null");

        return ResponseEntity.ok().body(employeer);
    }

    @RequestMapping(value = "/employeers/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteEmployeer(@PathVariable Long id) {
        Employeer employeer = employeerService.findOne(id);

        if (employeer == null)
            throw new NotFoundException("employeer is null");

        employeerService.remove(employeer);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
