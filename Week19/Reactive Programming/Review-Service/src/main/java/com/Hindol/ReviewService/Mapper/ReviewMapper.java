package com.Hindol.ReviewService.Mapper;

import com.Hindol.ReviewService.DTO.ReviewDTO;
import com.Hindol.ReviewService.Entity.Review;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    Review dtoToEntity(ReviewDTO reviewDTO);
    ReviewDTO entityToDTO(Review review);
}
