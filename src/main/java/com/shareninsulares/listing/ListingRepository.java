package com.shareninsulares.listing;

import com.shareninsulares.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ListingRepository extends JpaRepository<Listing, Long> {
    List<Listing> findBySeller(User seller);
    List<Listing> findByStatus(ListingStatus status);
    List<Listing> findByStatusAndCampus(ListingStatus status, String campus);
    List<Listing> findByStatusAndCategory(ListingStatus status, String category);
    List<Listing> findByStatusAndCampusAndCategory(ListingStatus status, String campus, String category);
}
