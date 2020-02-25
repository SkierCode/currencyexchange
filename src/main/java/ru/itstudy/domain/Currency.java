package ru.itstudy.domain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "money")
@JacksonXmlRootElement(localName = "Item")
public class Currency {
    @Id
    @JacksonXmlProperty(isAttribute = true, localName = "ID")
    private String id;
    @JacksonXmlProperty(localName = "NumCode")
    private String numCode;
    @JacksonXmlProperty(localName = "CharCode")
    private String charCode;
    @JacksonXmlProperty(localName = "Nominal")
    private int nominal;
    @JacksonXmlProperty(localName = "Name")
    private String name;
    @JacksonXmlProperty(localName = "Value")
    private String value;
    @JacksonXmlProperty(isAttribute = true, localName = "Date")
    private LocalDate date;
}
