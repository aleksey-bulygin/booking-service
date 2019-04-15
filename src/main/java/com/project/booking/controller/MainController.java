package com.project.booking.controller;

import com.project.booking.entity.Booking;
import com.project.booking.entity.Employeer;
import com.project.booking.entity.TimeLine;
import com.project.booking.exception.NotFoundException;
import com.project.booking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/booking")
@PreAuthorize("hasAuthority('USER')")
public class MainController {

    private final BookingService bookingService;

    @Autowired
    public MainController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Booking>> showBookings() {
        List<Booking> bookings = bookingService.findAll();

        if (bookings.isEmpty())
            throw new NotFoundException("bookings is empty");
        else
            return ResponseEntity.ok().body(bookings);
    }

    @RequestMapping(method = RequestMethod.GET, value = "{id}")
    public ResponseEntity<Booking> showBooking(@RequestParam Long id) {
        Optional<Booking> bookingOptional = bookingService.findById(id);

        Booking booking = bookingOptional.get();

        if (booking == null)
            throw new NotFoundException("booking with bookingID = " + id + "is't found");
        else
            return ResponseEntity.ok().body(booking);
    }

    @RequestMapping( value = "{timeLineId}/{employeerId}", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<List<Booking>> addBooking(@RequestBody Booking booking,
                                    @PathVariable("timeLineId") TimeLine timeLine,
                                    @PathVariable("employeerId") Employeer employeer) {

        if (timeLine == null || employeer == null)
            throw new NotFoundException("timeLine: " + timeLine.toString() + ", employeer: " + employeer.toString());
        else {
            booking.setEmployeer(employeer);
            booking.setTimeLine(timeLine);
        }

        bookingService.addBooking(booking);
        List<Booking> bookings = bookingService.findAll();

        return ResponseEntity.ok().body(bookings);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable("id") Booking booking) {
        if (booking == null)
            throw new NotFoundException("booking is't found for delete");
        else
            bookingService.deleteBookingById(booking.getId());

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
