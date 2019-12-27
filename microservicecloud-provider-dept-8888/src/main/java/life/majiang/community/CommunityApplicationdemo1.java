package life.majiang.community;


import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.jmx.support.RegistrationPolicy;

@EnableDiscoveryClient
@EnableEurekaClient
@SpringBootApplication
@Import(FdfsClientConfig.class)
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
//@EnableCircuitBreaker//对hystrixR熔断机制的支持
public class CommunityApplicationdemo1 {

    public static void main(String[] args) {
        SpringApplication.run(CommunityApplicationdemo1.class, args);
    }

}
