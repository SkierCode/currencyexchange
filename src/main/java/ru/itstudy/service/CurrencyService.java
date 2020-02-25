package ru.itstudy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.itstudy.domain.Currency;
import ru.itstudy.domain.History;
import ru.itstudy.domain.dto.Currencies;
import ru.itstudy.repo.CurrencyRepo;
import ru.itstudy.repo.HistoryRepo;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class CurrencyService {
    @Autowired
    CurrencyRepo currencyRepo;
    @Autowired
    private HistoryRepo historyRepo;


    @PostConstruct
    public void init() {
        updateRates();
    }

    private String getTodayRate(String id) {
        LocalDate today = LocalDate.now();
        Optional<Currency> currencyFromDb = currencyRepo.findById(id);

        if (currencyFromDb.get().getDate().equals(today)) {
            return currencyFromDb.get().getValue();
        } else {
            return updateRates()
                    .stream()
                    .filter(currency -> currency.getId().equals(id))
                    .findFirst()
                    .map(Currency::getValue)
                    .get();
        }
    }

    private List<Currency> updateRates() {
        LocalDate today = LocalDate.now();
        String formattedDate = today.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String curs = "http://www.cbr.ru/scripts/XML_daily.asp?date_req="+formattedDate;

        RestTemplate restTemplate = new RestTemplate();

        Currency rub = new Currency();
        rub.setId("R01555ub");
        rub.setName("Российский рубль");
        rub.setCharCode("RUB");
        rub.setNumCode("801");
        rub.setNominal(1);
        rub.setValue("1");

        Currencies currencies = restTemplate.getForObject(curs, Currencies.class);
        currencies.getCurrencies().add(rub);
        currencies.getCurrencies().sort(Comparator.comparing(Currency::getName));
        currencies.getCurrencies().forEach(currency -> {
            currency.setDate(today);
            currencyRepo.save(currency);
        });

        return currencies.getCurrencies();
    }

    public String getExchangeResult(Currency sourceCurrency, Currency targetCurrency, String firstVal) {
        firstVal = firstVal.replace(",", ".");

        if (!sourceCurrency.getDate().equals(LocalDate.now())) {
            String todayValue = getTodayRate(sourceCurrency.getId());
            sourceCurrency.setValue(todayValue);
        }

        if (!targetCurrency.getDate().equals(LocalDate.now())) {
            String todayValue = getTodayRate(targetCurrency.getId());
            targetCurrency.setValue(todayValue);
        }

        BigDecimal tmpCurs = new BigDecimal(targetCurrency.getValue().replace(",", "."))
                .divide(new BigDecimal(targetCurrency.getNominal()));

        BigDecimal result = new BigDecimal(firstVal)
                .multiply(new BigDecimal(sourceCurrency.getValue().replace(",", ".")))
                .divide(new BigDecimal(sourceCurrency.getNominal()))
                .divide(tmpCurs,10000, RoundingMode.HALF_UP)
                .setScale(2, RoundingMode.HALF_UP);

        String srcCurrency = sourceCurrency.getName() + "("+ sourceCurrency.getCharCode()+ ")";
        String trgCurrency = targetCurrency.getName() + "("+ targetCurrency.getCharCode()+ ")";

        historyRepo.save(new History(srcCurrency, trgCurrency, firstVal, String.valueOf(result), LocalDate.now()));

        return result.toString();
    }
}
