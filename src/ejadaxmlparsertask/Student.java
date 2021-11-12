package ejadaxmlparsertask;

import java.util.Arrays;

/**
 *
 * @author sasuk
 */
public class Student {

    private int id;
    private String ename;
    private String aname;
    private String email;
    private String phone;
    private String address;
    private boolean valid = false;

    public Student(int id, String ename, String aname, String email, String phone, String address) {
        this.id = id;
        this.ename = ename;
        this.aname = aname;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public String getEname() {
        return ename;
    }

    public String getAname() {
        return aname;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public boolean getValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    /**
     * Converts the student attributes to string array.
     * @return string array containing all student attributes.
     */
    public String[] toStringArray(){
        return new String[]{String.valueOf(id), ename, aname, email, phone, address};
    }
    
    @Override
    public String toString() {
        return Arrays.toString(this.toStringArray());
    }
}
