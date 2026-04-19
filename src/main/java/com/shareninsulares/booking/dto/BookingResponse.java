package com.shareninsulares.booking.dto;

import com.shareninsulares.booking.BookingStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BookingResponse {

    private Long id;
    private Long listingId;
    private String listingTitle;
    private Long buyerId;
    private String buyerName;
    private Long sellerId;
    private String sellerName;
    private BookingStatus status;
    private String message;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
