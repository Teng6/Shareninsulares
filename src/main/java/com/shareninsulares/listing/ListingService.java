package com.shareninsulares.listing;

import com.shareninsulares.listing.dto.CreateListingRequest;
import com.shareninsulares.listing.dto.ListingResponse;
import com.shareninsulares.listing.dto.UpdateListingRequest;
import com.shareninsulares.user.User;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ListingService {


    private final ListingRepository listingRepository;

    public ListingResponse create(CreateListingRequest request, User seller){
        Listing listing = Listing.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .price(request.getPrice())
                .category(request.getCategory())
                .type(request.getType())
                .status(ListingStatus.ACTIVE)
                .campus(seller.getCampus())
                .imageUrl(request.getImageUrl())
                .seller(seller)
                .build();

        return toResponse(listingRepository.save(listing));
    }

    public List<ListingResponse> getAll(String campus, String category){
        List<Listing> listings;

        if(campus != null && category != null){
            listings = listingRepository.findByStatusAndCampusAndCategory(ListingStatus.ACTIVE, campus, category);
        }else if(campus != null){
            listings = listingRepository.findByStatusAndCampus(ListingStatus.ACTIVE, campus);
        }else if(category != null){
            listings = listingRepository.findByStatusAndCategory(ListingStatus.ACTIVE, category);
        }else{
            listings = listingRepository.findByStatus(ListingStatus.ACTIVE);
        }

        return listings.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public ListingResponse getById(Long id){
        Listing listing = listingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Listing not found"));
        return toResponse(listing);
    }

    public List<ListingResponse> getMylistings(User seller){
        return listingRepository.findBySeller(seller)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    public ListingResponse update(Long id, UpdateListingRequest request, User currentUser){
        Listing listing = listingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Listing not found"));

        if(!listing.getSeller().getId().equals(currentUser.getId())){
            throw new IllegalArgumentException("You do not own this listing");
        }

        if (request.getTitle() != null) listing.setTitle(request.getTitle());
        if (request.getDescription() != null) listing.setDescription(request.getDescription());
        if (request.getPrice() != null) listing.setPrice(request.getPrice());
        if (request.getCategory() != null) listing.setCategory(request.getCategory());
        if (request.getType() != null) listing.setType(request.getType());
        if (request.getStatus() != null) listing.setStatus(request.getStatus());
        if (request.getImageUrl() != null) listing.setImageUrl(request.getImageUrl());

        listing.setUpdatedAt(LocalDateTime.now());

        return toResponse(listingRepository.save(listing));
    }

    public void delete(Long id, User currentUser){
        Listing listing = listingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Listing not found"));

        if(!listing.getSeller().getId().equals(currentUser.getId())){
            throw new IllegalArgumentException("You do not own this listing");
        }

        listingRepository.delete(listing);
    }


    private ListingResponse toResponse(Listing listing) {
        return ListingResponse.builder()
                .id(listing.getId())
                .title(listing.getTitle())
                .description(listing.getDescription())
                .price(listing.getPrice())
                .category(listing.getCategory())
                .type(listing.getType())
                .status(listing.getStatus())
                .campus(listing.getCampus())
                .imageUrl(listing.getImageUrl())
                .sellerId(listing.getSeller().getId())
                .sellerName(listing.getSeller().getFullName())
                .sellerReputationScore(listing.getSeller().getReputationScore())
                .createdAt(listing.getCreatedAt())
                .updatedAt(listing.getUpdatedAt())
                .build();
    }
}
