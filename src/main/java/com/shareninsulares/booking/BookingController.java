package com.shareninsulares.booking;

import com.shareninsulares.booking.dto.BookingResponse;
import com.shareninsulares.booking.dto.CreateBookingRequest;
import com.shareninsulares.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingResponse> create(
            @Valid @RequestBody CreateBookingRequest request,
            @AuthenticationPrincipal User user){
        return ResponseEntity.ok(bookingService.create(request, user));
    }

    @GetMapping("/my")
    public ResponseEntity<List<BookingResponse>> getMyBookings(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(bookingService.getMyBookings(user));
    }

    @GetMapping("/received")
    public ResponseEntity<List<BookingResponse>> getReceivedBookings(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(bookingService.getReceivedBookings(user));
    }

    @PutMapping("/{id}/accept")
    public ResponseEntity<BookingResponse> accept(
            @PathVariable Long id,
            @AuthenticationPrincipal User user){
        return ResponseEntity.ok(bookingService.accept(id, user));
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<BookingResponse> reject(
            @PathVariable Long id,
            @AuthenticationPrincipal User user){
        return ResponseEntity.ok(bookingService.reject(id, user));
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<BookingResponse> complete(
            @PathVariable Long id,
            @AuthenticationPrincipal User user){
        return ResponseEntity.ok(bookingService.complete(id, user));
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<BookingResponse> cancel(
            @PathVariable Long id,
            @AuthenticationPrincipal User user){
        return ResponseEntity.ok(bookingService.cancel(id, user));
    }

}
