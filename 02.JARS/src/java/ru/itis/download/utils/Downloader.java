package ru.itis.download.utils;
import java.net.*;
import java.io.*;
import java.util.*;

public class Downloader{

	public static void download(String link, String folder){

		try{
			URL url = new URL(link);
			InputStream in = new BufferedInputStream(url.openStream());
			OutputStream out = new BufferedOutputStream(
				new FileOutputStream(folder + "/" + link + ".jpg"));
			int i = in.read();
			while(i >= 0){
				out.write(i);
				i = in.read();
			}
			in.close();
			out.close();
			System.out.println("Downloaded");

		} catch (MalformedURLException e1) {
            throw new IllegalArgumentException(e1);
        } catch (FileNotFoundException e3) {
            throw new IllegalArgumentException(e3);
        } catch (IOException e2) {
            throw new IllegalArgumentException(e2);
        }

	}

}