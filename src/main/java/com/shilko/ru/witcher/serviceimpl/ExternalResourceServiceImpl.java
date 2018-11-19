package com.shilko.ru.witcher.serviceimpl;

import com.shilko.ru.witcher.service.ExternalResourceService;
import org.springframework.stereotype.Service;

@Service
public class ExternalResourceServiceImpl implements ExternalResourceService {

    private static final String ID_TOKEN;
    private static final String ACCOUNT_NUMBER;
    private static final String AMOUNT;
    private static final String COMMENT;

    static {
        ID_TOKEN = "51000500";
        ACCOUNT_NUMBER = "79500186016";
        AMOUNT = "100";
        COMMENT = "Thank%27s%20for%20witcher%20project";
    }

    @Override
    public String getAnalyticsRedirect() {
        return getRedirectYandexMetrika();
    }

    @Override
    public String getAnalyticsResource() {
        return getAnalyticsYandexResource();
    }

    @Override
    public String getBankRedirect() {
        return getRedirectOnQiwiPayment();
    }

    private String getRedirectYandexMetrika() {
        return "https://metrika.yandex.ru/dashboard?id=" + ID_TOKEN;
    }

    private String getAnalyticsYandexResource() {
        return "<!-- Yandex.Metrika counter -->\n" +
                "<script type=\"text/javascript\" >\n" +
                "    (function (d, w, c) {\n" +
                "        (w[c] = w[c] || []).push(function() {\n" +
                "            try {\n" +
                "                w.yaCounter" + ID_TOKEN + " = new Ya.Metrika2({\n" +
                "                    id:" + ID_TOKEN + ",\n" +
                "                    clickmap:true,\n" +
                "                    trackLinks:true,\n" +
                "                    accurateTrackBounce:true,\n" +
                "                    webvisor:true\n" +
                "                });\n" +
                "            } catch(e) { }\n" +
                "        });\n" +
                "\n" +
                "        var n = d.getElementsByTagName(\"script\")[0],\n" +
                "            s = d.createElement(\"script\"),\n" +
                "            f = function () { n.parentNode.insertBefore(s, n); };\n" +
                "        s.type = \"text/javascript\";\n" +
                "        s.async = true;\n" +
                "        s.src = \"https://mc.yandex.ru/metrika/tag.js\";\n" +
                "\n" +
                "        if (w.opera == \"[object Opera]\") {\n" +
                "            d.addEventListener(\"DOMContentLoaded\", f, false);\n" +
                "        } else { f(); }\n" +
                "    })(document, window, \"yandex_metrika_callbacks2\");\n" +
                "</script>\n" +
                "<noscript><div><img src=\"https://mc.yandex.ru/watch/" + ID_TOKEN + "\" style=\"position:absolute; left:-9999px;\" alt=\"\" /></div></noscript>\n" +
                "<!-- /Yandex.Metrika counter -->";
    }

    public String getRedirectOnQiwiPayment() {
        return "https://qiwi.com/payment/form/" +
                "99?extra%5B%27account%27%5D=" + ACCOUNT_NUMBER +
                "&amountInteger=" + AMOUNT +
                "&extra%5B%27comment%27%5D=" + COMMENT +
                "&currency=643&blocked[0]=account&blocked[1]=comment";
    }
}
