package Function;

import Database.*;
import Vue.ErrorBox;

import java.sql.*;
import java.util.*;

public class Commercial {
    private int id;
    private String name;
    private String surname;
    private int geographic_zone;
    private ArrayList<Client> clients = new ArrayList<>();
    private Planning planning = new Planning();

//----------------------------------------------------------------------------------------------------------------------
    //getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getGeographic_zone() {
        return geographic_zone;
    }

    public Planning getPlanning() {
        return this.planning;
    }

    public ArrayList<Client> getClientList() throws Exception {
        this.setClientList();
        return this.clients;
    }

//----------------------------------------------------------------------------------------------------------------------
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setGeographic_zone(int geographic_zone) {
        this.geographic_zone = geographic_zone;
    }

    public void setClientList() throws Exception {
        this.clients.clear();
        ResultSet res = crud_global.read_all("client");
        while (res.next()) {
            Client client = new Client();
            client.setId(res.getInt("id"));
            client.setName(res.getString("name"));
            client.setSurname(res.getString("surname"));
            client.setStreet_number(res.getInt("street_number"));
            client.setStreet_name(res.getString("street_name"));
            client.setPostcode(res.getInt("postcode"));
            client.setCity(res.getString("city"));
            this.addClient(client);
        }
    }

    public void setPlanning(int planning) {
        this.planning.setId(planning);
        this.planning.readPlanning(planning);

    }

//----------------------------------------------------------------------------------------------------------------------
    public void readCommercial(int id) throws Exception {
        this.setId(id);
        ResultSet res = crud_global.read_by_id(this);
        try {
            while (res.next()) {
                this.setName(res.getString("name"));
                this.setSurname(res.getString("surname"));
                this.setGeographic_zone(res.getInt("geographic_zone"));
            }
        } catch (SQLException e) {
            ErrorBox errorBox = new ErrorBox(" Error : " + e); //todo: error message
        }
        this.setClientList(); //todo choose client list by geographic zone ^^

        this.setPlanning(this.getId());
    }

//on s'assurre de ne pas avoir de doublons et ajoute a la liste des clients de ce commercial
    public void addClient(Client client){
        if (!this.clients.contains(client)) {
            this.clients.add(client);
        }
    }

    public void modifyRDV (RDV rdv) throws SQLException {
        crud_global.update(rdv);
        this.getPlanning().setRdv_list(this.getPlanning().getId());
        //todo : check update rdv
    }

    public void registerRDV(RDV rdv) throws SQLException {
        System.out.println("the planning id is " + rdv.getPlanningRDV_id()); //todo test code to remove
        crud_global.create(rdv);
        ResultSet res = crud_global.read_by_indication(rdv);
        while (res.next()) {
            rdv.setId(res.getInt("id"));
        }
        this.planning.addRdvPlanning(rdv);
        //todo : dead code have to see utility
        //rdv.setPlanning((int) rdvArray.get("planning"));
    }

    public void removeRdv() {
        //todo : to remove rdv
    }
}
