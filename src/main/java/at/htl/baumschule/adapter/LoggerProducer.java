package at.htl.baumschule.adapter;

import org.jboss.logging.Logger;

import javax.enterprise.inject.Default;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.ws.rs.Produces;

public class LoggerProducer {

    @Default
    @Produces
    public Logger getLogger(InjectionPoint injectionPoint) {
        return Logger.getLogger(injectionPoint.getBean().getBeanClass());
    }
}
