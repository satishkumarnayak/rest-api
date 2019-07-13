package com.instanceofcake.rest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class Util {
	
	public static String toStringBody(InputStream inputStream) {
		return new BufferedReader(new InputStreamReader(inputStream)).lines().collect(Collectors.joining("\n"));
	}


}
