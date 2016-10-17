package com.yimei.finance.entity.common;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "t_finance_number")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Number implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", length = 20)
    private Long id;
    @NonNull
    @Column(name = "type", length = 30)
    private String type;
    @NonNull
    @Column(name = "create_date")
    private LocalDate createDate;
}
