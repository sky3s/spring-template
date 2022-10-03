package com.tmp.springtemplate.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Locale;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AppConstants {

    public static final String DEFAULT_ENCODING = "UTF-8";

    public static final Locale TURKISH_LOCALE = new Locale.Builder().setLanguage("tr").build();

    public static final Locale ENGLISH_LOCALE = new Locale.Builder().setLanguage("en").build();

    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";


}
