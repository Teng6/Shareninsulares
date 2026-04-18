package com.shareninsulares.listing.dto;


import com.shareninsulares.listing.ListingStatus;
import com.shareninsulares.listing.ListingType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class ListingResponse {
    private Long id;
    private String title;
    private String description;
    private BigDecimal price;
    private String category;
    private ListingType type;
    private ListingStatus status;
    private String campus;
    private String imageUrl;
    private Long sellerId;
    private String sellerName;
    private BigDecimal sellerReputationScore;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
