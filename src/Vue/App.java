package Vue;

import Function.*;
import javafx.beans.property.*;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.util.Callback;

import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.Date;

public class App {

    public Button mesRDV;
    public Button validateClient;

    @FXML
    private Text zone, surname, name, login;
    @FXML
    private AnchorPane clientsVue, form, completePlanning, planningView;
    @FXML //textfield related to Client form
    private TextField clientName, clientSurname, clientStreetnumber, clientStreetname,
            clientPostcode, clientCity;
    @FXML //texfield related to rdv
    private TextField rdvSpot;
    @FXML
    private DatePicker rdvDate;
    @FXML
    private ComboBox<String> rdvTime;
    @FXML
    private TableView<Client> clients_list;
    @FXML
    private TableColumn<Client, String> name_column, surname_colunm;
    @FXML
    private TableColumn<Client, Void> details_colunm;
    @FXML
    private TableColumn<Client, Date> rdv_colunm;
    //PLANNING ATTRIBUTE
    // -------------------------------------------------------------------------------------------------------//
    @FXML
    private Label week_planning_date, monday_afternoon, tuesday_morning, tuesday_afternoon,
            wednesday_morning, wednesday_afternoon, thursday_morning, thursday_afternoon,
            friday_morning;

    @FXML //planning hbox (contains client action button, hidden by default)
    private HBox monday_afternoon_clientaction, tuesday_morning_clientaction, tuesday_afternoon_clientaction, wednesday_morning_clientaction,
            wednesday_afternoon_clientaction, thursday_morning_clientaction, thursday_afternoon_clientaction, friday_morning_clientaction;
    @FXML //fix rdv
    private Button fixRDV_monday_afternoon, fixRDV_tuesday_morning, fixRDV_tuesday_afternoon, fixRDV_wednesday_morning,
            fixRDV_wednesday_afternoon, fixRDV_thursday_morning, fixRDV_thursday_afternoon, fixRDV_friday_morning;
    @FXML //MODIFY rdv
    private Button modify_monday_afternoonRDV, modify_tuesday_morningRDV, modify_tuesday_afternoonRDV, modify_wednesday_morningrdv,
            modify_wednesday_afternoonRDV, modify_thursday_morningRDV, modify_thursday_afternoonRDV, modify_friday_morningRDV;
    @FXML //REMOVE rdv
    private Button delete_monday_afternoonRDV, delete_tuesday_morningRDV, delete_tuesday_afternoonRDV, delete_wednesday_morningrdv,
            delete_wednesday_afternoonRDV, delete_thursday_morningRDV, delete_thursday_afternoonRDV, delete_friday_morningRDV;
    //-------------------------------------------------------------------------------------------------------//
    //formulaires ajout client / ajout rdv
    @FXML
    private GridPane client_form, rdv_form;
    //this class personnal attribute
    private ArrayList<AnchorPane> viewList = new ArrayList<>();
    private Account account = new Account();
    @FXML
    private Button choose_client, add_client, add_rdv, modify_rdv, give_rdv;
    //-------------------------------------------------------------------------------------------------------//
    //complete planning vue
    @FXML
    private TableView<RDV> complete_planning_list;
    @FXML
    private TableColumn<RDV, Date> day_planning;
    @FXML
    private TableColumn<RDV, Time> slot_planning;
    @FXML
    private TableColumn<RDV, String> client_planning;


    //-------------------------------------------------------------------------------------------------------//
    public void init(Account account) throws Exception {

        this.viewList.add(form);
        this.viewList.add(planningView);
        this.viewList.add(clientsVue);
        this.viewList.add(completePlanning);
        
        showView(clientsVue);

        this.account = account;
        this.account.accountInit();

        login.setText(this.account.getLogin() + " connecté ");
        name.setText(this.account.getCommercial().getName());
        surname.setText(this.account.getCommercial().getSurname());
        zone.setText(String.valueOf(this.account.getCommercial().getGeographic_zone()));

        showClientsList();

        ObservableList<String> timeChoice = rdvTime.getItems();
        timeChoice.add("9:00:00");
        timeChoice.add("14:00:00");
    }

