package com.shareninsulares.booking;

import com.shareninsulares.booking.dto.BookingResponse;
import com.shareninsulares.booking.dto.CreateBookingRequest;
import com.shareninsulares.listing.Listing;
import com.shareninsulares.listing.ListingRepository;
import com.shareninsulares.listing.ListingStatus;
import com.shareninsulares.user.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingService {

    private final BookingRepository bookingRepository;
    private final ListingRepository listingRepository;

    public BookingResponse create(CreateBookingRequest request, User buyer){
        Listing listing = listingRepository.findById(request.getListingId())
                .orElseThrow(() -> new IllegalArgumentException("Listing not found"));

        if(listing.getSeller().getId().equals(buyer.getId())){
            throw new IllegalArgumentException("You cannot book your own listing");
        }

        if(listing.getStatus() != ListingStatus.ACTIVE){
            throw new IllegalArgumentException("This listing is not available for booking");
        }

        if(bookingRepository.existsByListingAndBuyerAndStatusIn(listing, buyer, List.of(BookingStatus.PENDING, BookingStatus.ACCEPTED))){
            throw new IllegalArgumentException("You already have an active booking for this listing");
        }

        Booking booking = Booking.builder()
                .listing(listing)
                .buyer(buyer)
                .message(request.getMessage())
                .build();

        return toResponse(bookingRepository.save(booking));
    }

    public List<BookingResponse> getMyBookings(User buyer){
        return bookingRepository.findByBuyer(buyer)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    public List<BookingResponse> getReceivedBookings(User seller){
        return bookingRepository.findByListingSeller(seller)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    //accept
    public BookingResponse accept(Long id, User seller){
        Booking booking = getOrThrow(id);
        assertSeller(booking, seller);
        assertStatus(booking, BookingStatus.PENDING);

        booking.setStatus(BookingStatus.ACCEPTED);
        booking.setUpdatedAt(LocalDateTime.now());

        Listing listing = booking.getListing();
        listing.setStatus(ListingStatus.RESERVED);
        listingRepository.save(listing);

        return toResponse(bookingRepository.save(booking));
    }

    //reject
    public BookingResponse reject(Long id, User seller){
        Booking booking = getOrThrow(id);
        assertSeller(booking, seller);
        assertStatus(booking, BookingStatus.PENDING);

        booking.setStatus(BookingStatus.REJECTED);
        booking.setUpdatedAt(LocalDateTime.now());

        return toResponse(bookingRepository.save(booking));
    }

    //complete
    public BookingResponse complete(Long id, User seller){
        Booking booking = getOrThrow(id);
        assertSeller(booking, seller);
        assertStatus(booking, BookingStatus.PENDING);

        booking.setStatus(BookingStatus.PENDING);
        booking.setUpdatedAt(LocalDateTime.now());

        Listing listing = booking.getListing();
        listing.setStatus(ListingStatus.SOLD);
        listingRepository.save(listing);

        return toResponse(bookingRepository.save(booking));
    }

    //cancel
    public BookingResponse cancel(Long id, User buyer){
        Booking booking = getOrThrow(id);
        assertBuyer(booking, buyer);

        if(booking.getStatus() != BookingStatus.PENDING
        && booking.getStatus() != BookingStatus.ACCEPTED){
            throw new IllegalArgumentException("Cannot cancel a booking with status: " + booking.getStatus());
        }

        if(booking.getStatus() == BookingStatus.ACCEPTED){
            Listing listing = booking.getListing();
            listing.setStatus(ListingStatus.ACTIVE);
            listingRepository.save(listing);
        }

        booking.setStatus(BookingStatus.CANCELLED);
        booking.setUpdatedAt(LocalDateTime.now());

        return toResponse(bookingRepository.save(booking));
    }

    private Booking getOrThrow(Long id){
        return bookingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
    }

    private void assertSeller(Booking booking, User user){
        if(!booking.getListing().getSeller().getId().equals(user.getId())){
            throw new IllegalArgumentException("You are not the seller of this listing");
        }
    }

    private void assertBuyer(Booking booking, User user){
        if(!booking.getBuyer().getId().equals(user.getId())){
            throw new IllegalArgumentException("You are not the buyer of this listing");
        }
    }

    private void assertStatus(Booking booking, BookingStatus expected){
        if(booking.getStatus() != expected){
            throw new IllegalArgumentException(
                    "Booking must be " + expected + " to perform this action");
        }
    }


    private BookingResponse toResponse(Booking booking) {
        return BookingResponse.builder()
                .id(booking.getId())
                .listingId(booking.getListing().getId())
                .listingTitle(booking.getListing().getTitle())
                .buyerId(booking.getBuyer().getId())
                .buyerName(booking.getBuyer().getFullName())
                .sellerId(booking.getListing().getSeller().getId())
                .sellerName(booking.getListing().getSeller().getFullName())
                .status(booking.getStatus())
                .message(booking.getMessage())
                .createdAt(booking.getCreatedAt())
                .updatedAt(booking.getUpdatedAt())
                .build();
    }

}
