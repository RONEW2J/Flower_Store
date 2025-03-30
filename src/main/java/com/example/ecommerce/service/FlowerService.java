package com.example.ecommerce.service;

import com.example.ecommerce.domain.Flower;
import com.example.ecommerce.dto.flower.FlowerSearchRequest;
import com.example.ecommerce.enums.SearchFlower;
import com.example.ecommerce.repository.projection.FlowerProjection;
import graphql.schema.DataFetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FlowerService {

    Flower getFlowerById(Long flowerId);

    Page<FlowerProjection> getAllFlowers(Pageable pageable);

    List<FlowerProjection> getFlowersByIds(List<Long> flowerIds);

    Page<FlowerProjection> findFlowersByFilterParams(FlowerSearchRequest filter, Pageable pageable);

    List<Flower> findBySpecies(String species);

    List<Flower> findByColor(List<String> colors);

    List<Flower> findFlowersByBloomingSeasons(List<String> bloomingSeasons);

    Page<FlowerProjection> findByInputText(SearchFlower searchType, String text, Pageable pageable);

    Flower saveFlower(Flower flower, MultipartFile file);

    String deleteFlower(Long flowerId);

    DataFetcher<Flower> getFlowerByQuery();

    DataFetcher<List<FlowerProjection>> getAllFlowersByQuery();

    DataFetcher<List<Flower>> getAllFlowersByIdsQuery();
}
