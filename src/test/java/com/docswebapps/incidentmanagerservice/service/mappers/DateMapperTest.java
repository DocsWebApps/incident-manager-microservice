package com.docswebapps.incidentmanagerservice.service.mappers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DateMapperTest {

    DateMapper dateMapper = new DateMapper();

    @Test
    @DisplayName("asOffsetDateTime()")
    void asOffsetDateTime() {
        OffsetDateTime offsetDateTime = dateMapper.asOffsetDateTime(Timestamp.valueOf(LocalDateTime.now()));
        assertNotNull(offsetDateTime);
        assertTrue(offsetDateTime instanceof OffsetDateTime);
    }

    @Test
    @DisplayName("asTimestamp()")
    void asTimestamp() {
        Timestamp timestamp = dateMapper.asTimestamp(OffsetDateTime.now(ZoneId.systemDefault()));
        assertNotNull(timestamp);
        assertTrue(timestamp instanceof Timestamp);
    }

}
