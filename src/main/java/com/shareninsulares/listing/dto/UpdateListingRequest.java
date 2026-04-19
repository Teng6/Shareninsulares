package com.shareninsulares.listing.dto;

import com.shareninsulares.listing.ListingStatus;
import com.shareninsulares.listing.ListingType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateListingRequest {
    private String title;
    private String description;
    private BigDecimal price;
    private String category;
    private ListingType type;
    private ListingStatus status;
    private String imageUrl;
}