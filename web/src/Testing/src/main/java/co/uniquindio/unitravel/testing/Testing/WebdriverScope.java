package co.uniquindio.unitravel.testing.Testing;

public class WebdriverScope extends SimpleThreadScope {
    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {
        Object o = super.get(name, objectFactory);
        SessionId sessionId = ((RemoteWebDriver)o).getSessionId();
        if(Objects.isNull(sessionId)){
            super.remove(name);
            o = super.get(name, objectFactory);
        }
        return o;
    }

    @Override
    public void registerDestructionCallback(String name, Runnable callback) {
    }
}