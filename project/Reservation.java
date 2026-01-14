import java.io.Serializable;

public class Reservation implements Serializable {
  public enum Status {CONFIRMED, CANCELLED,COMPLETED}
    private String reservationId;
    private Customer customer;
    private Room room;
    private MyDate checkInDate;
    private MyDate checkOutDate;
    private int numberOfNights;  
    private int numberOfGuests;
    private Status status; 
    private double totalPrice;

    public Reservation(String reservationId,Customer customer,Room room,MyDate checkInDate,MyDate checkOutDate,int numberOfNights,int numberOfGuests) 
    {
        this.reservationId=reservationId;
        this.customer=customer;
        this.room=room;
        this.checkInDate=checkInDate;
        this.checkOutDate=checkOutDate;
        this.numberOfNights=numberOfNights;
        this.numberOfGuests=numberOfGuests;
        this.status =Status.CONFIRMED;
        this.totalPrice=room.getPrice()*numberOfNights;
        
    }

    public String getReservationId() 
    {
        return reservationId;
    }
    public void setReservationId(String reservationId)
    { 
        this.reservationId = reservationId; 
    }
    public Customer getCustomer() 
    {
        return customer;
    }
    public void setCustomer(Customer customer) 
    {
        this.customer = customer;
    }
    public Room getRoom() 
    {
        return room;
    }
    public void setRoom(Room room)
    {
        this.room = room;
        calculateTotalPrice(); 
    }
    public MyDate getCheckInDate() 
    {
        return checkInDate;
    }
    public void setCheckInDate(MyDate checkInDate) 
    {
        this.checkInDate = checkInDate;
    }
    public MyDate getCheckOutDate() 
    {
        return checkOutDate;
    }
    public void setCheckOutDate(MyDate checkOutDate) 
    {
        this.checkOutDate = checkOutDate;     
    }
    public int getNumberOfNights() 
    {
        return numberOfNights;
    }
    public void setNumberOfNights(int numberOfNights) {
        this.numberOfNights = numberOfNights;
        calculateTotalPrice(); 
    }
    public int getNumberOfGuests() {
        return numberOfGuests;
    }
    public void setNumberOfGuests(int numberOfGuests) 
    {
        if (numberOfGuests > room.getCapacity()) 
        {
            throw new IllegalArgumentException("Number of guests is more than the capacity");
        }
        this.numberOfGuests = numberOfGuests;
    }
    public Status getStatus() 
    {
        return status;
    }
    public void setStatus(Status status) 
    {
        this.status = status;
    }
    public double getTotalPrice() 
    {
        return totalPrice;
    }
    private void calculateTotalPrice() 
    {
        if (room != null) 
        {
            this.totalPrice=room.getPrice()*numberOfNights;
        }
    }
    public boolean isActive()
    {
      return status==Status.CONFIRMED;
    }
    public boolean cancelReservation() 
    {
        if (isActive()) 
        {
            this.status=Status.CANCELLED;           
            return true;
        }
        return false;
    }

    public boolean completeReservation() 
    {
        if (isActive()) 
        {
            this.status=Status.COMPLETED;
            return true;
        }
        return false;
    }

}