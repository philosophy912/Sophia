package com.philosophy.summary;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @author lizhe
 * @date 2020/5/25 17:49
 **/
@Setter
@Getter
public class Exchange {
    private String name;
    private LocalDate exchangeDate;
    private Double amount;
    private ExchangeType type;
}
