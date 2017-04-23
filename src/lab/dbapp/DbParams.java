package lab.dbapp;

import java.io.IOException;
import java.util.Properties;

public class DbParams {
	private String driverUrl = null;
	private String driverClass = null;
	private String dbUrl = null;
	private String dbUser = null;
	private String dbPassword = null;

	public String getDriverUrl() {
		return driverUrl;
	}

	public void setDriverUrl(String driverUrl) {
		this.driverUrl = driverUrl;
	}

	public String getDriverClass() {
		return driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public String getDbUrl() {
		return dbUrl;
	}

	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}

	public String getDbUser() {
		return dbUser;
	}

	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}

	public String getDbPassword() {
		return dbPassword;
	}

	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}

	public boolean loadParams(String propertiesPath) throws IOException {
		Properties properties = Util.getProperties(propertiesPath);
		if (properties != null) {
			driverUrl = properties.getProperty("driver_url");
			driverClass = properties.getProperty("driver_class");
			dbUrl = properties.getProperty("db_url");
			dbUser = properties.getProperty("db_user");
			dbPassword = properties.getProperty("db_password");
			return true;
		}
		return false;
	}
}
