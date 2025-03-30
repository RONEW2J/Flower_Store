package com.example.ecommerce.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.ecommerce.dto.GraphQLRequest;
import com.example.ecommerce.dto.HeaderResponse;
import com.example.ecommerce.dto.flower.FlowerResponse;
import com.example.ecommerce.dto.flower.FlowerSearchRequest;
import com.example.ecommerce.dto.flower.SearchTypeRequest;
import com.example.ecommerce.mapper.FlowerMapper;
import com.example.ecommerce.service.graphql.GraphQLProvider;

import graphql.ExecutionResult;
import lombok.RequiredArgsConstructor;

import static com.example.ecommerce.constants.PathConstants.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_V1_FLOWERS)
public class FlowerController {

    private final FlowerMapper flowerMapper;
    private final GraphQLProvider graphQLProvider;

    @GetMapping
    public ResponseEntity<List<FlowerResponse>> getAllFlowers(@PageableDefault(size = 15) Pageable pageable) {
        HeaderResponse<FlowerResponse> response = flowerMapper.getAllFlowers(pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @GetMapping(FLOWER_ID)
    public ResponseEntity<FlowerResponse> getFlowerById(@PathVariable Long flowerId) {
        return ResponseEntity.ok(flowerMapper.getFlowerById(flowerId));
    }

    @PostMapping(IDS)
    public ResponseEntity<List<FlowerResponse>> getFlowersByIds(@RequestBody List<Long> flowerIds) {
        return ResponseEntity.ok(flowerMapper.getFlowersByIds(flowerIds));
    }

    @PostMapping(SEARCH)
    public ResponseEntity<List<FlowerResponse>> findFlowersByFilterParams(@RequestBody FlowerSearchRequest filter,
            @PageableDefault(size = 15) Pageable pageable) {
        HeaderResponse<FlowerResponse> response = flowerMapper.findFlowersByFilterParams(filter, pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @PostMapping(SEARCH_COLOR)
    public ResponseEntity<List<FlowerResponse>> findByColor(@RequestBody FlowerSearchRequest filter) {
        return ResponseEntity.ok(flowerMapper.findByColor(filter.getColors()));
    }

    @PostMapping(SEARCH_SPECIES)
    public ResponseEntity<List<FlowerResponse>> findBySpecies(@RequestBody FlowerSearchRequest filter) {
        return ResponseEntity.ok(flowerMapper.findBySpecies(filter.getSpecies()));
    }

    @PostMapping(SEARCH_TEXT)
    public ResponseEntity<List<FlowerResponse>> findByInputText(@RequestBody SearchTypeRequest searchType,
            @PageableDefault(size = 15) Pageable pageable) {
        HeaderResponse<FlowerResponse> response = flowerMapper.findByInputText(searchType.getSearchType(),
                searchType.getText(), pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @PostMapping(GRAPHQL_IDS)
    public ResponseEntity<ExecutionResult> getFlowersByIdsQuery(@RequestBody GraphQLRequest request) {
        return ResponseEntity.ok(graphQLProvider.getGraphQL().execute(request.getQuery()));
    }

    @PostMapping(GRAPHQL_FLOWERS)
    public ResponseEntity<ExecutionResult> getAllFlowersByQuery(@RequestBody GraphQLRequest request) {
        return ResponseEntity.ok(graphQLProvider.getGraphQL().execute(request.getQuery()));
    }

    @PostMapping(GRAPHQL_FLOWER)
    public ResponseEntity<ExecutionResult> getFlowerByQuery(@RequestBody GraphQLRequest request) {
        return ResponseEntity.ok(graphQLProvider.getGraphQL().execute(request.getQuery()));
    }
}
