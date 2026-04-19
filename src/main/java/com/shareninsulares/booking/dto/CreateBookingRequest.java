package com.shareninsulares.booking.dto;

import com.shareninsulares.listing.Listing;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateBookingRequest {

    @NotNull(message = "Listing ID is required")
    private Long listingId;

    private String message;

}
