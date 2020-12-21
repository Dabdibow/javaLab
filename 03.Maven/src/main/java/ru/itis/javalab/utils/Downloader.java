package ru.itis.javalab.utils;
import java.net.*;
import java.io.*;
import java.util.*;

public class Downloader{

    public static void download(String link, String folder){

        try{
            URL url = new URL(link);
            InputStream in = new BufferedInputStream(url.openStream());
            File file = new File(String.valueOf(UUID.randomUUID()));
            OutputStream out = new BufferedOutputStream(new FileOutputStream(folder + "/" + file.getName() + ".jpg"));
            int i = in.read();
            while(i >= 0){
                out.write(i);
                i = in.read();
            }
            in.close();
            out.close();
            System.out.println("Downloaded");

        } catch (MalformedURLException | FileNotFoundException e1) {
            throw new IllegalArgumentException(e1);
        } catch (IOException e2) {
            throw new IllegalArgumentException(e2);
        }

    }

}