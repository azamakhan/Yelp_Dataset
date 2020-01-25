
import java.io.Serializable;

public class User_2 implements Serializable {
 
	private static final long serialVersionUID = 1L;
	private String firstName;
    private String lastName;
    private String email;
    
    public User_2() {
        firstName = "abc";
        lastName = "";
        email = "";
    }
    
    public User_2(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
