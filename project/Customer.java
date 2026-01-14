import java.io.Serializable;

public class Customer implements Serializable 
{
  private String customerId;
  private String name;
  private String email;
  private String phone;
  private String address;
    
  public Customer(String customerId, String name, String email, String phone, String address)
  {
    this.customerId =customerId;
    this.name=name;
    this.email=email;
    this.phone=phone;
    this.address=address;
  }
  public void setCustomerId(String customerId) 
  {
    this.customerId = customerId;
  } 

  public String getCustomerId() 
  {
   return customerId;
  }
  public void setName(String name) 
  {
    this.name=name;
  }  
  public String getName() 
  {
    return name;
  }
  public void setEmail(String email) 
  {
    this.email = email;
  }
  public String getEmail() 
  {
    return email;
  }
  public void setPhone(String phone) 
  {
    this.phone = phone;
  }  
  public String getPhone() 
  {
    return phone;
  }
  public void setAddress(String address) 
  {
    this.address = address;
  }
      
  public String getAddress() 
  {
   return address;
  }
  public String toString() 
  {
    return customerId + " - " + name;
  }
}