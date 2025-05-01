package com.gingerx.focusservice.repository;

import com.gingerx.focusservice.entity.AppDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppDetailRepository extends JpaRepository<AppDetail, Long>{
    boolean existsByAppId(String appId);

    Optional<AppDetail> findByAppId(String appId);
}
