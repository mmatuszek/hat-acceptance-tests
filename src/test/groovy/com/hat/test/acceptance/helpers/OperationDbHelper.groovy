package com.hat.test.acceptance.helpers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component

import java.sql.ResultSet

@Component
class OperationDbHelper {

    @Autowired
    JdbcTemplate jdbcTemplate

    OperationRecord getOperation(String id) {
        jdbcTemplate.queryForObject("SELECT id, date, description, amount, type FROM operations WHERE id = ?",
                [id] as Object[],
                { ResultSet rs, int rowNum ->
                    OperationRecord record = new OperationRecord()
                    record.id = rs.getString("id")
                    record.date = rs.getDate("date")
                    record.description = rs.getString("description")
                    record.amount = rs.getBigDecimal("amount")
                    record.type = rs.getString("type")
                    record
                })
    }

    static class OperationRecord {

        String id
        Date date
        String description
        BigDecimal amount
        String type
    }
}
