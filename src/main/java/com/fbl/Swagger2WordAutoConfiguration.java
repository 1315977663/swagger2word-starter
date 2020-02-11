package com.fbl;

import com.fbl.configuration.Swagger2WordProp;
import com.fbl.services.WordService;
import com.fbl.services.impl.WordServiceImpl;
import com.fbl.web.TestApi;
import com.fbl.web.WordController;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.fbl.services.TestService;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

@Configuration
@EnableConfigurationProperties(Swagger2WordProp.class)
public class Swagger2WordAutoConfiguration {

    @Autowired
    Swagger2WordProp swagger2WordProp;

    @Bean
    @ConditionalOnMissingBean(TestService.class)
    public TestService testService() {
        return new TestService(swagger2WordProp);
    }

    @Bean
    @ConditionalOnMissingBean(TestApi.class)
    public TestApi testApi() {
        return new TestApi();
    }

    @Bean
    @ConditionalOnMissingBean(WordController.class)
    public WordController wordController(WordService wordService, RestTemplate restTemplate) {
        return new WordController(wordService, restTemplate);
    }

    @Bean
    @ConditionalOnMissingBean(WordService.class)
    public WordService wordService(RestTemplate restTemplate) {
        return new WordServiceImpl(restTemplate, swagger2WordProp);
    }

    @Bean
    @ConditionalOnMissingBean(RestTemplate.class)
    public RestTemplate restTemplate() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
        SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
                .loadTrustMaterial(null, acceptingTrustStrategy)
                .build();
        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);
        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(csf)
                .build();
        HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);

        //60s
        requestFactory.setConnectTimeout(60 * 1000);
        requestFactory.setReadTimeout(60 * 1000);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return restTemplate;
    }

}
