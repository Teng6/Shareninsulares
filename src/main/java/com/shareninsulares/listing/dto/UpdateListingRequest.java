package com.shareninsulares.listing.dto;

import com.shareninsulares.listing.ListingStatus;
import com.shareninsulares.listing.ListingType;

import java.math.BigDecimal;

public class UpdateListingRequest {
    private String title, description, category, imageurl;
    private BigDecimal price;
    private ListingType type;
    private ListingStatus status;
}
