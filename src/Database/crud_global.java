package Database;

import Function.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class crud_global {
    static String table_name;
    static String req;

    //----------------------------------------------------------------------------------------------------------------------
    public static void create(Object object) throws SQLException {

        if (object.getClass() == Client.class) {
            table_name = "`client`";
            req = "(`name`, `surname`, `street_number`,`street_name`, `postcode`,`city`) " +
                    " VALUES ('" + ((Client) object).getName() + "',"
                    + " '" + ((Client) object).getSurname() + "' ,"
                    + " '" + ((Client) object).getStreetNb() + "' ,"
                    + " '" + ((Client) object).getStreetName() + "' ,"
                    + " '" + ((Client) object).getPostCode() + "' ,"
                    + " '" + ((Client) object).getCity() + "') ";

        } else if (object.getClass() == Commercial.class) {
            table_name = "`commercial`";
            req = "(`id`,`name`, `surname`, `geographic_zone`)  " +
                    " VALUES ('" + ((Commercial) object).getId() + "',"
                    + " '" + ((Commercial) object).getName() + "' ,"
                    + " '" + ((Commercial) object).getSurname() + "' ,"
                    + " '" + ((Commercial) object).getGeographic_zone() + "' ) ";
        } else if (object.getClass() == Planning.class) {
            table_name = "`planning`";
        } else if (object.getClass() == RDV.class) {
            table_name = "`rdv`";
            req = "(`location`, `date_rdv`, `hour`, `client`, `planning`) " +
                    "VALUES ('" + ((RDV) object).getRdv_spot() + "'  , "
                    + " '" + ((RDV) object).getDate_rdv() + "' ,"
                    + " '" + ((RDV) object).getHour() + "' ,"
                    + " '" + ((RDV) object).getClient().getId() + "' ,"
                    + " '" + ((RDV) object).getPlanningRDV_id() + "' ) ";
        }
        req = "INSERT INTO " + table_name + req + ";";
        System.out.println(req);
        Database.execute_update(req);
    }

    //----------------------------------------------------------------------------------------------------------------------
    public static ResultSet read_by_indication(Object object) {

        if (object.getClass() == Account.class) {
            table_name = "`account_user`";
            req = " WHERE (`login` = '" + ((Account) object).getLogin() + "');";
        } else if (object.getClass() == Client.class) {
            table_name = "`client`";
            req = " WHERE (" +
                    "`name` =  '" + ((Client) object).getName() + "'  AND " +
                    "`surname` = '" + ((Client) object).getSurname() + "' ) ;";

        } else if (object.getClass() == Planning.class) {
            table_name = "`planning`";
            req = "WHERE (`commercial` = '" + ((Planning) object).getId_commercial() + "');";
        } else if (object.getClass() == RDV.class) {
            System.out.println("in read by indication");
            table_name = "`rdv`";
            req = "WHERE (`date_rdv` = '" + ((RDV) object).getDate_rdv() + " '  AND " +
                    "`hour` = '" + ((RDV) object).getHour() + "');";
        }
        req = "SELECT * FROM " + table_name + req;
        System.out.println(req);
        return Database.execute(req);
    }

    //----------------------------------------------------------------------------------------------------------------------
    public static ResultSet read_by_id(Object object) {
        if (object.getClass() == Client.class) {
            table_name = "`client`";
            req = " WHERE (`id` = '" + ((Client) object).getId() + "');";
        } else if (object.getClass() == Commercial.class) {
            table_name = "`commercial`";
            req = " WHERE (`id` = '" + ((Commercial) object).getId() + "');";
        } else if (object.getClass() == Planning.class) {
            table_name = "`rdv`";
            req = " WHERE (`planning` = '" + ((Planning) object).getId() + "');";
        } else if (object.getClass() == RDV.class) {
            table_name = "`rdv`";
            req = " WHERE (`id` = '" + ((RDV) object).getId() + "');";
        }
        req = "SELECT * FROM " + table_name + req;
        System.out.println(req);
        return Database.execute(req);
    }

    //----------------------------------------------------------------------------------------------------------------------
    public static ResultSet read_all(String nom_table) {
        if (nom_table.equals("rdv")) {
            table_name = " `rdv` ";
        } else if (nom_table.equals("client")) {
            table_name = "`client`";
        }
        String req = " SELECT * FROM " + table_name + " ; ";
        return Database.execute(req);
    }

    //----------------------------------------------------------------------------------------------------------------------
    public static void update(Object object) throws SQLException {
        if (object.getClass() == RDV.class) {
            table_name = "`rdv`";
            req = " `location`='" + ((RDV) object).getRdv_spot() +
                    "', `date_rdv`='" + ((RDV) object).getDate_rdv() +
                    "', `hour`='" + ((RDV) object).getHour() +
                    "'  WHERE `id`= " + (((RDV) object).getId()) + ";";
        }
        //UPDATE `algobreizh`.`rdv` SET `location`='chez lui' WHERE  `id`=37;
        req = "UPDATE " + table_name + " SET " + req;
        System.out.println(req);
        Database.execute_update(req);
    }

    //todo : write deletion query
    public static void delete(Object object) throws Exception {
        if (object.getClass() == RDV.class) {
            table_name = "`rdv`";
            req = " WHERE `id`=" + ((RDV) object).getId() + ";";
        }
        req = "DELETE FROM " + table_name + " " + req;
        System.out.println(req); //todo : test code to remove
        Database.execute_update(req);
    }
}
