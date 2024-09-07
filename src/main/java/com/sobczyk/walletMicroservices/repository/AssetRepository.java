package com.sobczyk.walletMicroservices.repository;

import com.sobczyk.walletMicroservices.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetRepository extends JpaRepository<Asset, String> {
}
