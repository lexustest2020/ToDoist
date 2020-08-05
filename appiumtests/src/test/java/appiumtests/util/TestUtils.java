package appiumtests.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import appiumtests.model.Project;
import appiumtests.model.Task;

public class TestUtils {
	public static final String TAB = "    ";

	public static String toString(InputStream inputStream) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader((inputStream)));
		String output;
		StringBuilder strBuilder = new StringBuilder();

		while ((output = br.readLine()) != null) {
			strBuilder.append(output);
		}

		return strBuilder.toString();
	}

	public static Project toProject(InputStream inputStream) throws JsonSyntaxException, IOException {
		Type type = new TypeToken<Project>() {
		}.getType();
		return new Gson().fromJson(toString(inputStream), type);
	}

	public static List<Project> toProjectList(InputStream inputStream) throws JsonSyntaxException, IOException {
		Type listType = new TypeToken<List<Project>>() {
		}.getType();
		return new Gson().fromJson(toString(inputStream), listType);
	}

	public static List<Task> toTaskList(InputStream inputStream) throws JsonSyntaxException, IOException {
		Type listType = new TypeToken<List<Task>>() {
		}.getType();
		return new Gson().fromJson(toString(inputStream), listType);
	}

	public static byte[] toPostDataBytes(Map<String, Object> params) throws UnsupportedEncodingException {

		StringBuilder postData = new StringBuilder();

		for (Map.Entry<String, Object> param : params.entrySet()) {
			if (postData.length() != 0) {
				postData.append('&');
			}

			postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
			postData.append('=');
			postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
		}

		return postData.toString().getBytes("UTF-8");
	}

	public static void printTestTitle(String title) {
		System.out.println("\n" + title + "\n");
	}

	public static void printlnWithTab(String strToPrint) {
		System.out.println(TestUtils.TAB + strToPrint);
	}

	public static void printEnd() {
		System.out.println("\n- End -");
	}
}