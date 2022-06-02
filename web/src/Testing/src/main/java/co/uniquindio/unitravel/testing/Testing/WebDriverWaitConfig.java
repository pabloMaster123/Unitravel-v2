package co.uniquindio.unitravel.testing.Testing;

@LazyConfiguration
public class WebDriverWaitConfig {

    @Value("${default.timeout:30}")
    private int timeout;

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public WebDriverWait webdriverWait(WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofMillis(this.timeout));
    }
}