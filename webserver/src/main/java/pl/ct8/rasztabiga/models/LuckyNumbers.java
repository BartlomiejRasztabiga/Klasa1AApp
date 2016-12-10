package pl.ct8.rasztabiga.models;

import java.util.ArrayList;
import java.util.List;

public class LuckyNumbers {

    private ArrayList<Integer> numbersList;
    /* 0 = monday
       4 = friday */

    public LuckyNumbers(ArrayList<Integer> list) {
        numbersList = list;
    }

    public ArrayList<Integer> getNumbersList() {
        return numbersList;
    }
}
