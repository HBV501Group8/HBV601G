package Model;

public class Users {

    int id;
    String UserName, UserPassword,mail,realName;


    // Getterar og settrar

    public Users(String test, String s, String test1, String s1) {
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }


    //Smiður fyrir User

    public Users(int id, String userName, String userPassword,String email,String realName) {
       // this.id = id;
        UserName = userName;
        UserPassword = userPassword;
        mail = email;
        realName = realName;
    }

    // Geterar og setterar

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserPassword() {
        return UserPassword;
    }

    public void setUserPassword(String userPassword) {
        UserPassword = userPassword;
    }
}
