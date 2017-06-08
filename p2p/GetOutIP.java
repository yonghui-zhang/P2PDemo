package p2p;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetOutIP {
	
	public static String getV4IP() throws IOException
	{
		String ip = "";
		String chinaz = "http://ip.chinaz.com";
		
		StringBuilder inputLine = new StringBuilder();
		String read = "";
		URL url = null;
		HttpURLConnection urlConnection = null;
		BufferedReader in = null;
		url = new URL(chinaz);
		urlConnection = (HttpURLConnection) url.openConnection();
		in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
		while((read = in.readLine()) != null)
		{
			inputLine.append(read + "\r\n");
		}
		if(in != null)
		{
			in.close();
		}
		
		//正则表达式来提取IP
		Pattern p = Pattern.compile("\\<dd class\\=\"fz24\">(.*?)\\<\\/dd>");
		Matcher m = p.matcher(inputLine.toString());
		if(m.find())
		{
			String ipstr = m.group(1);
			ip = ipstr;
		//	System.out.println(ipstr);
		}
		return ip;
	}

	public static void main(String[] args) throws IOException {
	
		String ip = GetOutIP.getV4IP();
		System.out.println("外网ip:" + ip);
	}

}
