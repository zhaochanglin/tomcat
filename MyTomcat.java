package MyTomcat1;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import javax.xml.ws.Dispatch;

public class MyTomcat {
	private int port =8080;
	private Map<String, String> urlServletMap = new HashMap<String ,String>();
	
	public MyTomcat(int port, Map<String, String> urlServletMap) {
		super();
		this.port = port;
		this.urlServletMap = urlServletMap;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public Map<String, String> getUrlServletMap() {
		return urlServletMap;
	}
	public void setUrlServletMap(Map<String, String> urlServletMap) {
		this.urlServletMap = urlServletMap;
	}
	public MyTomcat(int port) {
		super();
		this.port = port;
	}
	public void start(){
		initServletMapping();
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("MyTomcat start .....");
			while (true) {
			Socket socket = serverSocket.accept();
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			MyRequest myRequest = new MyRequest(inputStream);
			MyResponse myResponse = new MyResponse(outputStream);
			//请求分拨
			dispatch(myRequest,myResponse);
			socket.close();
			
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		}finally{
			if(serverSocket != null){
				try {
					serverSocket.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void initServletMapping() {
		for(ServletMapping servletMapping : ServletMappingConfig.servletMappingList){
			urlServletMap.put(servletMapping.getUrl(), servletMapping.getClazz());
		}
		
	}
	private void dispatch(MyRequest myRequest, MyResponse myResponse) {
		String clazz = urlServletMap.get(myRequest.getUrl());
		try {
			Class<MyServlet> myServletClass = (Class<MyServlet>) Class.forName(clazz);
			MyServlet myServlet =myServletClass.newInstance();
			myServlet.service(myRequest, myResponse);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (InstantiationException e) {
			e.printStackTrace();
		}catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		new MyTomcat(8080).start();
	}
	
}
