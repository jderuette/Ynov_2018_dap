package fr.ynov.dap.dap;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @author David_tepoche
 *
 */
@Configuration
public class WebBootstrapConf implements WebMvcConfigurer {
    /**
     * registry the boostrap ressources.
     */
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        // Access Bootstrap static resource:
        registry.addResourceHandler("/jquery/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/jquery/3.3.1-1/");
        registry.addResourceHandler("/popper/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/popper.js/1.14.1/umd/");
        registry.addResourceHandler("/bootstrap/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/bootstrap/4.1.1/");

    }

}
