package com.project.locker_management.model;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.List;


/**
 * The persistent class for the lockers database table.
 * 
 */
@Entity
@Table(name="lockers")
@NamedQuery(name="Locker.findAll", query="SELECT l FROM Locker l")
public class Locker implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="is_available")
	private Boolean isAvailable;

	@Column(name="locker_number")
	private String lockerNumber;

	//bi-directional many-to-one association to Booking
	@OneToMany(mappedBy="locker")
	private List<Booking> bookings;

	public Locker() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getIsAvailable() {
		return this.isAvailable;
	}

	public void setIsAvailable(Boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public String getLockerNumber() {
		return this.lockerNumber;
	}

	public void setLockerNumber(String lockerNumber) {
		this.lockerNumber = lockerNumber;
	}

	public List<Booking> getBookings() {
		return this.bookings;
	}

	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}

	public Booking addBooking(Booking booking) {
		getBookings().add(booking);
		booking.setLocker(this);

		return booking;
	}

	public Booking removeBooking(Booking booking) {
		getBookings().remove(booking);
		booking.setLocker(null);

		return booking;
	}

}