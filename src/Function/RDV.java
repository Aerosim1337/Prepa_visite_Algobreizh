package Function;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Time;

import Database.*;

public class RDV {
    private int id;
    private Date date_rdv;
    private Time hour;
    private String rdv_spot; //appeller location dans la bdd
    private Client client;
    private int planningRDV; //todo to rectifuy in code ( present in BDD)

    //----------------------------------------------------------------------------------------------------------------------
    //setters -------------------------------------------------------------
    public void setId(int id) {
        this.id = id;
    }

    public void setDate_rdv(Date date_rdv) {
        this.date_rdv = date_rdv;
    }

    public void setHour(Time hour) {
        this.hour = hour;
    }

    public void setRdv_spot(String rdv_spot) {
        this.rdv_spot = rdv_spot;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setPlanningRDV(int planningRDV) {
        this.planningRDV = planningRDV;
    }

    //----------------------------------------------------------------------------------------------------------------------
    //getters ------------------------------------------------------------
    public int getId() {
        return this.id;
    }

    public Client getClient() {
        return this.client;
    }

    public Date getDate_rdv() {
        return this.date_rdv;
    }

    public String getRdv_spot() {
        return this.rdv_spot;
    }

    public Time getHour() {
        return this.hour;
    }

    public int getPlanningRDV_id() {
        return this.planningRDV;
    }

    //----------------------------------------------------------------------------------------------------------------------
    //function
    public RDV readRdv(int id) throws Exception {
        this.setId(id);
        ResultSet res = crud_global.read_by_id(this);
        while (res.next()) {
            this.setDate_rdv(res.getDate("date_rdv"));
            this.setHour(res.getTime("hour"));
            this.setRdv_spot(res.getString("location"));
            this.setClient(new Client().readClient(res.getInt("client")));
        }
        return this;
    }

    public void removeRDV() throws Exception {
        crud_global.delete(this);
    }
}
