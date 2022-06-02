package co.uniquindio.unitravel.testing.Testing;

@Configuration
public class WebdriverScopeConfig {
    @Bean
    public static BeanFactoryPostProcessor beanFactoryPostProcessor(){
        return new WebdriverScopePostProcessor();
    }
}