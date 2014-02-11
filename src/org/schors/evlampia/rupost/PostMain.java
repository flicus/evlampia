/*
 * The MIT License
 *
 * Copyright (c) 2014.  schors (https://github.com/flicus)
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
