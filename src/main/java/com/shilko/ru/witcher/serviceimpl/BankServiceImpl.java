package com.shilko.ru.witcher.serviceimpl;

import com.shilko.ru.witcher.service.BankService;
import org.springframework.stereotype.Service;

@Service
public class BankServiceImpl implements BankService {

    private static final String ACCOUNT_NUMBER;
    private static final String AMOUNT;
    private static final String COMMENT;

    static {
        ACCOUNT_NUMBER = "79500186016";
        AMOUNT = "100";
        COMMENT = "Thank%27s%20for%20witcher%20project";
    }

    @Override
    public String getRedirectOnQiwiPayment() {
        return "https://qiwi.com/payment/form/" +
                "99?extra%5B%27account%27%5D=" + ACCOUNT_NUMBER +
                "&amountInteger=" + AMOUNT +
                "&extra%5B%27comment%27%5D=" + COMMENT +
                "&currency=643&blocked[0]=account&blocked[1]=comment";
    }
}
