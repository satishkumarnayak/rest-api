package com.instanceofcake.rest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.instanceofcake.rest.router.Response;

public class Util {

	public static String readInputStream(InputStream inputStream) {
		return new BufferedReader(new InputStreamReader(inputStream)).lines().collect(Collectors.joining("\n"));
	}

	public static final Gson GSON = new GsonBuilder().create();

	public static void response(Response resp, int status, Object obj) {
		resp.setStatus(status);
		resp.addHeader("Content-Type", "application/json");
		resp.setBodyAsJson(obj);
	}
}
