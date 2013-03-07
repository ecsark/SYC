package vee;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="property")
public class Property {
	
	public static final int STATE_POSSESS = 0;
	public static final int STATE_SELLING = 1;

	private long m_id;
	private long m_uid;
	private long m_mid;
	private long m_cost;
	private long m_price;
	private int m_state;
	
	@Id
	@Column(name="id")
	public long getId() {
		return m_id;
	}
	public void setId(long id) {
		m_id = id;
	}
	
	@Column(name="uid")
	public long getUid() {
		return m_uid;
	}
	public void setUid(long uid) {
		m_uid = uid;
	}
	
	@Column(name="mid")
	public long getMid() {
		return m_mid;
	}
	public void setMid(long mid) {
		m_mid = mid;
	}
	
	@Column(name="cost")
	public long getCost() {
		return m_cost;
	}
	public void setCost(long cost) {
		m_cost = cost;
	}
	
	@Column(name="price")
	public long getPrice() {
		return m_price;
	}
	public void setPrice(long price) {
		m_price = price;
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
	Property() {}
	
	
	Property(long id, long uid, long mid, long cost, long price, int state) {
		setId(id);
		setUid(uid);
		setMid(mid);
		setCost(cost);
		setPrice(price);
		setState(state);
	}
	
	public String toString() {
		return
				"[" + this.getClass().toString() + "]" +
				" ID: " + getId() +
				", UserID: " + getUid() +
				", MusicID: " + getMid() +
				", Cost: " + getCost() +
				", SellPrice: " + getPrice() +
				", State: " + getState();
	}
	
}
