package com.shareninsulares.listing;


import com.shareninsulares.listing.dto.CreateListingRequest;
import com.shareninsulares.listing.dto.ListingResponse;
import com.shareninsulares.listing.dto.UpdateListingRequest;
import com.shareninsulares.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/listings")
@RequiredArgsConstructor
public class ListingController {

    private final ListingService listingService;

    @PostMapping
    public ResponseEntity<ListingResponse> create(
            @Valid @RequestBody CreateListingRequest request,
            @AuthenticationPrincipal User user){
        return ResponseEntity.ok(listingService.create(request, user));
    }

    @GetMapping
    public ResponseEntity<List<ListingResponse>> getAll(
            @RequestParam(required = false) String campus,
            @RequestParam(required = false) String category){
        return ResponseEntity.ok(listingService.getAll(campus, category));
    }

    @GetMapping("/my")
    public ResponseEntity<List<ListingResponse>> getMyListings(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(listingService.getMylistings(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListingResponse> getById(@PathVariable Long id){
        return ResponseEntity.ok(listingService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ListingResponse> update(
            @PathVariable Long id,
            @RequestBody UpdateListingRequest request,
            @AuthenticationPrincipal User user){
        return ResponseEntity.ok(listingService.update(id, request, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @AuthenticationPrincipal User user){
        listingService.delete(id, user);
        return ResponseEntity.noContent().build();
    }

}
