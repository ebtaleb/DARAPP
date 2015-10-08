package upmc.restservice.resources.tracks;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "TRACKS")
public class Track implements Serializable {

    @Id 
	//@GeneratedValue
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

	@Column(name = "TITLE")
	private String title;

	@Column(name = "SINGER")
	private String singer;

	public Track() {}

	public int getId() {
		return id;
	}

	public void setId(int i) {
		this.id = i;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSinger() {
		return singer;
	}

	public void setSinger(String singer) {
		this.singer = singer;
	}

	@Override
	public String toString() {
		return "Track [title=" + title + ", singer=" + singer + "]";
	}

}