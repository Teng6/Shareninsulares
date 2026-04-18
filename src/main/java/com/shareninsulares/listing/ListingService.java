package com.shareninsulares.listing;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListingService {


    private final ListingRepository listingRepository;
}
