package com.tmp.springtemplate.database.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@EqualsAndHashCode
@NoArgsConstructor
@Table(name="DEMO_TABLE")
public class DemoEntity {

    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "TEXT", length = 128)
    private String text;

    @Column(name = "FLAG")
    private Boolean flag;

    @Column(name = "NUM_VALUE")
    private Long numValue;

    @Column(name = "DATE_VALUE")
    private LocalDate dateValue;

    @Column(name = "DATETIME_VALUE")
    private LocalDateTime datetimeValue;

}
