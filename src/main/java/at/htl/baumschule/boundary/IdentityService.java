package at.htl.baumschule.boundary;

import io.quarkus.security.identity.SecurityIdentity;
import org.jboss.resteasy.annotations.cache.NoCache;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@RequestScoped
@Path("identity")
public class IdentityService {

    @Inject
    SecurityIdentity identity;

    @GET
    @Path("info")
    @Produces(MediaType.APPLICATION_JSON)
    @NoCache
    public String getInfo() {
        return identity.getRoles().toString();
    }

}
