package com.docswebapps.incidentmanagerservice.service.mappers;

import com.docswebapps.incidentmanagerservice.domain.IncidentDetails;
import com.docswebapps.incidentmanagerservice.web.model.IncidentDetailsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {DateMapper.class})
public interface IncidentDetailsMapper {
   @Mapping(target = "serviceDetails", ignore = true)
   @Mapping(target = "version", ignore = true)
   @Mapping(target = "incidentUpdates", ignore = true)
   @Mapping(target = "createdDate", ignore = true)
   @Mapping(target = "lastModifiedDate", ignore = true)
   @Mapping(target = "id", ignore = true)
   IncidentDetails dtoToEntity(IncidentDetailsDto incidentDetailsDto);

   @Mapping(target = "serviceName", source = "")
   IncidentDetailsDto entityToDto(IncidentDetails incidentDetails);
}
