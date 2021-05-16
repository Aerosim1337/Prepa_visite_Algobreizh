package Function;

import Database.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.Date;
import java.util.HashMap;


public class Account {
    private int id;
    private String login;
    private Commercial commercial = new Commercial();

    //----------------------------------------------------------------------------------------------------------------------
    public String getLogin() {
        return this.login;
    }

    public Commercial getCommercial() {
        return this.commercial;
    }

//----------------------------------------------------------------------------------------------------------------------

    public void setId(int id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

//----------------------------------------------------------------------------------------------------------------------

    public boolean getAccount(String login, String passwd) throws Exception {
        if (login != null && passwd != null) {
            this.setLogin(login);
            ResultSet res = crud_global.read_by_indication(this);
            if (res != null) {
                try {
                    while (res.next()) {
                        if (this.login.equals(res.getString("login"))
                                && passwd.equals(res.getString("passwd"))) {

                            this.setId(res.getInt("id"));
                            this.commercial.setId(res.getInt("commercial"));
                            this.accountInit();
                            return true;
                        }
                    }
                } catch (SQLException e) {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
        return false;
    }

    public void accountInit() throws Exception {
        this.commercial.readCommercial(this.commercial.getId());
    }

    //----------------------------------------------------------------------------------------------------------------------
    //client action
    public void createClient(HashMap<String, String> clientInfo) throws Exception {

        Client client = new Client();
        client.setName(clientInfo.get("name"));
        client.setSurname(clientInfo.get("surname"));
        client.setStreet_number(Integer.parseInt(clientInfo.get("street_number")));
        client.setStreet_name(clientInfo.get("street_name"));
        client.setPostcode(Integer.parseInt(clientInfo.get("postcode")));
        client.setCity(clientInfo.get("city"));

        client.registerClient();

        this.commercial.addClient(client);
    }

    //----------------------------------------------------------------------------------------------------------------------
    //rdv action
    public void addRDV(RDV rdv) throws SQLException {
        this.commercial.registerRDV(rdv);
    }

    public RDV findRDV(int id) throws Exception {
        Planning planning = this.getCommercial().getPlanning();
        ArrayList<RDV> rdvList = planning.getRdvList();
        RDV rdv_to_return = new RDV();
        rdv_to_return.setId(id);
        for (RDV rdv : rdvList) {
            if (rdv.getId() == id) {
                System.out.println(" RDV was found ");
                rdv_to_return = rdv;
            }
        }
        if (rdv_to_return.getClient() == null) {
            System.out.println("searching database");
            rdv_to_return = rdv_to_return.readRdv(id);
        }
        return rdv_to_return;
    }

    public void removeRDV(RDV rdv) throws Exception {
        rdv.removeRDV();
        this.getCommercial().getPlanning().updatePlanning();

    }

    public void updateRDV(RDV rdv) throws SQLException {
        this.getCommercial().modifyRDV(rdv);
        this.getCommercial().getPlanning().updatePlanning();

    }

    //----------------------------------------------------------------------------------------------------------------------
    //planning action
    //return this week planning date
    public ArrayList<Date> getThisWeek() { //ne pas toucher l'ordre
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        Date todayDate = new Date(calendar.getTime().getTime());
        calendar.setTime(todayDate);
        ArrayList<Date> week = new ArrayList<>();
        SimpleDateFormat day = new SimpleDateFormat("EEEE");
        for (int i = 0; i < 5; i++) {
            week.add(todayDate);
            calendar.add(Calendar.DATE, 1);
            todayDate = new Date(calendar.getTime().getTime());
        }
        return week;
    }

    public ArrayList<RDV> getWeekPlanning() {
        return commercial.getPlanning().getRdvList();
    }
}