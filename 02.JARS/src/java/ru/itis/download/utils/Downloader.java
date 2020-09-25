package ru.itis.download.utils;
import java.net.*;
import java.io.*;
import java.util.*;

public class downloader{

	public static void download(String link, String folder){

		try{
			URL url = URL(link);
			InputStream in = new BufferedInputStream(url.openStream());
			OutputStream out = new BufferedOutputStream(
				new FileOutputStream(path + "/" + link + ".jpg"));
			int i = in.read();
			while(i >= 0){
				out.write(i);
				i = in read();
			}
			in.close();
			out.close();
			System.out.println("Downloaded");

		} catch(FileNotFoundExeption e1){
			throw new IllegalArgumentException(e1);
		} catch(IOExeption e2){
			throw new IllegalArgumentException(e2);
		}  catch(MalformedURLExeption e3){
			throw new IllegalArgumentException(e3);
		}
	}

}