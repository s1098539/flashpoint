package Main;

import Controller.*;
import Model.Kleur;
import Model.Rol;
import Model.Speler;
import View.SpelView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import static javafx.application.Application.launch;


public class Main2 extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        SpelController spelC = new SpelController();
        SpelView spelView = new SpelView(spelC);
        primaryStage.setScene(spelView.getScene());
        primaryStage.setTitle("Flash point");
        primaryStage.show();
        System.out.println("The Application has started.");

        SpeelveldController veldC = new SpeelveldController();
        SpelerController spelerC = new SpelerController();
        DobbelsteenController dobbelC = new DobbelsteenController();
        ChatController chatC = new ChatController();

        veldC.setControllers(spelC,spelerC,dobbelC,chatC);
        spelC.setControllers(veldC,spelerC,dobbelC,chatC);
        spelerC.setControllers(veldC,spelC,dobbelC,chatC);
        dobbelC.setControllers(veldC,spelC,spelerC,chatC);
        chatC.setControllers(spelC,veldC,spelerC,dobbelC);

        veldC.run();

//        Speler test = new Speler("Sam", Kleur.ROOD, "127", 0, 0, 0, 0, Rol.COMMANDANT, true);

    }

}
