package ru.itstudy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;
import ru.itstudy.domain.Currency;
import ru.itstudy.domain.History;
import ru.itstudy.repo.CurrencyRepo;
import ru.itstudy.repo.HistoryRepo;
import ru.itstudy.service.CurrencyService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    private CurrencyRepo currencyRepo;
    @Autowired
    private CurrencyService currencyService;
    private List<Currency> currencies;

    @GetMapping("/")
    public String greeting() {
        return "redirect:/hello";
    }

    @GetMapping("/hello")
    public String hello(Model model) {
        currencies = currencyRepo.findAll();
        currencies.sort(Comparator.comparing(Currency::getName));
        model.addAttribute("currencies", currencies);
        return "main";
    }

    @PostMapping("/calculate")
    public String calculate(
            @RequestParam Currency sourceCurrency,
            @RequestParam Currency targetCurrency,
            @RequestParam String firstVal, Model model
    ) {
        if (StringUtils.isEmpty(firstVal) || !firstVal.matches("^[0-9]*[.,]?[0-9]+$")) {
            return "redirect:/hello";
        }

        String result = currencyService.getExchangeResult(sourceCurrency, targetCurrency, firstVal);

        model.addAttribute("currencies", currencies);
        model.addAttribute("selected1", sourceCurrency);
        model.addAttribute("selected2", targetCurrency);
        model.addAttribute("firstVal", firstVal);
        model.addAttribute("result", result);

        return "main";
    }

}
