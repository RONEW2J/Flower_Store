package com.example.ecommerce.repository;

import com.example.ecommerce.domain.Flower;
import com.example.ecommerce.repository.projection.FlowerProjection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlowerRepository extends JpaRepository<Flower, Long> {

        List<FlowerProjection> findAllByOrderByIdAsc();

        @Query("SELECT flower FROM Flower flower ORDER BY flower.id ASC")
        Page<FlowerProjection> findAllByOrderByIdAsc(Pageable pageable);

        List<Flower> findBySpeciesOrderByPriceDesc(String species);

        List<Flower> findByColorOrderByPriceDesc(String color);

        List<Flower> findByIdIn(List<Long> flowerIds);

        @Query("SELECT flower FROM Flower flower WHERE flower.id IN :flowerIds")
        List<FlowerProjection> getFlowersByIds(List<Long> flowerIds);

        @Query("SELECT flower FROM Flower flower " +
                        "WHERE (coalesce(:species, null) IS NULL OR flower.species IN :species) " +
                        "AND (coalesce(:colors, null) IS NULL OR flower.color IN :colors) " +
                        "AND (coalesce(:bloomingSeason, null) IS NULL OR flower.bloomingSeason = :bloomingSeason) " +
                        "AND (coalesce(:priceStart, null) IS NULL OR flower.price BETWEEN :priceStart AND :priceEnd) " +
                        "ORDER BY CASE WHEN :sortByPrice = true THEN flower.price ELSE -flower.price END ASC")
        Page<FlowerProjection> findFlowersByFilterParams(
                        List<String> species,
                        List<String> colors,
                        String bloomingSeason,
                        Integer priceStart,
                        Integer priceEnd,
                        boolean sortByPrice,
                        Pageable pageable);

        @Query("SELECT flower FROM Flower flower " +
                        "WHERE UPPER(flower.species) LIKE UPPER(CONCAT('%',:text,'%')) " +
                        "ORDER BY flower.price DESC")
        Page<FlowerProjection> findBySpecies(String text, Pageable pageable);

        @Query("SELECT flower FROM Flower flower " +
                        "WHERE UPPER(flower.flowerName) LIKE UPPER(CONCAT('%',:text,'%')) " +
                        "ORDER BY flower.price DESC")
        Page<FlowerProjection> findByFlowerName(String text, Pageable pageable);

        @Query("SELECT flower FROM Flower flower " +
                        "WHERE UPPER(flower.bloomingSeason) LIKE UPPER(CONCAT('%',:text,'%')) " +
                        "ORDER BY flower.price DESC")
        Page<FlowerProjection> findByBloomingSeason(@Param("text") String text, Pageable pageable);

        @Query("SELECT flower FROM Flower flower " +
                        "WHERE (coalesce(:bloomingSeasons, null) IS NULL OR flower.bloomingSeason IN :bloomingSeasons) "
                        +
                        "ORDER BY flower.price DESC")
        List<Flower> findFlowersByBloomingSeasons(@Param("bloomingSeasons") List<String> bloomingSeasons);

        List<Flower> findByColorIn(List<String> colors);
}
