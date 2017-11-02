package io.project.app;

import io.project.model.Flight;
import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.apache.ignite.cache.CacheAtomicityMode.TRANSACTIONAL;
import org.apache.ignite.cache.CacheMode;

/**
 *
 * @author armdev
 */
@Configuration
public class IgniteConfig {

    @Bean(destroyMethod = "close")
    public Ignite igniteInstance() {
        IgniteConfiguration cfg = new IgniteConfiguration();
        // Setting some custom name for the node.
        cfg.setIgniteInstanceName("springDataNode");
        // Enabling peer-class loading feature.
        cfg.setPeerClassLoadingEnabled(true);
        
     
       
        // Defining and creating a new cache to be used by Ignite Spring Data
        // repository.
        CacheConfiguration ccfg = new CacheConfiguration("FlightCache");
        ccfg.setAtomicityMode(TRANSACTIONAL);
        
         ccfg.setCacheMode(CacheMode.REPLICATED);

        // Setting SQL schema for the cache.
        ccfg.setIndexedTypes(Long.class, Flight.class);          
        cfg.setActiveOnStart(true);    
        cfg.setCacheConfiguration(ccfg);       
        return Ignition.start(cfg);
    }
    
    // https://github.com/apache/ignite/blob/master/examples/src/main/java/org/apache/ignite/examples/datagrid/CacheQueryExample.java
}
