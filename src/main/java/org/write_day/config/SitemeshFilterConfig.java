package org.write_day.config;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;

public class SitemeshFilterConfig extends ConfigurableSiteMeshFilter {

    /** Для всех 1 шаблонизатор т.е decorator.jsp */
    @Override
    protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
        builder.addDecoratorPath("/*", "/layouts/decorator.jsp");
    }
}
