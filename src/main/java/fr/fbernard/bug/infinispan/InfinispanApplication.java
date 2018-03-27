package fr.fbernard.bug.infinispan;

import org.infinispan.configuration.parsing.ConfigurationBuilderHolder;
import org.infinispan.configuration.parsing.ParserRegistry;
import org.infinispan.jcache.embedded.JCacheManager;
import org.infinispan.manager.DefaultCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
import java.io.IOException;
import java.net.URI;

@SpringBootApplication
@EnableCaching
public class InfinispanApplication {

	public static void main(String[] args) {
		SpringApplication.run(InfinispanApplication.class, args);
	}


	@Bean
    @Autowired
    public CommandLineRunner tryGetCache(CacheManager cacheManager){
	    return args -> {
            Cache cache =cacheManager.getCache("mycache");
            if(cache == null){
                throw new RuntimeException("Cache config not loaded");
            }else{
                System.out.println("cache: "+cache.getName()+" well loadded from configuration");
            }
        };
    }

    @Autowired
    @Profile("workaround")
    @Bean
    public javax.cache.CacheManager workaround(CacheProperties cacheProperties) throws IOException {
        ConfigurationBuilderHolder configHolder = (new ParserRegistry()).parse(cacheProperties.getJcache().getConfig().getInputStream());
        DefaultCacheManager isCacheManager = new DefaultCacheManager(configHolder,true);
        return new JCacheManager(URI.create(isCacheManager.getName()),isCacheManager,Caching.getCachingProvider());
    }


}
