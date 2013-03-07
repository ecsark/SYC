package vee;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="account")
/**
 * User Account Structure
 */
public class Account
{
	/**
	 * Account State Constants
	 */
	public final static int STATE_OFFLINE = 0;
	public final static int STATE_ONLINE = 1;
	public final static int STATE_FROZEN = -1;
	
	private long m_id;
	private String m_name;
	private String m_password;
	private int m_state;
	
	@Id
	@Column(name="id")
	public long getId() {
		return m_id;
	}
	private void setId(long id) {
		m_id = id;
	}

	@Column(name="name")
	public String getName() {
		return m_name;
	}
	public void setName(String name) {
		m_name = name;
	}
	
	@Column(name="password")
	public String getPassword() {
		return m_password;
	}
	public void setPassword(String password) {
		m_password = password;
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
	Account() {}

	/**
	 * Constructor for Quick Assignment
	 */
	Account(long id, String name, String password, int state)
	{
		setId(id);
		setName(name);
		setPassword(password);
		setState(state);
	}
	
	/**
	 * Clear the Password Field for Safe Netword Transmition
	 */
	public void ClearPassword()
	{
		setPassword("");
	}
	
	/**
	 * Print itself
	 */
	public String toString() {
		return 
				"[" + this.getClass().toString() + "]" +
				" ID: " + getId() +
				", Name: " + getName() +
				", password: " + getPassword() +
				", state: " + getState();
	}
}
