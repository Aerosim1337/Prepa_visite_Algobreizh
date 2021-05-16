package Function;

import Database.*;
import Vue.ErrorBox;

import java.awt.desktop.SystemEventListener;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Client {

    private int id;
    private String name;
    private String surname;
    private int street_number;
    private String street_name;
    private int postcode;
    private String city;
    private int geographic_zone; //todo : add to BDD
    private Date last_rdv; //todo : add to BDD

    //----------------------------------------------------------------------------------------------------------------------
    //getter_____________________________________________
    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getSurname() {
        return this.surname;
    }

    public int getStreetNb() {
        return this.street_number;
    }

    public String getStreetName() {
        return this.street_name;
    }

    public int getPostCode() {
        return this.postcode;
    }

    public String getCity() {
        return this.city;
    }

    public int getGeographic_zone() {
        return this.geographic_zone;
    } //todo : to use once add to bdd

    public Date getLast_rdv() {
        return this.last_rdv;
    }

    //----------------------------------------------------------------------------------------------------------------------
    //setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setStreet_number(int street_number) {
        this.street_number = street_number;
    }

    public void setStreet_name(String street_name) {
        this.street_name = street_name;
    }

    public void setPostcode(int postcode) {
        this.postcode = postcode;
    }

    public void setCity(String city) {
        this.city = city;
    }

    //----------------------------------------------------------------------------------------------------------------------
    //function -------------------------
    public Client readClient(int id) {
        this.setId(id);
        ResultSet res = crud_global.read_by_id(this);
        try {
            while (res.next()) {
                this.setName(res.getString("name"));
                this.setSurname(res.getString("surname"));
                this.setStreet_number(res.getInt("street_number"));
                this.setStreet_name(res.getString("street_name"));
                this.setPostcode(res.getInt("postcode"));
                this.setCity(res.getString("city"));
            }
        } catch (SQLException e) {
            ErrorBox errorBox = new ErrorBox("Error : " + e); //todo: errorbox
        }
        return this;
    }

    public void guestClient() throws SQLException {
        System.out.println("I'm in guest function");
        ResultSet res = crud_global.read_by_indication(this);
        while (res.next()) {
            this.setId(res.getInt("id"));
            System.out.println(res.getInt("id"));
        }
        this.readClient(this.getId());

    }

    //register a new client in Database and set his id
    public void registerClient() throws SQLException {
        crud_global.create(this);
    }

}
