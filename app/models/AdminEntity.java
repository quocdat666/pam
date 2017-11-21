package models;

import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AdminEntity extends Model {
    @Id
    public int adminId;
    public int adminType;
    public String companyName;
    public String name;
    public String email;
    public int activeStatus;
    public String branch;
    public String username;
    public String nameKana;

}
