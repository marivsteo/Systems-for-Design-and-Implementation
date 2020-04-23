package lab9.labproblems.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"lab9.labproblems.repository", "lab9.labproblems.service", "lab9.labproblems.ui"})
public class CatalogConfig {


}
