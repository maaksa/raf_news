package rs.raf.demo;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import javax.inject.Singleton;
import javax.ws.rs.ApplicationPath;

@ApplicationPath("/api")
public class HelloApplication extends ResourceConfig {

    public HelloApplication() {
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);

        AbstractBinder binder = new AbstractBinder() {
            @Override
            protected void configure() {
                this.bind(MySqlSubjectRepository.class).to(SubjectRepository.class).in(Singleton.class);
                this.bind(InMemoryUserRepository.class).to(UserRepository.class).in(Singleton.class);

                this.bindAsContract(SubjectService.class);
                this.bindAsContract(UserService.class);
            }
        };
        register(binder);

        packages("rs.raf.demo");
    }
}
