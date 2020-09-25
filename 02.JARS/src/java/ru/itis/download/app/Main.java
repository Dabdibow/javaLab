package ru.itis.download.app;


import ru.itis.download.utils.*;
import com.beust.jcommander.JCommander;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main{


	public static void main(String[] args){

		Args argv = new Args();
		JCommander.newBuilder().addObject(argv).build().parse(args);
		String [] links = argv.urls.split(";");
		String folder = argv.folder;
		int count = argv.count;
		ExecutorService threadPool = Executors.newFixedThreadPool(count);
		for(String link : links){
			service.submit(new Runnable (){
				public void run (){
					Downloader.download(link, folder);
				}
			});
		}

	}

}