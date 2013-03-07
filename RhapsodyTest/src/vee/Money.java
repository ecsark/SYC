package vee;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "money")
public class Money {
	
	/**
	 * Define State Constants
	 */
	public static final int STATE_NORMAL = 0;

	private long m_id;
	private long m_amount;
	private int m_state;
	
	@Id
	@Column(name = "id")
	public long getId() {
		return m_id;
	}
	private void setId(long id) {
		m_id = id;
	}
	
	@Column(name = "amount")
	public long getAmount() {
		return m_amount;
	}
	public void setAmount(long amount) {
		m_amount = amount;
	}
	
	@Column(name = "state")
	public int getState() {
		return m_state;
	}
	private void setState(int state) {
		m_state = state;
	}
	
	/**
	 * Constructor for Hibernate
	 */
	Money() {}
	
	/**
	 * Constructor for Initialization
	 */
	Money(long id, long amount, int state) {
		setId(id);
		setAmount(amount);
		setState(state);
	}
	
	/**
	 * Print itself
	 */
	public String toString() {
		return 
				"[" + this.getClass().toString() + "]" +
				" ID: " + getId() +
				", Amount: " + getAmount() +
				", State: " + getState();
	}
	
}
