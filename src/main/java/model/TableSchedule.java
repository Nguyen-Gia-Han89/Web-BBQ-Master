
package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TableSchedule {
    private int reservationId;
    private Table table;
    private LocalDate date;          // tách ngày
    private LocalTime startTime;     // tách giờ
    private LocalTime endTime;
    private Booking booking;
    private ReservationStatus status;

    public enum ReservationStatus { BOOKED, CANCELLED, COMPLETED }

    
    // Overlap theo LocalDateTime
    public boolean overlaps(LocalDateTime start, LocalDateTime end) {
        LocalDateTime sdt = LocalDateTime.of(date, startTime);
        LocalDateTime edt = LocalDateTime.of(date, endTime);
        return start.isBefore(edt) && end.isAfter(sdt);
    }

    public String getDateLabel() {                 // 12/12/2025
        return (date != null) ? date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "";
    }
    public String getStartTimeLabel() {            // 18:00
        return (startTime != null) ? startTime.format(DateTimeFormatter.ofPattern("HH:mm")) : "";
    }
    public String getEndTimeLabel() {              // 19:30
        return (endTime != null) ? endTime.format(DateTimeFormatter.ofPattern("HH:mm")) : "";
    }

    public String getStartLabel() {                // 12/12/2025 18:00
        if (date == null || startTime == null) return "";
        return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
             + " " + startTime.format(DateTimeFormatter.ofPattern("HH:mm"));
    }
    public String getEndLabel() {                  // 12/12/2025 19:30
        if (date == null || endTime == null) return "";
        return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
             + " " + endTime.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    // getters/setters 
	public int getReservationId() {
		return reservationId;
	}

	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}

	public ReservationStatus getStatus() {
		return status;
	}

	public void setStatus(ReservationStatus status) {
		this.status = status;
	}

    
    
}
