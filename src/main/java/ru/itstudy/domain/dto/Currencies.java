package ru.itstudy.domain.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;
import lombok.Setter;
import ru.itstudy.domain.Currency;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@JacksonXmlRootElement(localName = "ValCurs")
public class Currencies {
    @JacksonXmlProperty(localName = "Valute")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Currency> currencies = new ArrayList<>();
}
