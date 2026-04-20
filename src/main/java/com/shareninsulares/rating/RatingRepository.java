package com.shareninsulares.rating;

import com.shareninsulares.booking.Booking;
import com.shareninsulares.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    List<Rating> findByReviewed(User reviewed);
    boolean existsByBooking(Booking booking);

    @Query("SELECT AVG(r.score) FROM Rating r WHERE r.reviewed.id = :userId")
    Double findAverageScoreByReviewedId(@Param("userId") Long userId);

}
