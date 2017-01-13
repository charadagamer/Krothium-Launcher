package kml;

import kml.handlers.URLHandler;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;

/**
 * @author DarkLBP
 * website https://krothium.com
 */
class GameStarter {
    public static void main(String[] args){
        System.out.println("GameStarter launcher with " + args.length + " arguments.");
        if (!Utils.ignoreHTTPSCert()){
            System.err.println("Failed load custom HTTPS certificate checker.");
        }
        Utils.testNetwork();
        System.out.println("Using HTTPS when available? | " + Constants.USE_HTTPS);
        if (!Constants.USE_LOCAL){
            URL.setURLStreamHandlerFactory(new URLHandler());
        }
        if (args.length == 0){
            System.exit(-1);
        }
        String mainClass = args[0];
        String[] gameArgs = new String[args.length - 1];
        System.arraycopy(args, 1, gameArgs, 0, args.length - 1);
        try{
            Class<?> gameClass = Class.forName(mainClass);
            Method method = gameClass.getMethod("main", String[].class);
            method.invoke(null, (Object)gameArgs);
        } catch (Exception ex){
            System.out.println("Failed to start the game.");
            ex.printStackTrace();
        }
    }
}
