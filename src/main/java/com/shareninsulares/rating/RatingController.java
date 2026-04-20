package com.shareninsulares.rating;


import com.shareninsulares.rating.dto.CreateRatingRequest;
import com.shareninsulares.rating.dto.RatingResponse;
import com.shareninsulares.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @PostMapping
    public ResponseEntity<RatingResponse> create(
            @Valid @RequestBody CreateRatingRequest request,
            @AuthenticationPrincipal User user){
        return ResponseEntity.ok(ratingService.create(request, user));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RatingResponse>> getRatingsForUser(
            @PathVariable Long userId){
        return ResponseEntity.ok(ratingService.getRatingsForUser(userId));
    }

}
