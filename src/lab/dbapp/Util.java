package lab.dbapp;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
	public static Properties getProperties(String path) throws IOException {
		Properties properties = null;
		FileInputStream is = null;
		try{
			is = new FileInputStream(path);
			properties = new Properties();
			properties.load(is);
			
		} finally{
			if (is != null)
				is.close();
			
		}
		return properties;
}
	public static boolean
	registerDBDriver(String driverURL, String className){
		try {
			URL url = new URL(driverURL);
			URLClassLoader ucl = 
					new URLClassLoader(new URL[] { url });
			Driver driver = 
					(Driver) Class.forName(className, true, ucl).newInstance();
			DriverManager.registerDriver(new DbDriver(driver));;
			return true;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	}

