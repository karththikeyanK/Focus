package com.gingerx.focusservice.repository;

import com.gingerx.focusservice.entity.App;
import com.gingerx.focusservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestrictedAppRepository extends JpaRepository<App, Long>{
    boolean existsByAppNameAndUser(String appName, User user);

    List<App> findAllByUser(User user);
}
