package com.example.currencyconverter.service;

import com.example.currencyconverter.model.Conversion;
import com.example.currencyconverter.repository.ConversionRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class ConversionService {

    private final ConversionRepository conversionRepository;
    private final RestTemplate restTemplate;

    public ConversionService(ConversionRepository conversionRepository, RestTemplate restTemplate) {
        this.conversionRepository = conversionRepository;
        this.restTemplate = restTemplate;
    }

    public Conversion convert(BigDecimal usd, String currency){
        Map response = restTemplate.getForObject("https://open.er-api.com/v6/latest/USD", Map.class);

        Map<String, Object> rates = (Map<String, Object>) response.get("rates");
        BigDecimal rate = new BigDecimal(rates.get("USD").toString());
        BigDecimal converted = usd.multiply(rate);

        Conversion c = new Conversion();
        c.setConvertedAmount(usd);
        c.setTargetCurrency(currency);
        c.setRate(rate);
        c.setConvertedAmount(converted);
        c.setCreatedAt(LocalDateTime.now());

        return conversionRepository.save(c);
    }

    public List<Conversion> history() {
       return conversionRepository.findAll();
    }
}
