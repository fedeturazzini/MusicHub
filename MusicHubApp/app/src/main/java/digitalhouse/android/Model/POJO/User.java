package digitalhouse.android.Model.POJO;

/**
 * Created by gaston on 07/06/17.
 */

public class User {

    //atributos
    private String mName;
    private String mLastName;
    private String mUserName;
    private String mEmail;
    private String mPassword;
    private Integer mUserImageResource;

    //consutructor
    public User(String mName, String mLastName, String mUserName, String mEmail, String password, Integer imageResource) {
        this.mName = mName;
        this.mLastName = mLastName;
        this.mUserName = mUserName;
        this.mEmail = mEmail;
        this.mPassword = password;
        this.mUserImageResource = imageResource;
    }

    //getters
    public String getmName() {
        return mName;
    }

    public String getmLastName() {
        return mLastName;
    }

    public String getmUserName() {
        return mUserName;
    }

    public String getmEmail() {
        return mEmail;
    }

    public String getPassword() {
        return mPassword;
    }

    public Integer getmUserImageResource() {
        return mUserImageResource;
    }

    //setters
    public void setmName(String mName) {
        this.mName = mName;
    }

    public void setmLastName(String mLastName) {
        this.mLastName = mLastName;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public void setmUserImageResource(Integer mUserImageResource) {
        this.mUserImageResource = mUserImageResource;
    }
}
