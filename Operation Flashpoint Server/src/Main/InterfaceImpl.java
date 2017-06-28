package Main;

import Controller.SpeelveldController;
import Controller.SpelController;
import Model.*;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import Main.ChatListenInterface;

public class InterfaceImpl implements Interface, Serializable {
    //DONT GET RID OF THIS ---------------------------

    Spel spelServer;
    SpeelveldData veldServer;

    public Spel getSpelServer() {return spelServer;}
    public void setSpelServer(Spel spelServer) {this.spelServer = spelServer;}
    public SpeelveldData getVeldServer() {return veldServer;}
    public void setVeldServer(SpeelveldData veldServer) {this.veldServer = veldServer;}

//    public Map<Kleur, Speler> getmMap() {
//        return mMap;
//    }

    protected InterfaceImpl() throws IOException {
    }

    //Map<Kleur, Speler> mMap = new HashMap<Kleur, Speler>();
    ArrayList<Speler> spelers = new ArrayList<Speler>();

    int i = 1;
    int a = 0;
    int b = 0;
    private ArrayList<ChatListenInterface> Listeners = new ArrayList<ChatListenInterface>();
    private ArrayList<Interface> gameListeners = new ArrayList<Interface>();

    @Override
    public void addSpeler(String naam) throws RemoteException {
        Kleur kleur;
        switch (i) {
            case 1:
                Speler speler = new Speler(naam, Kleur.BLAUW, 1, 1);
                spelers.add(speler);
                i++;
                break;
            case 2:
                Speler speler2 = new Speler(naam, Kleur.GEEL, 1, 2);
                spelers.add(speler2);
                i++;
                break;
            case 3:
                Speler speler3 = new Speler(naam, Kleur.GROEN, 1, 3);
                spelers.add(speler3);
                i++;
                break;
            case 4:
                Speler speler4 = new Speler(naam, Kleur.ORANJE, 1, 4);
                spelers.add(speler4);
                i++;
                break;
            case 5:
                Speler speler5 = new Speler(naam, Kleur.ROOD, 1, 5);
                spelers.add(speler5);
                i++;
                break;
            case 6:
                Speler speler6 = new Speler(naam, Kleur.ZWART, 1, 6);
                spelers.add(speler6);
                i++;
                break;
            default:
        }
    }

    @Override
    public ArrayList<Speler> GetSpeler() throws RemoteException {
        return spelers;
    }
    @Override
    public void setFirstTimeSpel(Spel spel) throws RemoteException {
        if (a == 0) {
            this.spelServer = spel;
            i++;
        }
    }
    @Override
    public void setFirstTimeData(SpeelveldData veldD) throws RemoteException {
        if (b == 0) {
            this.veldServer = veldD;
            b++;
        }
    }

    @Override
    public void setSpelData(Spel spel, SpeelveldData veldD) throws RemoteException {

        setVeldServer(veldD);
        setSpelServer(spel);
        this.notifyObserversSpel();

    }

    @Override
    public Spel updateGetSpel() throws RemoteException {
        return getSpelServer();
    }

    @Override
    public SpeelveldData updateGetData() throws RemoteException {
        return getVeldServer();
    }

    //---------------------------------->

    //    public synchronized void notifyObservers(Spel spel) throws RemoteException {
//        for (Interface client : Listeners) {
//            client.update(spel);
//        }
//        System.out.println("Notified observers");
//    }
    @Override
    public synchronized void registerObserver(ChatListenInterface listener) throws RemoteException {
        this.Listeners.add(listener);
        System.out.println("Listener added" + listener);
    }

    @Override
    public void registerObserverSpel(Interface gameListener) throws RemoteException {
        this.gameListeners.add(gameListener);
        System.out.println("Listener added" + gameListener);
        notifyObserversSpel();

    }

    @Override
    public synchronized void unregisterObserver(Interface gameListener) throws RemoteException {
        this.gameListeners.remove(gameListener);
        System.out.println("Interface listener removed.");
    }

    @Override
    public synchronized void unregisterObserver(ChatListenInterface listener) throws RemoteException {
        this.Listeners.remove(listener);
        System.out.println("ChatListenerInterface removed");
    }


//    public void update(Spel spel) throws RemoteException {
//        spelC.setSpel(spel);
//    }


//    @Override
//    public void updateMessage(Message message) throws RemoteException {
//        System.out.println(message.getSenderName() + ": " + message.getMessage());
//    }

    @Override
    public void sendMessageObject(String user, String message) throws RemoteException {
        Message messageObject = new Message(user, message);
        this.notifyObserversMessage(messageObject);
    }
    @Override
    public synchronized void notifyObserversMessage(Message message) throws RemoteException {
        for (ChatListenInterface client : Listeners) {
            client.update(message);
        }
        System.out.println("Notified observers");
    }
    @Override
    public synchronized void notifyObserversSpel() throws RemoteException {
        for (ChatListenInterface client : Listeners) {
            client.receiveGame(getSpelServer(),getVeldServer());
        }
        System.out.println("Notified observers");
    }
    public static void main(String[] args) throws Exception{

    }
}

