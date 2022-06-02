package co.uniquindio.unitravel.testing.Testing;

@Profile("grid")
@LazyConfiguration
public class RemoteWebDriverConfig {
    @Value("${selenium.grid.url}")
    private URL url;

    @WebdriverScopeBean
    @ConditionalOnProperty(name = "browser", havingValue = "firefox")
    @Primary
    public WebDriver remoteFirefoxDriver(){
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        return new RemoteWebDriver(this.url, firefoxOptions);
    }

    @WebdriverScopeBean
    @ConditionalOnProperty(name = "browser", havingValue = "edge")
    @Primary
    public WebDriver remoteEdgeDriver(){
        EdgeOptions edgeOptions = new EdgeOptions();
        return new RemoteWebDriver(this.url, edgeOptions);
    }

    @WebdriverScopeBean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = "browser", havingValue = "chrome")
    @Primary
    public WebDriver remoteChromeDriver(){
        ChromeOptions chromeOptions = new ChromeOptions();
        return new RemoteWebDriver(this.url, chromeOptions);
    }
}