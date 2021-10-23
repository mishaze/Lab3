package gorodagame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Game {

    private static final ArrayList<Gorod> strana = new ArrayList<>();
    private static Gorod lastGorod;


    public static Gorod getLastGorod() {
        return lastGorod;
    }

    // ##############################################################
    // strana заполняется объектами gorod из отсортированного списка наход. файле cities_ru.txt
    static private void fillStrana(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)))) {
            while (reader.ready()) {
                strana.add(new Gorod(reader.readLine().trim()));
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        strana.trimToSize();
    }

    // ##############################################################
    // дает верный ответ по правилам игры "Города"
    // например: даем "Москва", получаем "Абакан"
    // после выдачи, город считается использованным, т.е. больше не выдается


    // ##############################################################
    // есть ли такой город (его название)
    // если есть, то второй раз его уже не используют (игрок применил его)
    synchronized public static boolean hasGorodInStrana(String quest) {
        Collections.shuffle(strana);
        Gorod questGr = new Gorod(quest);
        if (lastGorod == null) {
            lastGorod = new Gorod(quest.substring(0, 1).toUpperCase());
        }
        String temp = questGr.getName();
        for (Gorod tmpGor : strana) {
            if (temp.equals(tmpGor.getName()) && !questGr.isUsed() && lastGorod.getLinkedName().charAt(1) == questGr.getLinkedName().charAt(0)) {
                lastGorod = questGr;
                tmpGor.setUsed(true);
                return true;
            }
        }
        return false;
    }


    // ##############################################################
    // public static void main(String[] args) throws IOException {
    //  String ss = "Агадес";
    // System.out.println("Начальный город - " + ss);
    //  fillStrana("cities.txt");
    //  while (!"game over!".equals((ss = getAnswer(ss)))){
    //     System.out.println(ss);

    //       ss = getGorodNameLikeSource(ss);
    //        if (ss == null)
    //          break;
    //}

    //} //// end main
    static public void clearProcess() {
        strana.clear();
        lastGorod = null;
    }

    static public void newGame() {
        fillStrana("cities.txt");
    }
}

