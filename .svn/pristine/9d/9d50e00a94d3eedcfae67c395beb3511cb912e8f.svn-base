package com.quinnox.flm.tms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * @author AmareshP
 *
 */
@ServletComponentScan
@SpringBootApplication
@EnableAsync
@EnableScheduling
public class TmsApplication extends SpringBootServletInitializer
{

	@Override
	protected SpringApplicationBuilder configure(final SpringApplicationBuilder application)
	{
		return application.sources(TmsApplication.class);
	}

	public static void main(final String[] args)
	{
		SpringApplication.run(TmsApplication.class, args);
	}

	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer()
	{

		return new EmbeddedServletContainerCustomizer()
		{
			@Override
			public void customize(final ConfigurableEmbeddedServletContainer container)
			{

				final ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/error/401.jsp");
				final ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/error/404.jsp");
				final ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/500.jsp");
				container.addErrorPages(error401Page, error404Page, error500Page);
			}
		};
	}
}
