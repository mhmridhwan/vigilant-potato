package com.project.locker_management.model;

import java.io.Serializable;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the bookings database table.
 * 
 */
@Entity
@Table(name="bookings")
@NamedQuery(name="Booking.findAll", query="SELECT b FROM Booking b")
public class Booking implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="access_attempts")
	private Integer accessAttempts;

	private BigDecimal deposit;

	@Column(name="end_date")
	private Timestamp endDate;

	@Column(name="is_active")
	private Boolean isActive;

	private String password;

	@Column(name="start_date")
	private Timestamp startDate;

	//bi-directional many-to-one association to Locker
	@ManyToOne
	private Locker locker;

	//bi-directional many-to-one association to User
	@ManyToOne
	private User user;

	//bi-directional many-to-one association to Transaction
	@OneToMany(mappedBy="booking")
	private List<Transaction> transactions;

	public Booking() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAccessAttempts() {
		return this.accessAttempts;
	}

	public void setAccessAttempts(Integer accessAttempts) {
		this.accessAttempts = accessAttempts;
	}

	public BigDecimal getDeposit() {
		return this.deposit;
	}

	public void setDeposit(BigDecimal deposit) {
		this.deposit = deposit;
	}

	public Timestamp getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public Boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Timestamp getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public Locker getLocker() {
		return this.locker;
	}

	public void setLocker(Locker locker) {
		this.locker = locker;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Transaction> getTransactions() {
		return this.transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public Transaction addTransaction(Transaction transaction) {
		getTransactions().add(transaction);
		transaction.setBooking(this);

		return transaction;
	}

	public Transaction removeTransaction(Transaction transaction) {
		getTransactions().remove(transaction);
		transaction.setBooking(null);

		return transaction;
	}

}