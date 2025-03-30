package com.example.ecommerce.mapper;

import com.example.ecommerce.domain.Flower;
import com.example.ecommerce.dto.HeaderResponse;
import com.example.ecommerce.dto.flower.FlowerRequest;
import com.example.ecommerce.dto.flower.FlowerResponse;
import com.example.ecommerce.dto.flower.FlowerSearchRequest;
import com.example.ecommerce.enums.SearchFlower;
import com.example.ecommerce.exception.InputFieldException;
import com.example.ecommerce.repository.projection.FlowerProjection;
import com.example.ecommerce.service.FlowerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FlowerMapper {

    private final CommonMapper commonMapper;
    private final FlowerService flowerService;

    public FlowerResponse getFlowerById(Long flowerId) {
        return commonMapper.convertToResponse(flowerService.getFlowerById(flowerId), FlowerResponse.class);
    }

    public List<FlowerResponse> getFlowersByIds(List<Long> flowerIds) {
        return commonMapper.convertToResponseList(flowerService.getFlowersByIds(flowerIds), FlowerResponse.class);
    }

    public HeaderResponse<FlowerResponse> getAllFlowers(Pageable pageable) {
        Page<FlowerProjection> flowers = flowerService.getAllFlowers(pageable);
        return commonMapper.getHeaderResponse(flowers.getContent(), flowers.getTotalPages(), flowers.getTotalElements(),
                FlowerResponse.class);
    }

    public HeaderResponse<FlowerResponse> findFlowersByFilterParams(FlowerSearchRequest filter, Pageable pageable) {
        Page<FlowerProjection> flowers = flowerService.findFlowersByFilterParams(filter, pageable);
        return commonMapper.getHeaderResponse(flowers.getContent(), flowers.getTotalPages(), flowers.getTotalElements(),
                FlowerResponse.class);
    }

    public List<FlowerResponse> findBySpecies(String species) {
        return commonMapper.convertToResponseList(flowerService.findBySpecies(species), FlowerResponse.class);
    }

    public List<FlowerResponse> findByColor(List<String> colors) {
        return commonMapper.convertToResponseList(flowerService.findByColor(colors), FlowerResponse.class);
    }

    public HeaderResponse<FlowerResponse> findByInputText(SearchFlower searchType, String text, Pageable pageable) {
        Page<FlowerProjection> flowers = flowerService.findByInputText(searchType, text, pageable);
        return commonMapper.getHeaderResponse(flowers.getContent(), flowers.getTotalPages(), flowers.getTotalElements(),
                FlowerResponse.class);
    }

    public FlowerResponse saveFlower(FlowerRequest flowerRequest, MultipartFile file, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InputFieldException(bindingResult);
        }
        Flower flower = commonMapper.convertToEntity(flowerRequest, Flower.class);
        return commonMapper.convertToResponse(flowerService.saveFlower(flower, file), FlowerResponse.class);
    }

    public String deleteFlower(Long flowerId) {
        return flowerService.deleteFlower(flowerId);
    }
}