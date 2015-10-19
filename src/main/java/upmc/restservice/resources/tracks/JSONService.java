package upmc.restservice.resources.tracks;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.hibernate.Query; 
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import upmc.restservice.resources.tracks.Track;
import upmc.restservice.util.HibernateUtil;

@Path("json/metallica")
public class JSONService {

	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	public Track getTrackInJSON() {

		Track track = new Track();
		track.setId(1);
		track.setTitle("Enter Sandman");
		track.setSinger("Metallica");
		
		SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();

		//String result = "Track saved : " + track;
		session.save(track);

		session.getTransaction().commit();
        session.close();

		return track;
	}

    @GET
    @Path("/get/{id: \\d+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Track fetchBy(@PathParam("id") int id) {

		SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();
		Query query = session.createQuery("from Track where ID=" + id);
		//query.setParameter("id", id);
		Track found = (Track) query.uniqueResult();;

		session.getTransaction().commit();
		session.close();

		return found;
    }

	@POST
	@Path("/post")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createTrackInJSON(Track track) {

		SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();

		String result = "Track saved : " + track;
		session.save(track);

		session.getTransaction().commit();
        session.close();
		return Response.status(201).entity(result).build();
	}

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(Track track) {
        // update notification
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") int id) {
        // deleting notification
    }
	
}