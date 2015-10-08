package upmc.restservice.resources.notifs;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "NOTIFS")
public class Notification implements Serializable {

    @Id @GeneratedValue
    @Column(name = "ID")
    private Integer id;

    @Column(name = "MSG")
    private String message;

	@Column(name = "CREATED_BY")
	private String createdBy;

	//@Column(name = "CREATED_DATE")
	//private Date createdDate;

	public Notification() {}

    public Notification(Integer id, String message, String createdBy) {//, Date createdDate) {
        this.id = id;
        this.message = message;
        this.createdBy = createdBy;
        //this.createdDate = createdDate;
    }

    public Integer getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public String getcreatedBy() {
        return createdBy;
    }

    //public Date getcreatedDate() {
        //return createdDate;
    //}
}