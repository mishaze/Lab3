import gorodagame.Game;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Client1 {
    private Client1() {
    }

    public static void main(String[] args) throws Exception {
        String host = (args.length < 1) ? null : args[0];
        Scanner in = new Scanner(System.in);
        //ввод ссервера
        String objectName = "Addition";
        String gamerName = "1";
        short position;
        String lastGorod = "";


        Registry registry = LocateRegistry.getRegistry(host);
        AdditionInterface stub = (AdditionInterface) registry.lookup(objectName);

        position = (short) (stub.setGamer(gamerName));
        //Занято ли имя
        if (position > 0) {
            //Вывод
            System.out.println("Вы вошли в игру, как толька в лобби будет больше 1 игрока, игра начнеться, удачи!");
            while (true) {
                if (stub.whoWinner() >= 0) {
                    if (stub.whoWinner() == position) {
                        System.out.println("ВЫ выйграли");
                    } else {
                        System.out.println("ВЫ ПРОИГРАЛИ");
                    }
                    break;
                }
                //вывод последнего введеного города
                if (!lastGorod.equals(stub.getLastGorod())) {
                    lastGorod = stub.getLastGorod();
                    System.out.println(lastGorod);
                }
                //Ожидание своей очереди
                if (!(stub.getQueue() == position)) {
                    TimeUnit.MILLISECONDS.sleep(500);
                    continue;
                }


                // ввод города
                System.out.print("Input city: ");
                String city = in.nextLine();
                //проверка критерия игры
                String response = String.valueOf(stub.getAnswer(city, position));
                System.out.println("response: " + response);
                if (response.equals("ВЫ ПРОИГРАЛИ") || response.equals("game over!"))
                    break;
            }
        } else {
            if (position == -1) System.out.println("Имя занято");
            else if (position == -2) System.out.println("Лобби полное");
            else if (position == -3) System.out.println("Игра уже идет");

        }

    }

}
