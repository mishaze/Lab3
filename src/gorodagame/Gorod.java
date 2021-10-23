package gorodagame;

import java.util.Objects;

public class Gorod {
    private String name;    // имя города, например, Уфа
    private String linkedName;  // "УА" из примера выше
    private boolean used = false;
    private short score;


    Gorod(String titl) {
        name = titl.toUpperCase();
        name = name.replace('Й', 'И');
        name = name.replace('Ё', 'Е');
        linkedName = "" + name.charAt(0);

        // если последний символ "Ь" или "Ы", берем предпоследний
        if (name.charAt(name.length() - 1) == 'Ь' || name.charAt(name.length() - 1) == 'Ы') {
            linkedName += "" + name.charAt(name.length() - 2);
        } else linkedName += "" + name.charAt(name.length() - 1);

    }



    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getName() {
        return name;
    }

    public String getLinkedName() {
        return linkedName;
    }

    public boolean isUsed() {
        return used;
    }

}