    //planning /action
    public void showPlanning() {

        clearClient_Form();

        clearRDV_Form();

        SimpleDateFormat day = new SimpleDateFormat("EEEE");

        ArrayList<Date> week = account.getThisWeek();

        ArrayList<RDV> rdv_list = account.getWeekPlanning();

        for (Date date : week) {
            String date_for_title = day.format(date);
            if (date_for_title.equals("lundi")) {
                this.week_planning_date.setText("Votre planning de la semaine du " + date);
            } else if (date_for_title.equals("vendredi")) {
                this.week_planning_date.setText(this.week_planning_date.getText() + " au " + date);
            }
            for (RDV rdv : rdv_list) {
                String day_name = day.format(date);
                if (rdv.getDate_rdv().equals(date) && day_name.equals("lundi")) {
                    if (rdv.getHour().equals(Time.valueOf("14:00:00"))) {
                        showRDVinPlanning(this.monday_afternoon, rdv, this.fixRDV_monday_afternoon, this.monday_afternoon_clientaction);
                    }
                } else if (rdv.getDate_rdv().equals(date) && day_name.equals("mardi")) {
                    if (rdv.getHour().equals(Time.valueOf("9:00:00"))) {
                        showRDVinPlanning(this.tuesday_morning, rdv, this.fixRDV_tuesday_morning, this.tuesday_morning_clientaction);
                    } else if (rdv.getHour().equals(Time.valueOf("14:00:00"))) {
                        showRDVinPlanning(this.tuesday_afternoon, rdv, this.fixRDV_tuesday_afternoon, this.tuesday_afternoon_clientaction);
                    }
                } else if (rdv.getDate_rdv().equals(date) && day_name.equals("mercredi")) {
                    if (rdv.getHour().equals(Time.valueOf("9:00:00"))) {
                        showRDVinPlanning(this.wednesday_morning, rdv, this.fixRDV_wednesday_morning, this.wednesday_morning_clientaction);
                    } else if (rdv.getHour().equals(Time.valueOf("14:00:00"))) {
                        showRDVinPlanning(this.wednesday_afternoon, rdv, this.fixRDV_wednesday_afternoon, this.wednesday_afternoon_clientaction);
                    }
                } else if (rdv.getDate_rdv().equals(date) && day_name.equals("jeudi")) {
                    if (rdv.getHour().equals(Time.valueOf("9:00:00"))) {
                        showRDVinPlanning(this.thursday_morning, rdv, this.fixRDV_thursday_morning, this.thursday_morning_clientaction);
                    } else if (rdv.getHour().equals(Time.valueOf("14:00:00"))) {
                        showRDVinPlanning(this.thursday_afternoon, rdv, this.fixRDV_thursday_afternoon, this.thursday_afternoon_clientaction);
                    }
                } else if (rdv.getDate_rdv().equals(date) && day_name.equals("vendredi")) {
                    if (rdv.getHour().equals(Time.valueOf("9:00:00"))) {
                        showRDVinPlanning(this.friday_morning, rdv, this.fixRDV_friday_morning, this.friday_morning_clientaction);
                    }
                }
            }
        }
        showView(planningView);
    }

    public void showRDVinPlanning(
            Label info_rdv, RDV rdv, Button fixerRDV, HBox client_action
    ) {
        String newLine = System.getProperty("line.separator");
        info_rdv.setText(rdv.getClient().getName() + " " + rdv.getClient().getSurname() +
                newLine + newLine + newLine +
                rdv.getRdv_spot());
        info_rdv.getParent().setId(String.valueOf(rdv.getId()));
        fixerRDV.setVisible(false);
        client_action.setVisible(true);
    }

    //todo : show the upcomming week planning ( just change date and use back show planning
    public void showNextWeekPlanning() {
    }

    public void showCompletePlanning() {
        ArrayList<RDV> completeplanning = account.getCommercial().getPlanning().getRdvList();

        ObservableList<RDV> rdvs = FXCollections.observableArrayList(completeplanning);

        this.day_planning.setCellValueFactory(date -> convertToObjectProperty(date.getValue().getDate_rdv()));
        this.slot_planning.setCellValueFactory(time -> convertToTimeProperty(time.getValue().getHour()));
        this.client_planning.setCellValueFactory(client_identity -> convertToProperty(client_identity.getValue().getClient().getName() +
                " " + client_identity.getValue().getClient().getSurname()));

        this.complete_planning_list.setItems(rdvs);

        showView(completePlanning);
    }

    public void showForm() { //show form //utiliser by Ajouter un nouveau client/Fixer un RDV
        //todo fix date and hour(créneau) si on clique sur Fixer un RDV//optionnel ^^
        clearClient_Form();
        clearRDV_Form();
        initForm();
        showView(form);
    }

