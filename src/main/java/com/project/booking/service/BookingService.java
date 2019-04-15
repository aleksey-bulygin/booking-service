package com.project.booking.service;

import com.project.booking.entity.Booking;
import com.project.booking.repository.EmployeerRepository;
import com.project.booking.repository.TimeLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.booking.repository.BookingRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final EmployeerRepository employeerRepository;
    private final TimeLineRepository timeLineRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository, EmployeerRepository employeerRepository, TimeLineRepository timeLineRepository) {
        this.bookingRepository = bookingRepository;
        this.employeerRepository = employeerRepository;
        this.timeLineRepository = timeLineRepository;
    }

    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    public Optional<Booking> findById(Long id) {
        return bookingRepository.findById(id);
    }

    public void addBooking(Booking booking) {
        bookingRepository.save(booking);
    }

    public void deleteBookingById(Long id) {
        Optional<Booking> byId = bookingRepository.findById(id);

        if (byId.isPresent()) {
            Booking booking = byId.get();
            booking.setEmployeer(null);
            booking.setTimeLine(null);
            bookingRepository.saveAndFlush(booking);
            bookingRepository.deleteById(id);
        }
    }
}
