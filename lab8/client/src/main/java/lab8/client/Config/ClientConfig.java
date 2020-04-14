package lab8.client.Config;

import lab8.client.UI.Console;
import lab8.common.Services.AssignmentService;
import lab8.common.Services.ProblemService;
import lab8.common.Services.StudentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

@Configuration
public class ClientConfig {
    @Bean
    RmiProxyFactoryBean rmiProxyFactoryBean() {
        RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
        rmiProxyFactoryBean.setServiceInterface(StudentService.class);
        rmiProxyFactoryBean.setServiceUrl("rmi://localhost:1099/StudentService");
        return rmiProxyFactoryBean;
    }

    @Bean
    RmiProxyFactoryBean rmiProxyFactoryBean2() {
        RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
        rmiProxyFactoryBean.setServiceInterface(ProblemService.class);
        rmiProxyFactoryBean.setServiceUrl("rmi://localhost:1099/ProblemService");
        return rmiProxyFactoryBean;
    }

    @Bean
    RmiProxyFactoryBean rmiProxyFactoryBean3() {
        RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
        rmiProxyFactoryBean.setServiceInterface(AssignmentService.class);
        rmiProxyFactoryBean.setServiceUrl("rmi://localhost:1099/AssignmentService");
        return rmiProxyFactoryBean;
    }

    @Bean
    Console console() {
        return new Console();
    }
}
