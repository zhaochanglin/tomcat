package MyTomcat1;


import java.io.IOException;
import java.io.OutputStream;

public class MyResponse {
	private OutputStream outputStream;
	public MyResponse (OutputStream outputStream){
		this.outputStream = outputStream;
	}
	/**
	 * HTTP响应协议
	 * <p>Title: write</p>
	 * <p>Description: </p>
	 * @param content
	 * @throws IOException
	 */
	public void write(String content)throws IOException{
		StringBuffer httpResponse = new StringBuffer();
		httpResponse.append("HTTP/1.1 200 OK\n")
		.append("Content-Type: text/html\n")
		.append("\r\n")
		.append("<html><body>")
		.append(content)
		.append("</body></html>");
		
		outputStream.write(httpResponse.toString().getBytes());
		outputStream.close();
	}
}
