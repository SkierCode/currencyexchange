package ru.itstudy.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter @Setter
@Table(name = "history")
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String sourceCurrency;
    private String targetCurrency;
    private String originalAmount;
    private String receivedAmount;
    private LocalDate date;

    public History(String sourceCurrency, String targetCurrency, String originalAmount, String receivedAmount, LocalDate date) {
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
        this.originalAmount = originalAmount;
        this.receivedAmount = receivedAmount;
        this.date = date;
    }
}
