package co.uniquindio.unitravel.testing.Testing;

@LazyConfiguration
public class OtherBeansConfigs {
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public JavascriptExecutor javascriptExecutor(WebDriver driver) {
        return (JavascriptExecutor) driver;
    }
}