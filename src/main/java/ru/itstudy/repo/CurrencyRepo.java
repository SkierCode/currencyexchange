package ru.itstudy.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itstudy.domain.Currency;

public interface CurrencyRepo extends JpaRepository<Currency, String> {
}
