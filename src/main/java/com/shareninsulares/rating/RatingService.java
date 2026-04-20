package com.shareninsulares.rating;

import com.shareninsulares.booking.Booking;
import com.shareninsulares.booking.BookingRepository;
import com.shareninsulares.booking.BookingStatus;
import com.shareninsulares.rating.dto.CreateRatingRequest;
import com.shareninsulares.rating.dto.RatingResponse;
import com.shareninsulares.user.User;
import com.shareninsulares.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RatingService {

    private final RatingRepository ratingRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    public RatingResponse create(CreateRatingRequest request, User reviewer){
        Booking booking = bookingRepository.findById(request.getBookingId())
                .orElseThrow(()-> new IllegalArgumentException("Booking not found"));

        if(!booking.getBuyer().getId().equals(reviewer.getId())){
            throw new IllegalArgumentException("Only the buyer can rate this booking");
        }

        if(booking.getStatus() != BookingStatus.COMPLETED){
            throw new IllegalArgumentException("Can only rate a completed booking");
        }

        if(ratingRepository.existsByBooking(booking)){
            throw new IllegalArgumentException("You have already rated this booking");
        }

        User reviewed = booking.getListing().getSeller();

        Rating rating = Rating.builder()
                .booking(booking)
                .reviewer(reviewer)
                .reviewed(reviewed)
                .score(request.getScore())
                .comment(request.getComment())
                .build();

        ratingRepository.save(rating);

        Double avg = ratingRepository.findAverageScoreByReviewedId(reviewed.getId());
        if(avg != null){
            reviewed.setReputationScore(
                    BigDecimal.valueOf(avg).setScale(2, RoundingMode.HALF_UP));
            userRepository.save(reviewed);
        }

        return toResponse(rating);

    }

    public List<RatingResponse> getRatingsForUser(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new IllegalArgumentException("User not found"));
        return ratingRepository.findByReviewed(user)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }


    private RatingResponse toResponse(Rating rating) {
        return RatingResponse.builder()
                .id(rating.getId())
                .bookingId(rating.getBooking().getId())
                .reviewerId(rating.getReviewer().getId())
                .reviewerName(rating.getReviewer().getFullName())
                .reviewedId(rating.getReviewed().getId())
                .reviewedName(rating.getReviewed().getFullName())
                .score(rating.getScore())
                .comment(rating.getComment())
                .createdAt(rating.getCreatedAt())
                .build();
    }
}
