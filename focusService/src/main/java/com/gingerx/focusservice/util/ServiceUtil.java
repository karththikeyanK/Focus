package com.gingerx.focusservice.util;

import com.gingerx.focusservice.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
@Slf4j
public class ServiceUtil {
    public static <T> T validateEntity(boolean condition, Supplier<T> entitySupplier, String entityName, Long entityId) {
        if (!condition) {
            log.error("{} with ID {} not found", entityName, entityId);
            throw new ResourceNotFoundException(entityName + " with ID " + entityId + " not found");
        }
        return entitySupplier.get();
    }
}
