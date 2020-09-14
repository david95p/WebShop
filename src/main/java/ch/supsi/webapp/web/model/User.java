package ch.supsi.webapp.web.model;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class User {
    @Id
    private String userName;

    private String fName;
    private String lName;

    @ManyToOne
    @NotNull
    private Role role;

    @NotNull
    private  String password;

    private String favorites;

    public User() {
    }

    public User(String name) {
        this.userName = name;
        this.role=null;
    }

    public User(String userName, String fName, String lName, Role role, String password, String favorites) {
        this.userName = userName;
        this.fName = fName;
        this.lName = lName;
        this.role = role;
        this.password = password;
        this.favorites =favorites;
    }

    public String getFavorites() {
        return favorites;
    }

    public void setFavorites( String favorites) {
        this.favorites = favorites;
    }

    public void addFavorite (int favItemId){
        favorites += ","+favItemId;
    }

    public void removeFavorite(int id){
        if (!favorites.equals("") && isFavorite(id)){
            String [] favsarr= favorites.split(",");
            String finalfavs="";
            for (int i = 0; i < favsarr.length; i++) {
                if (!favsarr[i].equals(""+id))
                    finalfavs+=favsarr[i]+",";
            }
            favorites= finalfavs;
        }
    }

    public boolean isFavorite (int id){
        if (favorites !=null){
            String [] favsarr= favorites.split(",");

            for (int i = 0; i < favsarr.length; i++) {
                if (!favsarr[i].equals("") && id==Integer.parseInt(favsarr[i]))
                    return true;
            }
            return false;
        }
        return false;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userName.equalsIgnoreCase(user.userName) &&
                role.equals(user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, role);
    }
}
