package com.example.currencyconverter.controller;

import com.example.currencyconverter.model.Conversion;
import com.example.currencyconverter.service.ConversionService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("api")
public class ConversionController {

    private final ConversionService conversionService;

    public ConversionController(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @PostMapping("/convert")
    public Conversion convert(@RequestParam BigDecimal amount, @RequestParam String currency){
       return conversionService.convert(amount,currency);
    }

    @GetMapping("/")
    public List<Conversion> history(){
        return conversionService.history();
    }
}
