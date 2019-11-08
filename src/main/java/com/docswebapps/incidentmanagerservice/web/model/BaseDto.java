package com.docswebapps.incidentmanagerservice.web.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Null;
import java.time.OffsetDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
class BaseDto {
    @Null
    private Long id;

    @Null
    private OffsetDateTime createdDate;

    @Null
    private OffsetDateTime lastModifiedDate;
}
