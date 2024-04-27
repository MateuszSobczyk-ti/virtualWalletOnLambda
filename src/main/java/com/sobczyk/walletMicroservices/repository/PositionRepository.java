package com.sobczyk.walletMicroservices.repository;

import com.sobczyk.walletMicroservices.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PositionRepository extends JpaRepository<Position, Long> {

    @Query(value = "select p from Position p where p.asset.ticker = :ticker and p.closed_at is null")
    Optional<Position> getPostionByTicker(String ticker);
}
