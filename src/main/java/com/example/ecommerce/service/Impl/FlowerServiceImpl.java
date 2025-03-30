package com.example.ecommerce.service.Impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.ecommerce.domain.Flower;
import com.example.ecommerce.dto.flower.FlowerSearchRequest;
import com.example.ecommerce.enums.SearchFlower;
import com.example.ecommerce.exception.ApiRequestException;
import com.example.ecommerce.repository.FlowerRepository;
import com.example.ecommerce.repository.projection.FlowerProjection;
import com.example.ecommerce.service.FlowerService;
import graphql.schema.DataFetcher;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.example.ecommerce.constants.ErrorMessage.FLOWER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class FlowerServiceImpl implements FlowerService {

    private final FlowerRepository flowerRepository;
    private final AmazonS3 amazonS3client;

    @Value("${amazon.s3.bucket.name}")
    private String bucketName;

    @Override
    public Flower getFlowerById(Long flowerId) {
        return flowerRepository.findById(flowerId)
                .orElseThrow(() -> new ApiRequestException(FLOWER_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    @Override
    public Page<FlowerProjection> getAllFlowers(Pageable pageable) {
        return flowerRepository.findAllByOrderByIdAsc(pageable);
    }

    @Override
    public List<FlowerProjection> getFlowersByIds(List<Long> flowerIds) {
        return flowerRepository.getFlowersByIds(flowerIds);
    }

    @Override
    public Page<FlowerProjection> findFlowersByFilterParams(FlowerSearchRequest filter, Pageable pageable) {
        List<String> speciesList = filter.getSpecies() != null ? List.of(filter.getSpecies()) : null;

        return flowerRepository.findFlowersByFilterParams(
                speciesList,
                filter.getColors(),
                filter.getBloomingSeason(),
                filter.getPrices().get(0),
                filter.getPrices().get(1),
                filter.getSortByPrice(),
                pageable);
    }

    @Override
    public List<Flower> findBySpecies(String species) {
        return flowerRepository.findBySpeciesOrderByPriceDesc(species);
    }

    @Override
    public List<Flower> findByColor(List<String> colors) {
        return flowerRepository.findByColorIn(colors);
    }

    @Override
    public List<Flower> findFlowersByBloomingSeasons(List<String> bloomingSeasons) {
        return flowerRepository.findFlowersByBloomingSeasons(bloomingSeasons);
    }

    @Override
    public Page<FlowerProjection> findByInputText(SearchFlower searchType, String text, Pageable pageable) {
        if (searchType.equals(SearchFlower.SPECIES)) {
            return flowerRepository.findBySpecies(text, pageable);
        } else if (searchType.equals(SearchFlower.FLOWER_NAME)) {
            return flowerRepository.findByFlowerName(text, pageable);
        } else {
            return flowerRepository.findByBloomingSeason(text, pageable);
        }
    }

    @Override
    @Transactional
    public Flower saveFlower(Flower flower, MultipartFile multipartFile) {
        if (multipartFile == null) {
            flower.setImageFilename(amazonS3client.getUrl(bucketName, "empty.jpg").toString());
        } else {
            File file = new File(multipartFile.getOriginalFilename());
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(multipartFile.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            String fileName = UUID.randomUUID().toString() + "." + multipartFile.getOriginalFilename();
            amazonS3client.putObject(new PutObjectRequest(bucketName, fileName, file));
            flower.setImageFilename(amazonS3client.getUrl(bucketName, fileName).toString());
            file.delete();
        }
        return flowerRepository.save(flower);
    }

    @Override
    @Transactional
    public String deleteFlower(Long flowerId) {
        Flower flower = flowerRepository.findById(flowerId)
                .orElseThrow(() -> new ApiRequestException(FLOWER_NOT_FOUND, HttpStatus.NOT_FOUND));
        flowerRepository.delete(flower);
        return "Flower deleted successfully";
    }

    @Override
    public DataFetcher<Flower> getFlowerByQuery() {
        return dataFetchingEnvironment -> {
            Long flowerId = Long.parseLong(dataFetchingEnvironment.getArgument("id"));
            return flowerRepository.findById(flowerId).get();
        };
    }

    @Override
    public DataFetcher<List<FlowerProjection>> getAllFlowersByQuery() {
        return dataFetchingEnvironment -> flowerRepository.findAllByOrderByIdAsc();
    }

    @Override
    public DataFetcher<List<Flower>> getAllFlowersByIdsQuery() {
        return dataFetchingEnvironment -> {
            List<String> objects = dataFetchingEnvironment.getArgument("ids");
            List<Long> flowerIds = objects.stream()
                    .map(Long::parseLong)
                    .collect(Collectors.toList());
            return flowerRepository.findByIdIn(flowerIds);
        };
    }
}