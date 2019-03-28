package MyTomcat1;


import java.util.ArrayList;
import java.util.List;

public class ServletMappingConfig {
	public static List<ServletMapping> servletMappingList = new ArrayList<>();
	static{
		servletMappingList.add(new ServletMapping("findGirl", "/girl", "MyTomcat1.FindGirlServlet"));
		servletMappingList.add(new ServletMapping("helloWorld", "/world", "MyTomcat1.HelloWorldServlet"));
	}
}
