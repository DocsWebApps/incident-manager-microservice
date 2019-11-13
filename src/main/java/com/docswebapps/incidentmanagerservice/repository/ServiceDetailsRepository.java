package com.docswebapps.incidentmanagerservice.repository;

import com.docswebapps.incidentmanagerservice.domain.ServiceDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServiceDetailsRepository extends JpaRepository<ServiceDetails, Long> {
    Optional<ServiceDetails> findByServiceName(String serviceName);
}
