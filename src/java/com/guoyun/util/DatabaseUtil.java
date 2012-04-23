package com.guoyun.util;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * The Util of the database，used to connection and close the database
 * 数据库工具类，用于连接数据库、关闭数据库等
 * 
 * @author Mickey
 * @date 2009-12-14
 * @time 上午11:22:29
 */
public class DatabaseUtil {
	/*
	 * public static ComboPooledDataSource cpds = new ComboPooledDataSource();
	 * 
	 * static { try { Class.forName("com.mysql.jdbc.Driver");
	 * 
	 * } catch (ClassNotFoundException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 * 
	 * try { cpds.setDriverClass("com.mysql.jdbc.Driver"); // 设置数据库URL
	 * 
	 * // 当连接池中的连接用完时，C3PO一次性创建新的连接数目; cpds.setAcquireIncrement(5); //
	 * 定义在从数据库获取新的连接失败后重复尝试获取的次数，默认为30; cpds.setAcquireRetryAttempts(30); //
	 * 两次连接中间隔时间默认为1000毫秒 cpds.setAcquireRetryDelay(1000); // 连接关闭时默认将所有未提交的操作回滚
	 * 默认为false; cpds.setAutoCommitOnClose(false); //
	 * 获取连接失败将会引起所有等待获取连接的线程异常,但是数据源仍有效的保留
	 * ,并在下次调用getConnection()的时候继续尝试获取连接.如果设为true
	 * ,那么尝试获取连接失败后该数据源将申明已经断开并永久关闭.默认为false
	 * cpds.setBreakAfterAcquireFailure(false); //
	 * 当连接池用完时客户端调用getConnection()后等待获取新连接的时间
	 * ,超时后将抛出SQLException,如设为0则无限期等待.单位毫秒,默认为0 cpds.setCheckoutTimeout(5000);
	 * // 隔多少秒检查所有连接池中的空闲连接,默认为0表示不检查; cpds.setIdleConnectionTestPeriod(0); //
	 * 初始化时创建的连接数,应在minPoolSize与maxPoolSize之间取值.默认为3
	 * cpds.setInitialPoolSize(10); // 最大空闲时间,超过空闲时间的连接将被丢弃.为0或负数据则永不丢弃.默认为0;
	 * cpds.setMaxIdleTime(0); // 连接池中保留的最大连接数据.默认为15 cpds.setMaxPoolSize(20);
	 * //JDBC的标准参数,用以控制数据源内加载的PreparedStatement数据.
	 * 但由于预缓存的Statement属于单个Connection而不是整个连接池
	 * .所以设置这个参数需要考滤到多方面的因素,如果maxStatements //
	 * 与maxStatementsPerConnection均为0,则缓存被关闭.默认为0; cpds.setMaxStatements(0); //
	 * 连接池内单个连接所拥有的最大缓存被关闭.默认为0; cpds.setMaxStatementsPerConnection(0); //
	 * C3P0是异步操作的,缓慢的JDBC操作通过帮助进程完成.扩展这些操作可以有效的提升性能,通过多数程实现多个操作同时被执行.默为为3
	 * cpds.setNumHelperThreads(3); // 用户修改系统配置参数执行前最多等待的秒数.默认为300;
	 * cpds.setPropertyCycle(300); } catch (PropertyVetoException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); }
	 * 
	 * }
	 */

