package com.tmp.springtemplate.extension.request;

import com.tmp.springtemplate.constant.AppHeader;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Objects;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RequestUtils {


    public static String getAttributeAsString(String key) {

        return Objects.toString(getAttribute(key), null);
    }

    public static Object getAttribute(String key) {

        return RequestContextHolder.currentRequestAttributes().getAttribute(key, RequestAttributes.SCOPE_REQUEST);
    }

    public static void setAttribute(String key, Object value) {

        RequestContextHolder.currentRequestAttributes().setAttribute(key, value, RequestAttributes.SCOPE_REQUEST);
    }


    public static String getHeaderAsString(AppHeader header) {

        return getHeaderAsString(header.getKey());
    }

    public static String getHeaderAsString(String headerKey) {

        return Objects.toString(getHeader(headerKey), null);
    }

    public static Object getHeader(AppHeader header) {

        return getHeader(header.getKey());
    }

    public static Object getHeader(String headerKey) {

        final HttpServletRequest currentHttpRequest = getCurrentRequest();
        if (Objects.nonNull(currentHttpRequest)) {
            return currentHttpRequest.getHeader(headerKey);

        }
        return null;
    }


    public static HttpServletRequest getCurrentRequest() {

        final RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            return ((ServletRequestAttributes) requestAttributes).getRequest();
        }
        return null;
    }

    public static HttpSession getSession() {
        return getSession(true);
    }

    public static HttpSession getSession(boolean create) {
        return getCurrentRequest().getSession(create);
    }

    public static ServletContext getServletContext() {
        return getSession().getServletContext();
    }

    public static ApplicationContext getApplicationContext() {
        return WebApplicationContextUtils.getWebApplicationContext(getServletContext());
    }

    public static <T> T getBean(Class<T> beanClass) {
        try {
            return getApplicationContext().getBean(beanClass);
        } catch (NoUniqueBeanDefinitionException var2) {
            throw var2;
        } catch (NoSuchBeanDefinitionException var3) {
            return null;
        }
    }

    public static <T> T getBean(String name, Class<T> beanClass) {
        try {
            return getApplicationContext().getBean(name, beanClass);
        } catch (NoUniqueBeanDefinitionException var3) {
            throw var3;
        } catch (NoSuchBeanDefinitionException var4) {
            return null;
        }
    }

    public static <T> Map<String, T> getBeansOfType(Class<T> type) {
        return getApplicationContext().getBeansOfType(type);
    }

}
