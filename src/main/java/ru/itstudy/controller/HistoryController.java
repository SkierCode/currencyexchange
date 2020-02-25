package ru.itstudy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itstudy.domain.Currency;
import ru.itstudy.domain.History;
import ru.itstudy.repo.CurrencyRepo;
import ru.itstudy.repo.HistoryRepo;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Controller
public class HistoryController {
    @Autowired
    private CurrencyRepo currencyRepo;
    @Autowired
    private HistoryRepo historyRepo;
    private List<Currency> currencies;

    @GetMapping("/history")
    public String history(Model model) {
        currencies = currencyRepo.findAll();
        currencies.sort(Comparator.comparing(Currency::getName));

        model.addAttribute("currencies", currencies);

        return "history";
    }


    @PostMapping("/history")
    public String historyResult(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
            @RequestParam Currency sourceCurrency,
            @RequestParam Currency targetCurrency,
            Model model
    ) {
        String srcCurrency = sourceCurrency.getName() + "("+ sourceCurrency.getCharCode()+ ")";
        String trgCurrency = targetCurrency.getName() + "("+ targetCurrency.getCharCode()+ ")";

        List<History> histories = historyRepo.findByDateAndAndSourceCurrencyAndAndTargetCurrency(
                date,srcCurrency,trgCurrency
        );

        model.addAttribute("currencies", currencies);
        model.addAttribute("selected1", sourceCurrency);
        model.addAttribute("selected2", targetCurrency);
        model.addAttribute("histories", histories);

        return "history";
    }
}
