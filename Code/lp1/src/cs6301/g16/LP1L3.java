/*
 * <h1>Fall 2017 Long Project 1: Integer arithmetic with arbitrarily large numbers</h1>
 * <p>
 * Driver function 3.
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.0
 * @since 2017-09-23
 */

package cs6301.g16;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LP1L3 {

    public static void main(String[] args) throws Exception {
        long base = 10;

        List<Num> store = new ArrayList<>(26);

        for (int i = 0; i < 26; i++) {
            store.add(null);
        }

        Scanner in = new Scanner(System.in);
        ArrayList<String[]> src = new ArrayList<>();

        ArrayList<ExecutableLine> exe = new ArrayList<>(src.size());
        boolean newLine = Boolean.TRUE;
        ArrayList<String> line = null;
        String word;
        while (in.hasNext()) {

            if (newLine) {
                newLine = Boolean.FALSE;
                line = new ArrayList<>();
            }

            word = in.next();

            if (word.equals(";")) {
                if (line.size() == 0)
                    break;
                newLine = Boolean.TRUE;
                exe.add(new ExecutableLine.PosFixExpressionLine(line, store, base));
            } else
                line.add(word);
        }

        for (int i = 0; i < exe.size(); i++) {
            ExecutableLine l = exe.get(i);

            int next = l.execute();

            if (next == 0)
                continue;
        }

        exe.get(exe.size() - 1).getNum().printList();
    }
}
