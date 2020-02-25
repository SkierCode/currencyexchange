package ru.itstudy.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itstudy.domain.History;

import java.time.LocalDate;
import java.util.List;

public interface HistoryRepo extends JpaRepository<History, Long> {
    List<History> findByDateAndAndSourceCurrencyAndAndTargetCurrency(LocalDate localDate, String sourceCurrency, String targetCurrency);
}
