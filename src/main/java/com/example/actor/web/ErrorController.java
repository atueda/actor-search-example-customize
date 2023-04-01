package com.example.actor.web;

/*import java.util.Set;

import org.apache.tomcat.util.descriptor.web.ErrorPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Configuration
@Controller
public class ErrorController {
  final static Logger logger = LoggerFactory.getLogger(ErrorController.class);

	  @Bean
	  public EmbeddedServletContainerCustomizer containerCustomizer() {
	return new EmbeddedServletContainerCustomizer() {
	  @Override
	  public void customize(ConfigurableServletWebServerFactory container) {
	    ErrorPage error404 = new ErrorPage(HttpStatus.NOT_FOUND, "/error/404");
	    ErrorPage error500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/500");
	    container.addErrorPages(error404, error500);
	  }
	};
	  }
  
  @Bean
  public EmbeddedServletContainerCustomizer containerCustomizer() {
      return new ServletContainerCustomizer();
  }
  private static class ServletContainerCustomizer implements EmbeddedServletContainerCustomizer {
      @Override
      public void customize(ConfigurableEmbeddedServletContainer factory) {
    	  factory.setErrorPages((Set<org.springframework.boot.context.embedded.ErrorPage>) new ErrorPage(HttpStatus.NOT_FOUND, "/404"));
          factory.setErrorPages((Set<org.springframework.boot.context.embedded.ErrorPage>) new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500"));
      }
  }

  @GetMapping("/error/404")
  public String error404() {
    return "Error/404";
  }

  @GetMapping("/error/500")
  public String error500() {
    return "Error/500";
  }

}*/
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController {
 
    /**
     * 初期表示画面を表示する
     * @return 初期表示画面
     */
    @GetMapping("/")
    public String index(){
        return "index";
    }
 
    /**
     * HTTP 500エラー(404以外)を発生させる
     * @return 存在しない画面
     */
    @PostMapping("/submit_error_other")
    public String submitErrorOther(){
        // 存在しない画面に遷移しようとするため、
        // HTTP 500エラーが発生する
        return "no_page";
    }

    /**
     * HTTP 404エラー発生後のエラー画面に遷移させる
     * @return HTTP 404エラー発生後のエラー画面
     */
    @RequestMapping("/notFoundNew")
    public String notFoundNew(){
        return "404new";
    }
}
