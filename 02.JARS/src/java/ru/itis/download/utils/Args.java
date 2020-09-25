package ru.itis.download.utils;

import com.beust.jcommander.Parameter;
import java.net.*;


public class Args{

	@Parameter(names = {"--mode"})
	public String mode;

	@Parameter(names = {"--count"})
	public int count;

	@Parameter(names = {"--URLS"})
	public String urls;

	@Parameter(names = {"--folder"})
	public String folder;

}