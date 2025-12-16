package com.ac.cst8319.lms.model;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;
import java.math.BigDecimal;


import lombok.*;

/**
 * Checkout DTO.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Checkout {
	
	private long checkoutId;
    private long loanedTo;
    private long bookCopyId;
    private LocalDate checkoutDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private long checkedOutBy;
    private long returnedBy;
    private BigDecimal lateFeeAssessed;
    private boolean lateFeePaid;
    private Instant createdAt;
	
    public Checkout(long checkoutId, long loanedTo, long bookCopyId, LocalDate checkoutDate, LocalDate dueDate,
			LocalDate returnDate, long checkedOutBy, long returnedBy, BigDecimal lateFeeAssessed, boolean lateFeePaid,
			Instant createdAt) {
		super();
		this.checkoutId = checkoutId;
		this.loanedTo = loanedTo;
		this.bookCopyId = bookCopyId;
		this.checkoutDate = checkoutDate;
		this.dueDate = dueDate;
		this.returnDate = returnDate;
		this.checkedOutBy = checkedOutBy;
		this.returnedBy = returnedBy;
		this.lateFeeAssessed = lateFeeAssessed;
		this.lateFeePaid = lateFeePaid;
		this.createdAt = createdAt;
	}
    
    

	public Checkout() {
		super();
	}

	public long getCheckoutId() {
		return checkoutId;
	}

	public void setCheckoutId(long checkoutId) {
		this.checkoutId = checkoutId;
	}

	public long getLoanedTo() {
		return loanedTo;
	}

	public void setLoanedTo(long loanedTo) {
		this.loanedTo = loanedTo;
	}

	public long getBookCopyId() {
		return bookCopyId;
	}

	public void setBookCopyId(long bookCopyId) {
		this.bookCopyId = bookCopyId;
	}

	public LocalDate getCheckoutDate() {
		return checkoutDate;
	}

	public void setCheckoutDate(LocalDate checkoutDate) {
		this.checkoutDate = checkoutDate;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public LocalDate getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(LocalDate returnDate) {
		this.returnDate = returnDate;
	}

	public long getCheckedOutBy() {
		return checkedOutBy;
	}

	public void setCheckedOutBy(long checkedOutBy) {
		this.checkedOutBy = checkedOutBy;
	}

	public long getReturnedBy() {
		return returnedBy;
	}

	public void setReturnedBy(long returnedBy) {
		this.returnedBy = returnedBy;
	}

	public BigDecimal getLateFeeAssessed() {
		return lateFeeAssessed;
	}

	public void setLateFeeAssessed(BigDecimal lateFeeAssessed) {
		this.lateFeeAssessed = lateFeeAssessed;
	}

	public boolean isLateFeePaid() {
		return lateFeePaid;
	}

	public void setLateFeePaid(boolean lateFeePaid) {
		this.lateFeePaid = lateFeePaid;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}



	@Override
	public String toString() {
		return "Checkout [checkoutId=" + checkoutId + ", loanedTo=" + loanedTo + ", bookCopyId=" + bookCopyId
				+ ", checkoutDate=" + checkoutDate + ", dueDate=" + dueDate + ", returnDate=" + returnDate
				+ ", checkedOutBy=" + checkedOutBy + ", returnedBy=" + returnedBy + ", lateFeeAssessed="
				+ lateFeeAssessed + ", lateFeePaid=" + lateFeePaid + ", createdAt=" + createdAt + "]";
	}



	@Override
	public int hashCode() {
		return Objects.hash(bookCopyId, checkedOutBy, checkoutDate, checkoutId, createdAt, dueDate, lateFeeAssessed,
				lateFeePaid, loanedTo, returnDate, returnedBy);
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Checkout other = (Checkout) obj;
		return bookCopyId == other.bookCopyId && checkedOutBy == other.checkedOutBy
				&& Objects.equals(checkoutDate, other.checkoutDate) && checkoutId == other.checkoutId
				&& Objects.equals(createdAt, other.createdAt) && Objects.equals(dueDate, other.dueDate)
				&& Objects.equals(lateFeeAssessed, other.lateFeeAssessed) && lateFeePaid == other.lateFeePaid
				&& loanedTo == other.loanedTo && Objects.equals(returnDate, other.returnDate)
				&& returnedBy == other.returnedBy;
	}
    
}