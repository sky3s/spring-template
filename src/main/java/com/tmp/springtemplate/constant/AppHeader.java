package com.tmp.springtemplate.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum AppHeader {

    API_KEY("ApiKeyAuth", "X-ApiKey", "txBjGANblv1vozEzYumh", true, "Please enter your apikey"),
    ACCEPT_LANGUAGE("AcceptLanguage", "Accept-Language", "tr", false, "Requested language"),
    X_FORWARDED_FOR("XForwardedFor", "X-FORWARDED-FOR", null, false, "Redirected IP Address");

    /**
     * Unique name of this header
     */
    private final String name;

    /**
     * Header Key pass via Header
     */
    private final String key;

    private final String defaultValue;

    private final boolean required;

    private final String description;

}
