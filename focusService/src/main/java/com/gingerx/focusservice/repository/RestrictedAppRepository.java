package com.gingerx.focusservice.repository;

import com.gingerx.focusservice.entity.RestrictedApp;
import com.gingerx.focusservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestrictedAppRepository extends JpaRepository<RestrictedApp, Long>{
    boolean existsByAppNameAndUser(String appName, User user);

    List<RestrictedApp> findAllByUser(User user);
}
