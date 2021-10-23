import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AdditionInterface extends Remote {
    public int setGamer(String name) throws RemoteException;
    public String getAnswer(String city, short position) throws RemoteException;
    public short getQueue() throws RemoteException;
    public String getLastGorod()throws RemoteException;
    public int whoWinner() throws RemoteException;
}
