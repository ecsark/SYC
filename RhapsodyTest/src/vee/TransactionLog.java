package vee;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="transactionlog")
public class TransactionLog
{
	public long TransactionID;
	public long BuyerID;
	public long SellerID;
	public long MusicID;
	public Date TransactionTime;
	public long Price;
	
	@Id
	@Column(name="tid")
	public long getTransactionID() {
		return TransactionID;
	}
	public void setTransactionID(long transactionID) {
		TransactionID = transactionID;
	}
	
	@Column(name="bid")
	public long getBuyerID() {
		return BuyerID;
	}
	public void setBuyerID(long buyerID) {
		BuyerID = buyerID;
	}
	
	@Column(name="sid")
	public long getSellerID() {
		return SellerID;
	}
	public void setSellerID(long sellerID) {
		SellerID = sellerID;
	}
	
	@Column(name="mid")
	public long getMusicID() {
		return MusicID;
	}
	public void setMusicID(long musicID) {
		MusicID = musicID;
	}
	
	@Column(name="time")
	public Date getTransactionTime() {
		return TransactionTime;
	}
	public void setTransactionTime(Date transactionTime) {
		TransactionTime = transactionTime;
	}
	
	@Column(name="price")
	public long getPrice() {
		return Price;
	}
	public void setPrice(long price) {
		Price = price;
	}
	
}
