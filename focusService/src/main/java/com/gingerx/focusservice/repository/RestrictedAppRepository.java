package com.gingerx.focusservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gingerx.focusservice.entity.App;
import com.gingerx.focusservice.entity.User;

@Repository
public interface RestrictedAppRepository extends JpaRepository<App, Long>{
    boolean existsByAppNameAndUser(String appName, User user);

    List<App> findAllByUser(User user);

    boolean existsByUserIdAndAppId(Long userId, String appId);
}
