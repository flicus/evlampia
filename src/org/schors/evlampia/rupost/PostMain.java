package org.schors.evlampia.rupost;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class PostMain {

    public static void main(String[] args) {
       // TracksManager manager = new TracksManager();

        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String readed = "aa";
        while (!"".equals(readed)) {
            try {
                readed = input.readLine();
                String[] a = readed.split(" ");
             /*   switch (a[0]) {
                    case ".a": {
                        manager.addTrack(a[1], a[2], a[3]);
                        break;
                    }
                    case ".d": {
                        manager.deleteTrack(a[1], a[2]);
                        break;
                    }
                    case ".s": {
                        List<String> res = manager.getStatus(a[1]);
                        for (String s : res) {
                            System.out.println(s);
                        }
                    }
                }*/
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        //manager.stop();
    }
}
