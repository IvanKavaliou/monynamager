package net.ivan.kavaliou.moneyman.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.servlet.*;
import java.io.IOException;
import java.util.Locale;

@Slf4j
public class FixLocaleFilter implements Filter {
    private SessionLocaleResolver localeResolver = new SessionLocaleResolver();

    public FixLocaleFilter() {
        // TODO: get from context
        this.localeResolver = new SessionLocaleResolver();
        this.localeResolver.setDefaultLocale(Locale.ENGLISH);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        try {
            LocaleContext localeContext = this.localeResolver.resolveLocaleContext((javax.servlet.http.HttpServletRequest) request);
            if (localeContext != null) {
                LocaleContextHolder.setLocaleContext(localeContext);
            }
        } catch (Exception e) {
          log.debug(e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
