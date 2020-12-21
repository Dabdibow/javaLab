package ru.itis.javalab.app;



import com.beust.jcommander.JCommander;
import ru.itis.javalab.utils.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main{


	public static void main(String[] args){

		Args argv = new Args();
		JCommander.newBuilder().addObject(argv).build().parse(args);
		//String link = argv.urls;
		//String [] links = argv.urls.split(";");
		ExecutorService threadPool = Executors.newFixedThreadPool(argv.count);
		//for(String link : links){
			threadPool.submit(new Runnable (){
				public void run (){
					Downloader.download(argv.urls, argv.folder);
				}
			});
		//}

	}

} 