	public static void closeConnection(Connection con, Statement stmt,
			PreparedStatement pstmt, ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void closeConnection(Connection con, Statement stmt,
			ResultSet rs) {
		closeConnection(con, stmt, null, rs);
	}

	/*
	 * public static void closeCop3() { try { DataSources.destroy(cpds); } catch
	 * (SQLException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } }
	 */

	/**
	 * The util to connection any database 
     * 数据库连接工具类
	 * 
	 * @param url
	 * @param userName
	 * @param userPassword
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection(String url, String userName,
			String userPassword) throws SQLException {
		Connection con = null;
		try {
			con = DriverManager.getConnection(url, userName, userPassword);
		} catch (SQLException e) {
			throw e;
		}
		return con;
	}
    
    /**
     * The util to connection any database 
     * 数据库连接工具类,无需用户名跟密码,一般用于嵌入式数据库
     * 
     * @param url
     * @param userName
     * @param userPassword
     * @return
     * @throws SQLException
     */
    public static Connection getConnection(String url) throws SQLException {
        Connection con = null;
        try {
            con = DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw e;
        }
        return con;
    }
    

	/**
	 * 获取连接，爬虫，www.lelong.com
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection_Mysql_CrawlServer_Lelong()
			throws SQLException {
		String url = ConfigUtil.getValue("mysql.CrawlDatabase.lelong.url");
		String userName = ConfigUtil
				.getValue("mysql.CrawlDatabase.lelong.userName");
		String userPassword = ConfigUtil
				.getValue("mysql.CrawlDatabase.lelong.userPassword");

		return getConnection(url, userName, userPassword);
	}

	public static Connection getConnection_Mysql_CrawlServer_Local()
			throws SQLException, ClassNotFoundException {
	    Class.forName("org.gjt.mm.mysql.Driver");
		String url = ConfigUtil.getValue("mysql.CrawlDatabase.local.url");
		String userName = ConfigUtil
				.getValue("mysql.CrawlDatabase.local.userName");
		String userPassword = ConfigUtil
				.getValue("mysql.CrawlDatabase.local.userPassword");

		return getConnection(url, userName, userPassword);
	}

	public static Connection getConnection_Mysql_CrawlServer_Remote_Zhejiang()
			throws SQLException {
		String url = ConfigUtil
				.getValue("mysql.CrawlDatabase.remote.zhejiang.url");
		String userName = ConfigUtil
				.getValue("mysql.CrawlDatabase.remote.zhejiang.userName");
		String userPassword = ConfigUtil
				.getValue("mysql.CrawlDatabase.remote.zhejiang.userPassword");

		return getConnection(url, userName, userPassword);
	}

	/**
	 * Get the connection of the Search Server's database 获得Search服务器的数据库连接
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection_Mysql_SearchServer()
			throws SQLException {
		String url = ConfigUtil.getValue("mysql.searchDatabase.url");
		String userName = ConfigUtil.getValue("mysql.searchDatabase.userName");
		String userPassword = ConfigUtil
				.getValue("mysql.SearchDatabase.userPassword");

		return getConnection(url, userName, userPassword);

	}

	/**
	 * Get the connecion of the Web Server's database 获得Web服务器的数据库连接
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection_Mysql_WebServer()
			throws SQLException {
		String url = ConfigUtil.getValue("mysql.WebDatabase.url");
		String userName = ConfigUtil.getValue("mysql.WebDatabase.userName");
		String userPassword = ConfigUtil
				.getValue("mysql.WebDatabase.userPassword");

		return getConnection(url, userName, userPassword);
	}
	
	/**
     * Get the connecion of the Web Server's database 
     * 获得Web服务器的数据库连接
     * 
     * @return
     * @throws SQLException
	 * @throws ClassNotFoundException 
     */
    public static Connection getConnection_Sqlserver_WebServer()
            throws SQLException, ClassNotFoundException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String url = ConfigUtil.getValue("web.config.sqlserver.url");
        String userName = ConfigUtil.getValue("web.config.sqlserver.userName");
        String userPassword = ConfigUtil.getValue("web.config.sqlserver.userPassword");

        return getConnection(url, userName, userPassword);
    }
    /**
     * 获得Derby本地数据库连接
     * @return
     * @throws ClassNotFoundException 
     * @throws SQLException 
     */
    public static Connection getConnection_Derby_Localhost() throws ClassNotFoundException, SQLException{
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        String url = ConfigUtil.getValue("connection.derby.localhost.url");

        return getConnection(url);
    }
    
    /**
     * 获得服务器192.168.1.250上连接
     * @return
     * @throws ClassNotFoundException 
     * @throws SQLException 
     */
    public static Connection getConnection_Mysql_CrawlServer_205() throws ClassNotFoundException, SQLException{
        Class.forName("org.gjt.mm.mysql.Driver"); 
        String url = ConfigUtil.getValue("mysql.CrawlDatabase.localoffice.url");
        String userName = ConfigUtil.getValue("mysql.CrawlDatabase.localoffice.userName");
        String userPassword = ConfigUtil.getValue("mysql.CrawlDatabase.localoffice.userPassword");

        return getConnection(url,userName,userPassword);
    }
    

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		// new SMSBuyConfig(true);
		Connection con = null;

		 con = getConnection_Mysql_CrawlServer_Local();
		//con = getConnection_Sqlserver_WebServer();
		// con=getConnection_Mysql_CrawlServer_Lelong();
        //con=getConnection_Mysql_CrawlServer_205();
		System.out.println(con);
		closeConnection(con, null, null);

	}
}