    public void addRDV() throws Exception {

        Client client = new Client();
        client.setName(this.clientName.getText());
        client.setSurname(this.clientSurname.getText());
        client.guestClient();
        //inutile pour l'instant
        this.clientStreetnumber.getText();
        this.clientStreetname.getText();
        this.clientPostcode.getText();
        this.clientCity.getText();

        RDV rdv = new RDV();

        rdv.setClient(client);
        rdv.setRdv_spot(this.rdvSpot.getText());
        rdv.setDate_rdv(Date.valueOf(this.rdvDate.getValue()));
        rdv.setHour(Time.valueOf(this.rdvTime.getValue()));
        rdv.setPlanningRDV(account.getCommercial().getPlanning().getId());

        account.addRDV(rdv);

        showView(planningView);
        showPlanning();
    }

    public void fullRDVform(Client client) {
        showClientForm();
        showRDVform();
        this.clientName.setText(client.getName());
        this.clientSurname.setText(client.getSurname());
        this.clientStreetnumber.setText(String.valueOf(client.getStreetNb()));
        this.clientStreetname.setText(client.getStreetName());
        this.clientPostcode.setText(String.valueOf(client.getPostCode()));
        this.clientCity.setText(client.getCity());
    }

    public void modifyRDV() {
        this.modify_monday_afternoonRDV.setOnMouseClicked(mouseEvent ->
        {
            try {
                doMofication(this.modify_monday_afternoonRDV);
                resetRDVinPlanning(this.monday_afternoon, account.findRDV(Integer.parseInt(this.monday_afternoon.getParent().getId())), this.fixRDV_monday_afternoon, this.monday_afternoon_clientaction);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        this.modify_tuesday_morningRDV.setOnMouseClicked(mouseEvent ->
        {
            try {
                doMofication(this.modify_tuesday_morningRDV);
                resetRDVinPlanning(this.tuesday_morning, account.findRDV(Integer.parseInt(this.tuesday_morning.getParent().getId())), this.fixRDV_tuesday_morning, this.tuesday_morning_clientaction);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        this.modify_tuesday_afternoonRDV.setOnMouseClicked(mouseEvent ->
        {
            try {
                doMofication(this.modify_tuesday_afternoonRDV);
                resetRDVinPlanning(this.tuesday_afternoon, account.findRDV(Integer.parseInt(this.tuesday_afternoon.getParent().getId())), this.fixRDV_tuesday_afternoon, this.tuesday_afternoon_clientaction);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        this.modify_wednesday_morningrdv.setOnMouseClicked(mouseEvent ->
        {
            try {
                doMofication(this.modify_wednesday_morningrdv);
                resetRDVinPlanning(this.wednesday_morning, account.findRDV(Integer.parseInt(this.wednesday_morning.getParent().getId())), this.fixRDV_wednesday_morning, this.wednesday_morning_clientaction);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        this.modify_wednesday_afternoonRDV.setOnMouseClicked(mouseEvent ->
        {
            try {
                doMofication(this.modify_wednesday_afternoonRDV);
                resetRDVinPlanning(this.wednesday_afternoon, account.findRDV(Integer.parseInt(this.wednesday_afternoon.getParent().getId())), this.fixRDV_wednesday_afternoon, this.wednesday_afternoon_clientaction);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        this.modify_thursday_morningRDV.setOnMouseClicked(mouseEvent ->
        {
            try {
                doMofication(this.modify_thursday_morningRDV);
                resetRDVinPlanning(this.thursday_morning, account.findRDV(Integer.parseInt(this.thursday_morning.getParent().getId())), this.fixRDV_thursday_morning, this.thursday_morning_clientaction);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        this.modify_thursday_afternoonRDV.setOnMouseClicked(mouseEvent ->
        {
            try {
                doMofication(this.modify_thursday_afternoonRDV);
                resetRDVinPlanning(this.thursday_afternoon, account.findRDV(Integer.parseInt(this.thursday_afternoon.getParent().getId())), this.fixRDV_thursday_afternoon, this.thursday_afternoon_clientaction);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        this.modify_friday_morningRDV.setOnMouseClicked(mouseEvent ->
        {
            try {
                doMofication(this.modify_friday_morningRDV);
                resetRDVinPlanning(this.friday_morning, account.findRDV(Integer.parseInt(this.friday_morning.getParent().getId())), this.fixRDV_friday_morning, this.friday_morning_clientaction);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void removeRDV() {
        this.delete_monday_afternoonRDV.setOnMouseClicked(mouseEvent ->
        {
            try {
                resetRDVinPlanning(this.monday_afternoon, account.findRDV(Integer.parseInt(this.monday_afternoon.getParent().getId())), this.fixRDV_monday_afternoon, this.monday_afternoon_clientaction);
                account.removeRDV(account.findRDV(Integer.parseInt(this.monday_afternoon.getParent().getId()))); //todo : check remove function
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        this.delete_tuesday_morningRDV.setOnMouseClicked(mouseEvent ->
        {
            try {
                resetRDVinPlanning(this.tuesday_morning, account.findRDV(Integer.parseInt(this.tuesday_morning.getParent().getId())), this.fixRDV_tuesday_morning, this.tuesday_morning_clientaction);
                account.removeRDV(account.findRDV(Integer.parseInt(this.tuesday_morning.getParent().getId()))); //todo : check remove function
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        this.delete_tuesday_afternoonRDV.setOnMouseClicked(mouseEvent ->
        {
            try {
                resetRDVinPlanning(this.tuesday_afternoon, account.findRDV(Integer.parseInt(this.tuesday_afternoon.getParent().getId())), this.fixRDV_tuesday_afternoon, this.tuesday_afternoon_clientaction);
                account.removeRDV(account.findRDV(Integer.parseInt(this.tuesday_afternoon.getParent().getId()))); //todo : check remove function
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        this.delete_wednesday_morningrdv.setOnMouseClicked(mouseEvent ->
        {
            try {
                resetRDVinPlanning(this.wednesday_morning, account.findRDV(Integer.parseInt(this.wednesday_morning.getParent().getId())), this.fixRDV_wednesday_morning, this.wednesday_morning_clientaction);
                account.removeRDV(account.findRDV(Integer.parseInt(this.wednesday_morning.getParent().getId()))); //todo : check remove function
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        this.delete_wednesday_afternoonRDV.setOnMouseClicked(mouseEvent ->
        {
            try {
                resetRDVinPlanning(this.wednesday_afternoon, account.findRDV(Integer.parseInt(this.wednesday_afternoon.getParent().getId())), this.fixRDV_wednesday_afternoon, this.wednesday_afternoon_clientaction);
                account.removeRDV(account.findRDV(Integer.parseInt(this.wednesday_afternoon.getParent().getId()))); //todo : check remove function
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        this.delete_thursday_morningRDV.setOnMouseClicked(mouseEvent ->
        {
            try {
                resetRDVinPlanning(this.thursday_morning, account.findRDV(Integer.parseInt(this.thursday_morning.getParent().getId())), this.fixRDV_thursday_morning, this.thursday_morning_clientaction);
                account.removeRDV(account.findRDV(Integer.parseInt(this.thursday_morning.getParent().getId())));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        this.delete_thursday_afternoonRDV.setOnMouseClicked(mouseEvent ->
        {
            try {
                resetRDVinPlanning(this.thursday_afternoon, account.findRDV(Integer.parseInt(this.thursday_afternoon.getParent().getId())), this.fixRDV_thursday_afternoon, this.thursday_afternoon_clientaction);
                account.removeRDV(account.findRDV(Integer.parseInt(this.thursday_afternoon.getParent().getId()))); //todo : check remove function
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        this.delete_friday_morningRDV.setOnMouseClicked(mouseEvent ->
        {
            try {
                resetRDVinPlanning(this.friday_morning, account.findRDV(Integer.parseInt(this.friday_morning.getParent().getId())), this.fixRDV_friday_morning, this.friday_morning_clientaction);
                account.removeRDV(account.findRDV(Integer.parseInt(this.friday_morning.getParent().getId())));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void doMofication(Button button) throws Exception {
        showForm();
        RDV rdv;
        rdv = account.findRDV(Integer.parseInt(button.getParent().getParent().getId()));
        fullRDVform(rdv.getClient());
        this.rdvSpot.setText(rdv.getRdv_spot());
        this.rdvDate.setValue(Date.valueOf(String.valueOf(rdv.getDate_rdv())).toLocalDate());
        this.rdvTime.setValue(String.valueOf(rdv.getHour()));
        this.give_rdv.setVisible(false);
        this.modify_rdv.setVisible(true);
        this.modify_rdv.setOnAction((ActionEvent rdv_update_event) -> {
            if (!this.rdvSpot.getText().equals(rdv.getRdv_spot())) {
                rdv.setRdv_spot(this.rdvSpot.getText());
            } else if (!Date.valueOf(this.rdvDate.getValue()).equals(rdv.getDate_rdv())) {
                rdv.setDate_rdv(Date.valueOf(this.rdvDate.getValue()));
            } else if (!Time.valueOf(this.rdvTime.getValue()).equals(rdv.getHour())) {
                rdv.setHour(Time.valueOf(this.rdvTime.getValue()));
            } else {
                System.out.println("No Change");
            }
            try {
                account.updateRDV(rdv);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            this.modify_rdv.setVisible(false);
            this.give_rdv.setVisible(true);
            showPlanning();
        });
    }

    public void resetRDVinPlanning(
            Label info_rdv, RDV rdv, Button fixerRDV, HBox client_action
    ) throws Exception {
        fixerRDV.getParent().getParent().setId(null);
        info_rdv.setText("Créneau Disponible");
        fixerRDV.setVisible(true);
        client_action.setVisible(false);
    }

    //client action
    public void showClientsList() throws Exception {
        /*
        NOTE FOR YOU GUYS / La vraie fonction ressemble à ça :
        this.rdv_colunm.setCellValueFactory(new Callback<TableColumn.CellClientFeatures<Client, Date>, ObservableValue<Date>>() {
            @Override
            public ObservableValue<Date> call(TableColumn.CellClientFeatures<Client, Date> date) {
                return convertToObjectProperty(date.getValue().getLast_rdv());
            }
        });
         */

        ArrayList<Client> clientsList = account.getCommercial().getClientList();

        ObservableList<Client> details = FXCollections.observableArrayList(clientsList);

        this.name_column.setCellValueFactory(name -> convertToProperty(name.getValue().getName()));
        this.surname_colunm.setCellValueFactory(surname -> convertToProperty(surname.getValue().getSurname()));
        this.rdv_colunm.setCellValueFactory(date -> convertToObjectProperty(date.getValue().getLast_rdv()));

        //https://riptutorial.com/javafx/example/27946/add-button-to-tableview pour comprendre
        Callback<TableColumn<Client, Void>, TableCell<Client, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Client, Void> call(final TableColumn<Client, Void> param) {
                return new TableCell<>() {
                    private final Button btn = new Button("Attribuer un rendez-vous");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Client client = getTableView().getItems().get(getIndex());
                            showForm();
                            //todo : full client information
                            fullRDVform(client);

                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
            }
        };

        this.details_colunm.setCellFactory(cellFactory);

        this.clients_list.setItems(details);
    }

    public void showClientForm() {
        clearClient_Form();
        this.client_form.setVisible(true);
        this.add_client.setVisible(false);
        this.choose_client.setVisible(false);
    }

    public void showRDVform() {
        this.rdv_form.setVisible(true);
        this.choose_client.setVisible(false);
        this.add_rdv.setVisible(false);
    }

    public void validateClientAdd() throws Exception {

        HashMap<String, String> clientInfo = new HashMap<>();

        clientInfo.put("name", clientName.getText());
        clientInfo.put("surname", clientSurname.getText());
        clientInfo.put("street_number", clientStreetnumber.getText());
        clientInfo.put("street_name", clientStreetname.getText());
        clientInfo.put("postcode", clientPostcode.getText());
        clientInfo.put("city", clientCity.getText());

        account.createClient(clientInfo);
        updateClientList();

        this.add_rdv.setVisible(true);
    }

    public void updateClientList() throws Exception {
        //todo set a update for client List
        clients_list.getItems().clear();
        showClientsList();
    }

    public void goBack() {
        //todo : ajouter 2 fonctions qui vide les champs en fonction des deux formulaire (client_form/rdv_form)
        //vide tous les champs
        clearClient_Form();
        clearRDV_Form();
        //show client list
        showView(clientsVue);
        //put back form to default view
        initForm();
    }

    public void clearClient_Form() {
        this.clientName.clear();
        this.clientSurname.clear();
        this.clientStreetnumber.clear();
        this.clientStreetname.clear();
        this.clientPostcode.clear();
        this.clientCity.clear();
    }

    public void clearRDV_Form() {
        this.rdvSpot.clear();
        this.rdvDate.getEditor().clear();
        this.rdvTime.setValue(null);
    }

    public void initForm() {
        this.client_form.setVisible(false);
        this.add_client.setVisible(true);
        this.choose_client.setVisible(true);
        this.rdv_form.setVisible(false);
    }

    //utilities function
//convertit une date en SimpleDatePropperty
    public ObjectProperty<Date> convertToObjectProperty(Date date) {
        return new SimpleObjectProperty<>(date);
    }

    public ObjectProperty<Time> convertToTimeProperty(Time time) {
        return new SimpleObjectProperty<>(time);
    }

    public StringProperty convertToProperty(String string) {
        return new SimpleStringProperty(string);
    }

    //todo : add this function to UML Schema
    public void showView(AnchorPane anchorPane) {
        for (AnchorPane anchorPane1 : this.viewList) {
            anchorPane1.setVisible(anchorPane1 == anchorPane);
        }
    }
}