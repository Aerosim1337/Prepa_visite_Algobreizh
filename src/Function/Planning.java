package Function;

import Database.*;
import Vue.ErrorBox;

import java.sql.ResultSet;
import java.sql.*;
import java.util.ArrayList;

public class Planning {
    private int id;
    private int id_commercial;
    private ArrayList<RDV> rdv_list = new ArrayList<>();

    //----------------------------------------------------------------------------------------------------------------------
    //getter______________________________________________
    public int getId() {
        System.out.println("Get planning id");
        return this.id;
    }

    public int getId_commercial() {
        return this.id_commercial;
    }

    public ArrayList<RDV> getRdvList() {
        return this.rdv_list;
    }

    //----------------------------------------------------------------------------------------------------------------------
    //setter_______________________________________________
    public void setId(int id) {
        this.id = id;
    }

    public void setId_commercial(int id_commercial) {
        this.id_commercial = id;
    }

    public void setRdv_list(int id_planning) throws SQLException {
        System.out.println(" rdv_list was set " + this.rdv_list.size());
        this.rdv_list.clear();
        ResultSet res = crud_global.read_all("rdv");
        while (res.next()) {
            if (res.getInt("planning") == this.id) {
                RDV rdv = new RDV();
                rdv.setId(res.getInt("id"));
                rdv.setRdv_spot(res.getString("location"));
                rdv.setPlanningRDV(res.getInt("planning"));
                rdv.setDate_rdv(res.getDate("date_rdv"));
                rdv.setHour(res.getTime("hour"));
                rdv.setClient(new Client().readClient(res.getInt("client")));
                this.addRdvPlanning(rdv);
            }
        }
    }

    //----------------------------------------------------------------------------------------------------------------------
    //function
    public void readPlanning(int id_commercial) {
        this.setId_commercial(id_commercial);
        ResultSet res = crud_global.read_by_indication(this);
        try {
            if (res != null) {
                while (res.next()) {
                    this.setId(res.getInt("id"));
                }
            }
            this.setRdv_list(this.getId());
        } catch (SQLException e) {
            ErrorBox errorBox = new ErrorBox("Error : " + e); //todo: errorbox
        }
    }

    //add a new rdv
    public void addRdvPlanning(RDV newRdv) {
        System.out.println("RDV with " + newRdv.getClient().getName() + " was add");
        this.rdv_list.add(newRdv);
    }

    public void updatePlanning() { //todo write function to update planning
        this.readPlanning(this.getId_commercial());
    }
}

