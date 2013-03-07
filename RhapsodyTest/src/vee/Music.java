package vee;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="music")
public class Music {

	private long m_id;
	private String m_name;
	private String m_url;
	private long m_srcid;
	private int m_state;
	
	@Id
	@Column(name="id")
	public long getId() {
		return m_id;
	}
	public void setId(long id) {
		m_id = id;
	}
	
	@Column(name="name")
	public String getName() {
		return m_name;
	}
	public void setName(String name) {
		m_name = name;
	}
	
	@Column(name="url")
	public String getUrl() {
		return m_url;
	}
	public void setUrl(String url) {
		m_url = url;
	}
	
	@Column(name="srcid")
	public long getSrcid() {
		return m_srcid;
	}
	public void setSrcid(long srcid) {
		m_srcid = srcid;
	}
	
	@Column(name="state")
	public int getState() {
		return m_state;
	}
	public void setState(int state) {
		m_state = state;
	}
	
	/**
	 * Constructor for Hibernate
	 */
	Music() {}
	
	
	Music(long id, String name, String url, long srcid, int state) {
		setId(id);
		setName(name);
		setUrl(url);
		setSrcid(srcid);
		setState(state);
	}
	
	/**
	 * Print itself
	 */
	public String toString() {
		return 
				"[" + this.getClass().toString() + "]" +
				"ID=" + getId() +
				", Name=" + getName() +
				", Url=" + getUrl() +
				", SourceID=" + getSrcid() +
				", State=" + getState();
	}
	
}
