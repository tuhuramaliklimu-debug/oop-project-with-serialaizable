import java.io.Serializable;

public class Room implements Serializable
{
  
  public enum RoomStatus {FREE, BOOKED}

  private String id;
  private RoomType type;
  private int capacity;
  private double price;
  private RoomStatus status;
    
  public Room(String id,RoomType type, RoomStatus status,int capacity,double price) 
  {

    this.id=id;
    this.type=type;
    this.capacity=capacity;
    this.price=price;
    this.status=status;
  }
  public void setId(String id)
  {
    this.id=id;
  }  
  public String getId()
  {
    return id; 
  }
  public void setType(RoomType type)
  {
    this.type=type;
  } 
  public RoomType getType() 
  { 
    return type;    
  }
  public void setCapacity(int capacity)
  {
    this.capacity=capacity;
  }
  public int getCapacity() 
  {
    return capacity;
  }
  public void setPrice(double price)
  {
    this.price=price ;
  }
  public double getPrice() 
  { 
    return price; 
  }
  
  public void setStatus(RoomStatus status) { this.status = status; }
  public RoomStatus getStatus() { return status; }
  @Override
  public String toString()
  {
    return id + " - " + type + " (Capacity: " + capacity + ", Price: $" + price + ")";
  }
}
