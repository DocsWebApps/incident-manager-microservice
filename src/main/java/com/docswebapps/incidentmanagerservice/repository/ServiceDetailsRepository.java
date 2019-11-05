package com.docswebapps.incidentmanagerservice.repository;

import com.docswebapps.incidentmanagerservice.domain.ServiceDetails;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ServiceDetailsRepository extends PagingAndSortingRepository<ServiceDetails, Long> {
    Optional<ServiceDetails> findByServiceName(String serviceName);
}
