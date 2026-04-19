package com.shareninsulares.booking;


import com.shareninsulares.listing.Listing;
import com.shareninsulares.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Book;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByBuyer(User buyer);
    List<Booking> findByListingSeller(User seller);
    boolean existsByListingAndBuyerAndStatusIn(Listing listing, User buyer, List<BookingStatus> statuses);
}
