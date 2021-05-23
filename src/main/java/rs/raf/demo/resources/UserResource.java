package rs.raf.demo.resources;

import rs.raf.demo.entities.Category;
import rs.raf.demo.entities.User;
import rs.raf.demo.services.UserService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/user")
public class UserResource {

    @Inject
    private UserService userService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> allUser() {
        return this.userService.allUser();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public User addUser(User user) {
        return this.userService.addUser(user);
    }

    @PUT
    @Path("/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public User updateUser(User user, @PathParam("email") String email) {
        return this.userService.updateUser(user, email);
    }

}
