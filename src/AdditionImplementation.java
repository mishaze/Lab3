import gorodagame.Game;
import gorodagame.Gamer;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class AdditionImplementation implements AdditionInterface {

    //чья очередь отвечать
    short queue = 0;
    boolean isGameStart = false;
    private volatile boolean mFinish = false;
    ArrayList<Gamer> ListGamer = new ArrayList<Gamer>();

    public String getAnswer(String city, short positionGamer) throws RemoteException {
        mFinish = true;
        if (!Game.hasGorodInStrana(city)) {
            kickGamer(positionGamer);
            queueNext();
        }
        queueNext();
        return Game.getLastGorod().getName();
    }

    //добавление игроков в игру
    synchronized public int setGamer(String name) throws RemoteException {
        if (isGameStart)
            return -3;//игра началась

        for (Gamer gamer : ListGamer) {
            if (gamer.getName().equals(name)) {
                return -1;//имя занято
            }
        }
        //нет мест
        if (ListGamer.size() >= 5) {
            return -2;//лобби полное
        }
        ListGamer.add(new Gamer(name, ListGamer.size() + 1));
        if (ListGamer.size() >= 2) {
            timerStartGame();
        }
        return ListGamer.size();
    }

    public short getQueue() throws RemoteException {
        return queue;
    }

    //последний введенный город
    public String getLastGorod() throws RemoteException {
        if (Game.getLastGorod() == null)
            return "";
        return Game.getLastGorod().getName();
    }

    //следующий в очереде
    private void queueNext() {
        if (ListGamer.size() == 0) return;

        for (int i = queue + 1; i <= ListGamer.size(); i++) {
            if (ListGamer.get(i - 1).isGame()) {
                queue = (short) i;
                startTimer();
                return;

            }

        }
        for (int i = 1; i <= ListGamer.size(); i++) {
            if (ListGamer.get(i - 1).isGame()) {
                queue = (short) i;
                startTimer();
                return;
            }
        }
    }

    //кик игрока
    private void kickGamer(short position) {
        if (ListGamer.size() == 0) return;
        ListGamer.get(position - 1).setGame(false);
    }

    //запуск таймера
    private void startTimer() {
        mFinish = false;

        Runnable task = () -> {
            int time = 0;
            while (true) {
                if (!mFinish)    //Проверка на необходимость
                {
                    if (time < 20) {

                        try {

                            TimeUnit.MILLISECONDS.sleep(500);
                            time++;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            timesUP();
                        } catch (RemoteException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    }


                } else
                    return;        //Завершение потока
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }

    //Время вышло кик игрока
    private void timesUP() throws RemoteException, InterruptedException {
        if (checkWinner()) {
            TimeUnit.SECONDS.sleep(1);
            mFinish = true;
            startNewGame();
            return;
        }
        kickGamer(getQueue());
        queueNext();

    }

    private void timerStartGame() {
        Runnable task = () -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            queue = 1;
            Game.newGame();
            queueNext();
            isGameStart = true;
            mFinish = false;
        };
        Thread thread = new Thread(task);
        thread.start();
    }

    public boolean checkWinner() {
        if (ListGamer.size() <= 1) {
            Game.clearProcess();
            return true;
        }
        int temp = 0;
        for (Gamer gamer : ListGamer) {
            if (gamer.isGame()) {
                temp++;
            }
        }
        return temp <= 1;
    }

    public int whoWinner() throws RemoteException {
        if (ListGamer.size() == 0) {
            Game.clearProcess();
            return 0;
        }

        int temp = 0;
        int res = -1;
        for (Gamer gamer : ListGamer) {
            if (gamer.isGame()) {
                temp++;
                res = gamer.getNumInGame();
            }
        }
        if (temp > 1 || !isGameStart) return -1;
        else {
            Game.clearProcess();
            return res;
        }
    }

    private void startNewGame() {
        ListGamer.clear();
        queue = 0;
        isGameStart = false;
    }
}
