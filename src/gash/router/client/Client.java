/**
 * Copyright 2016 Gash.
 *
 * This file and intellectual content is protected under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package gash.router.client;

import gash.router.client.CommConnection;
import gash.router.client.CommListener;
import gash.router.client.MessageClient;
import routing.Pipe.CommandMessage;

import java.io.File;
import java.util.logging.Logger;


/**
 * Created by Richa on 3/31/16.
 */
public class Client implements CommListener {
    private MessageClient mc;

    public Client(MessageClient mc) {
        init(mc);
    }

    private void init(MessageClient mc) {
        this.mc = mc;
        this.mc.addListener(this);
    }

//    private void ping(int N) {
//        // test round-trip overhead (note overhead for initial connection)
//        final int maxN = 10;
//        long[] dt = new long[N];
//        long st = System.currentTimeMillis(), ft = 0;
//        for (int n = 0; n < N; n++) {
//            mc.ping();
//            ft = System.currentTimeMillis();
//            dt[n] = ft - st;
//            st = ft;
//        }
//
//        System.out.println("Round-trip ping times (msec)");
//        for (int n = 0; n < N; n++)
//            System.out.print(dt[n] + " ");
//        System.out.println("");
//    }

    @Override
    public String getListenerID() {

        return "demo";
    }

    @Override
    public void onMessage(CommandMessage msg) {

        System.out.println("Reply from Server " + msg);
    }

    /**
     * sample application (client) use of our messaging service
     *
     * @param args
     */
    public static void main(String[] args) {
        //System.out.println(args[1]);
        String host = "127.0.0.1";
        int port = 4568;
        int ctr=3;
            try {
                MessageClient mc = new MessageClient(host, port);
                Client cl = new Client(mc);

                // do stuff w/ the connection
                System.out.println("Sending message to server....." + "Hello from client");
                mc.sendMessage("Hello from client");
                System.out.println("Sending Image to server.....");
                File file = new File("/Users/Rii/Documents/Cmpe275/lab1/fluffy/netty_mongo/test.jpg");
                int size = (int) file.length();
                byte[] buffer = new byte[size];
                mc.sendImage(buffer);
                System.out.flush();
                Thread.sleep(10 * 1000);
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                CommConnection.getInstance().release();
            }


        System.out.println("Done execution");

    }
}
