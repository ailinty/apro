package com.joeysoft.kc868.db;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joeysoft.kc868.common.GlobalConst;
import com.joeysoft.kc868.common.GlobalMethod;
import com.joeysoft.kc868.common.SyncSimpleDateFormat;

public class SQLUtil {
	private static Logger logger = LoggerFactory.getLogger(SQLUtil.class);
	
    public static final String strcSQL_OB = " (";
    public static final String strcSQL_EQ_QM = " = ? ";
    public static final String strcSQL_EQ_NULL = " = NULL ";
    public static final String strcSQL_AND_OB = " AND ( ";
    public static final String strcSQL_AND = " AND ";
    public static final String strcSQL_FROM = " FROM ";
    public static final String strcSQL_OR = " OR ";
    public static final String strcSQL_SET = " SET ";
    public static final String strcSQL_SET_MNT_WHERE = " SET MNT_STTS_F = MNT_STTS_F WHERE ";
    public static final String strcSQL_VALUES = " VALUES (";
    public static final String strcSQL_WHERE = " WHERE ";
    public static final String strcSQL_CB = ") ";
    public static final String strcSQL_COMMA_QM = ",?";
    public static final String strcSQL_QM = "?";
    public static final String strcSQL_EQ = "=";
    public static final String strc1900 = "1900";
    public static final String strcSQL_DELETE = "DELETE FROM ";
    public static final String strcSQL_INSERT = "INSERT INTO ";
    public static final String strcSQL_NaN = "NaN";
    public static final String strcSQL_NULL = "NULL";
    public static final String strcSQL_SELECT = "SELECT ";
    public static final String strcSQL_UPDATE = "UPDATE ";

    private static final String strcSP1 = "{call ";
    private static final String strcSP2 = "(";
    private static final String strcSP3 = ")}";

    private static final String strc6Zero = "000000";
    private static final String strc7Zero = "0000000";

    private static int intcTimeChk = 1000;
    
	/**
     * Description : Execute a insert sql with the specific table name and field
     * param
     * 
     * @param strType
     *            String
     * @param strTableName
     *            String
     * @param htParam
     *            Map
     */
    public static void insertSQL(Connection conDB , String strTableName , Map htParam ) throws Exception
    {
        try
        {
            StringBuffer sbSQL = new StringBuffer( 100 );
            StringBuffer sbSQL1 = new StringBuffer( 100 );
            StringBuffer sbSQL2 = new StringBuffer( 100 );
            StringBuffer sbTemp = new StringBuffer( 100 );
            Object obj;
            Object objParam;

            List vecParam = new Vector();
            Iterator itr = htParam.keySet().iterator();
            while( itr.hasNext() )
            {
                obj = itr.next();
                objParam = htParam.get( obj );

                sbSQL1.append( ( sbSQL1.length() > 0 ) ? GlobalConst.CONST_STRING_COMMA : GlobalConst.CONST_STRING_EMPTY ).append( obj.toString() );

                if ( objParam == null )
                {
                    sbSQL2.append( ( sbSQL2.length() > 0 ) ? GlobalConst.CONST_STRING_COMMA : GlobalConst.CONST_STRING_EMPTY ).append( strcSQL_NULL );
                }
                else
                {
                    sbSQL2.append( ( sbSQL2.length() > 0 ) ? GlobalConst.CONST_STRING_COMMA : GlobalConst.CONST_STRING_EMPTY ).append( strcSQL_QM );
                    if ( objParam.getClass() == java.lang.Double.class )
                    {
                        if ( Double.valueOf( objParam.toString() ).isNaN() )
                        {
                            vecParam.add( objParam.toString() );
                        }
                        else
                        {
                            vecParam.add( objParam );
                        }
                    }
                    else if ( objParam instanceof java.util.Date )
                    {
                        vecParam.add( objParam );
                    }
                    else
                    {
                        vecParam.add( objParam.toString() );
                    }
                }
            }
            if ( ( sbSQL1.length() > 0 ) && ( sbSQL2.length() > 0 ) )
            {
                sbTemp.setLength( 0 );
                sbTemp.append( strcSQL_INSERT ).append( strTableName ).append( strcSQL_OB ).append( sbSQL1.toString() ).append( strcSQL_CB );
                sbSQL.append( sbTemp.toString() );
                sbSQL.append( strcSQL_VALUES ).append( sbSQL2.toString() ).append( strcSQL_CB );
            }
            executeSQL( conDB , vecParam , sbSQL.toString() );
        }
        catch( SQLException e )
        {
            if ( htParam != null )
            {
                logger.error( "SQLUtil (insertSQL:SQLException) - " + e.getMessage() + htParam.toString() );
            }
            else
            {
                logger.error( "SQLUtil (insertSQL:SQLException) - " + e.getMessage() );
            }
            throw e;
        }
        catch( Exception e )
        {
            if ( htParam != null )
            {
                logger.error( "SQLUtil (insertSQL:Exception) - " + e.getMessage() + htParam.toString() );
            }
            else
            {
                logger.error( "SQLUtil (insertSQL:Exception) - " + e.getMessage() );
            }
            throw e;
        }
    }
    
    /**
     * Description : Execute a prepare sql statement with input params (use
     * executeSQLReturnCount as the flow is the same)
     * 
     * @param conDB
     *            Connection
     * @param aParameters
     *            List
     * @param aSQL
     *            String - SQL statement
     */

    public static void executeSQL( Connection conDB , String aSQL ) throws Exception
    {
        executeSQLReturnCount( conDB , null , aSQL );
    }

    public static void executeSQL( Connection conDB , Object param0 , String aSQL ) throws Exception
    {
        Vector aParameters = new Vector( 2 );
        aParameters.add( param0 );
        executeSQL( conDB , aParameters , aSQL );
    }

    public static void executeSQL( Connection conDB , Object param0 , Object param1 , String aSQL ) throws Exception
    {
        Vector aParameters = new Vector( 3 );
        aParameters.add( param0 );
        aParameters.add( param1 );
        executeSQL( conDB , aParameters , aSQL );
    }

    public static void executeSQL( Connection conDB , Object param0 , Object param1 , Object param2 , String aSQL ) throws Exception
    {
        Vector aParameters = new Vector( 4 );
        aParameters.add( param0 );
        aParameters.add( param1 );
        aParameters.add( param2 );
        executeSQL( conDB , aParameters , aSQL );
    }

    public static void executeSQL( Connection conDB , Object param0 , Object param1 , Object param2 , Object param3 , String aSQL ) throws Exception
    {
        Vector aParameters = new Vector( 5 );
        aParameters.add( param0 );
        aParameters.add( param1 );
        aParameters.add( param2 );
        aParameters.add( param3 );
        executeSQL( conDB , aParameters , aSQL );
    }

    public static void executeSQL( Connection conDB , Object param0 , Object param1 , Object param2 , Object param3 , Object param4 , String aSQL ) throws Exception
    {
        Vector aParameters = new Vector( 6 );
        aParameters.add( param0 );
        aParameters.add( param1 );
        aParameters.add( param2 );
        aParameters.add( param3 );
        aParameters.add( param4 );
        executeSQL( conDB , aParameters , aSQL );
    }

    public static void executeSQL( Connection conDB , List aParameters , String aSQL ) throws Exception
    {
        executeSQLReturnCount( conDB , aParameters , aSQL );
    }
    
    /**
     * Description : Return the row count for the data retrieve with the sql
     * statement provided
     * 
     * @param conDB
     *            Connection
     * @param aParameters
     *            List
     * @param aSQL
     *            String - SQL statement
     * @return int
     */
    public static int executeSQLReturnCount( Connection conDB , List aParameters , String aSQL ) throws Exception
    {
        int count = 0;
        PreparedStatement statAction = null;
        try
        {
            long lStart = System.currentTimeMillis();
            long lEnd = 0;
            long lFlagId = lStart;

            String strLogDefault = GlobalConst.CONST_STRING_EMPTY;
            StringBuffer sbLog = new StringBuffer( 100 );

            if ( conDB == null )
            {
                throw new Exception( "No Active Connection." );
            }
            
            statAction = conDB.prepareStatement( aSQL );
            if ( aParameters != null )
            { //set parameters if any
                int intSize = aParameters.size();
                for ( int i = 0; i < intSize; i++ )
                {
                    setParameter( statAction , i + 1 , aParameters.get( i ) );
                }
            }
            
            logger.debug( "SQLUtil (executeSQLReturnCount:SQL) - " + aSQL);
            count = statAction.executeUpdate();
        }
        catch( SQLException e )
        {
            e.printStackTrace( System.out );
            logger.error( "SQLUtil (executeSQLReturnCount:SQLException) - " + e.getMessage() + aSQL + aParameters );
        }
        catch( Exception e )
        {
            e.printStackTrace( System.out );
            logger.error( "SQLUtil (executeSQLReturnCount:Exception) - " + e.getMessage() + aSQL + aParameters );
            throw new Exception( "Cannot execute SQL. executeSQL : " + e.getMessage() + "|" + aSQL );
        }
        finally
        {
            try
            {
                statAction.close();
                statAction = null;
            }
            catch( Exception e )
            {
                logger.error( e.toString() );
            }
        }
        return count;
    }
    
    /**
     * Description : Set parameter into a prepare statement object
     * 
     * @param aStat
     *            PreparedStatement
     * @param aPos
     *            int
     * @param aParameter
     *            Object
     */
    public static void setParameter( PreparedStatement aStat , int aPos , Object aParameter ) throws Exception
    {
        try
        {
            if ( aParameter == null )
            {
                /**
                 * REMARKED BY RAYMOND (20030415) - CANNOT GET CLASS FROM NULL
                 * VALUE if (aParameter.getClass() == java.sql.Timestamp.class)
                 * aStat.setNull(aPos, java.sql.Types.TIMESTAMP); else if
                 * (aParameter.getClass() == java.lang.Integer.class)
                 * aStat.setNull(aPos, java.sql.Types.INTEGER); else if
                 * (aParameter.getClass() == java.lang.Double.class){
                 * aStat.setNull(aPos, java.sql.Types.DOUBLE); } else
                 */
                aStat.setNull( aPos , java.sql.Types.VARCHAR );
            }
            else
            {

                // MSSQL
                String strParm = aParameter.toString();
                Object paramClass = aParameter.getClass();

                    if ( paramClass == java.sql.Date.class )
                    {
                        aStat.setTimestamp( aPos , new java.sql.Timestamp( ( (java.sql.Date) aParameter ).getTime() ) );
                    }
                    else if ( paramClass == java.math.BigInteger.class )
                    {
                        aStat.setInt( aPos , Integer.parseInt( strParm ) );
                    }
                    else if ( paramClass == java.lang.Double.class )
                    {
                        if ( strParm.equalsIgnoreCase( strcSQL_NaN ) )
                        {
                            aStat.setBigDecimal( aPos , new BigDecimal( 0 ));
                        }
                        else
                        {
                            if ( strParm.indexOf( 'E' ) > 0 )
                            {
                                aStat.setBigDecimal( aPos , new BigDecimal( strParm ) );
                            }
                            else
                            {
                                aStat.setBigDecimal( aPos , new BigDecimal( strParm + strc7Zero ) );
                            }
                        }
                    }
                    else
                    {
                        aStat.setObject( aPos , aParameter );
                    }
                }
        }
        catch( SQLException e )
        {
            e.printStackTrace( System.out );
            logger.error( "SQLUtil (setParameter:SQLException) - " + e.getMessage() + aParameter );
        }
        catch( Exception e )
        {
            e.printStackTrace( System.out );
            logger.error( "SQLUtil (setParameter:Exception) - " + e.getMessage() );
            throw new Exception( "Cannot execute SQL. executeSQL : " + e.getMessage() );
        }
    }
    
    /**
     * Description : Execute a prepare update sql statement with specific table
     * name and param
     * 
     * @param conDB
     *            Connection
     * @param strTableName
     *            String
     * @param htUpdateParam
     *            Map
     * @param htKeyParam
     *            Map
     */
    public static void updateSQL( Connection conDB , String strTableName , Map htUpdateParam , Map htKeyParam ) throws Exception
    {
        updateSQLReturnCount( conDB , strTableName , htUpdateParam , htKeyParam );
    }

    public static int updateSQLReturnCount( Connection conDB , String strTableName , Map htUpdateParam , Map htKeyParam ) throws Exception
    {
        List vecParam = new Vector();
        int intCount = 0;
        try
        {
            StringBuffer sbSQL = new StringBuffer( 100 );
            StringBuffer sbSQL1 = new StringBuffer( 100 );
            StringBuffer sbSQL2 = new StringBuffer( 100 );
            //StringBuffer sbTemp = new StringBuffer(100);

            Object obj;
            Object objParam;

            //      Enumeration enum = htUpdateParam.keys();
            //      while (enum.hasMoreElements ()){
            //        obj = enum.nextElement ();
            Iterator itr = htUpdateParam.keySet().iterator();
            while( itr.hasNext() )
            {
                obj = itr.next();
                objParam = htUpdateParam.get( obj );

                if ( objParam != null && objParam.toString().trim().length() > 0 )
                {
                    sbSQL1.append( ( sbSQL1.length() > 0 ) ? GlobalConst.CONST_STRING_COMMA : GlobalConst.CONST_STRING_EMPTY ).append( obj.toString() ).append( strcSQL_EQ_QM );
                    vecParam.add( objParam );
                }
                else if ( objParam != null && objParam.toString().trim().length() == 0 )
                {
                    sbSQL1.append( ( sbSQL1.length() > 0 ) ? GlobalConst.CONST_STRING_COMMA : GlobalConst.CONST_STRING_EMPTY ).append( obj.toString() ).append( strcSQL_EQ_NULL );
                }
            }

            //      enum = htKeyParam.keys();
            //      while (enum.hasMoreElements ()){
            //        obj = enum.nextElement ();
            itr = htKeyParam.keySet().iterator();
            while( itr.hasNext() )
            {
                obj = itr.next();
                objParam = htKeyParam.get( obj );
                if ( objParam != null )
                {
                    sbSQL2.append( ( sbSQL2.length() > 0 ) ? strcSQL_AND : GlobalConst.CONST_STRING_EMPTY ).append( obj.toString() ).append( strcSQL_EQ_QM );
                    vecParam.add( objParam );
                }
            }

            if ( ( sbSQL1.length() > 0 ) && ( sbSQL2.length() > 0 ) )
            {
                sbSQL.append( strcSQL_UPDATE ).append( strTableName ).append( strcSQL_SET ).append( sbSQL1.toString() ).append( strcSQL_WHERE ).append( sbSQL2.toString() );
                intCount = executeSQLReturnCount( conDB , vecParam , sbSQL.toString() );
            }
            return intCount;
        }
        catch( SQLException e )
        {
            if ( htUpdateParam != null )
            {
                logger.error( "SQLUtil (updateSQL:SQLException) - " + e.getMessage() + htUpdateParam.toString() );
            }
            else
            {
                logger.error( "SQLUtil (updateSQL:SQLException) - " + e.getMessage() );
            }
            throw e;
        }
        catch( Exception e )
        {
            if ( htUpdateParam != null )
            {
                logger.error( "SQLUtil (updateSQL:Exception) - " + e.getMessage() + htUpdateParam.toString() );
            }
            else
            {
                logger.error( "SQLUtil (updateSQL:Exception) - " + e.getMessage() );
            }
            throw e;
        }
        finally
        {
            vecParam.clear();
            vecParam = null;
        }
    }

    /**
     * Description : Execute a prepare delete sql statement with specific table
     * name and param
     * 
     * @param conDB
     *            Connection
     * @param strTableName
     *            String
     * @param htParam
     *            Map
     */
    public static void deleteSQL( Connection conDB , String strTableName , Map htParam ) throws Exception
    {
        List vecParam = new Vector();
        try
        {
            StringBuffer sbSQL = new StringBuffer( 100 );
            StringBuffer sbTemp = new StringBuffer( 100 );
            Object obj;
            Object objParam;

            //      Enumeration enum = htParam.keys();
            boolean blnFirstTime = true;
            //      while (enum.hasMoreElements ()){
            //        obj = enum.nextElement ()
            //			Iterator itr = htParam.keySet().iterator();
            //			while (itr.hasNext()) {
            //				obj = itr.next();
            Iterator itr = htParam.keySet().iterator();
            while( itr.hasNext() )
            {
                obj = itr.next();
                objParam = htParam.get( obj );

                if ( !blnFirstTime )
                {
                    sbSQL.append( strcSQL_AND );
                }
                sbSQL.append( obj.toString() ).append( strcSQL_EQ );
                if ( objParam == null )
                {
                    sbSQL.append( strcSQL_NULL );
                }
                else
                {
                    sbSQL.append( strcSQL_QM );
                    vecParam.add( objParam );
                }
                if ( blnFirstTime )
                {
                    blnFirstTime = false;
                }
            }
            sbTemp.append( strcSQL_DELETE ).append( strTableName );
            if(sbSQL.length() > 0 ){
            	sbTemp.append( strcSQL_WHERE ).append( sbSQL.toString() );
            }
           
            executeSQL( conDB , vecParam , sbTemp.toString() );
        }
        catch( SQLException e )
        {
            if ( htParam != null )
            {
                logger.error( "SQLUtil (deleteSQL:SQLException) - " + e.getMessage() + htParam.toString() );
            }
            else
            {
                logger.error( "SQLUtil (deleteSQL:SQLException) - " + e.getMessage() );
            }
            throw e;
        }
        catch( Exception e )
        {
            if ( htParam != null )
            {
                logger.error( "SQLUtil (deleteSQL:Exception) - " + e.getMessage() + htParam.toString() );
            }
            else
            {
                logger.error( "SQLUtil (deleteSQL:Exception) - " + e.getMessage() );
            }
            throw e;
        }
        finally
        {
            vecParam.clear();
            vecParam = null;
        }
    }
    
    /**
	 * 
	 * @param conDB
	 * @param param0
	 * @param baRtnNull
	 * @param straSQL
	 * @return
	 * @throws Exception
	 */
	public static Hashtable getFirstRecord(Connection conDB, Object param0,
			boolean baRtnNull, String straSQL) throws Exception {
		List aParameters = new Vector(2);
		aParameters.add(param0);
		return getFirstRecord(conDB, aParameters, baRtnNull, straSQL);
	}

	public static Hashtable getFirstRecord(Connection conDB, Object param0,
			Object param1, boolean baRtnNull, String straSQL) throws Exception {
		List aParameters = new Vector(3);
		aParameters.add(param0);
		aParameters.add(param1);
		return getFirstRecord(conDB, aParameters, baRtnNull, straSQL);
	}

	public static Hashtable getFirstRecord(Connection conDB, Object param0,
			Object param1, Object param2, boolean baRtnNull, String straSQL)
			throws Exception {
		List aParameters = new Vector(4);
		aParameters.add(param0);
		aParameters.add(param1);
		aParameters.add(param2);
		return getFirstRecord(conDB, aParameters, baRtnNull, straSQL);
	}

	public static Hashtable getFirstRecord(Connection conDB, Object param0,
			Object param1, Object param2, Object param3, boolean baRtnNull,
			String straSQL) throws Exception {
		List aParameters = new Vector(5);
		aParameters.add(param0);
		aParameters.add(param1);
		aParameters.add(param2);
		aParameters.add(param3);
		return getFirstRecord(conDB, aParameters, baRtnNull, straSQL);
	}

	/**
	 * 
	 * @param conDB
	 * @param aParameters
	 * @param straSQL
	 * @return Hashtable
	 * @throws Exception
	 */
	public static Hashtable getFirstRecord(Connection conDB, List aParameters,
			boolean baRtnNull, String straSQL) throws Exception {
		return new Hashtable(getFirstRecordinMap(conDB, aParameters, baRtnNull,
				straSQL));
	}

	/**
	 * 
	 * @param conDB
	 * @param aParameters
	 *            List
	 * @param straSQL
	 *            String
	 * @return Map
	 * @throws Exception
	 */
	public static Map getFirstRecordinMap(Connection conDB, List aParameters,
			boolean baRtnNull, String straSQL) throws Exception {
		Statement stmStat = null;
		ResultSet rsData = null;
		HashMap hmData = new HashMap();

		try {
			long lStart = System.currentTimeMillis();
			long lEnd = 0;
			long lFlagId = lStart;

			String strLogDefault = GlobalConst.CONST_STRING_EMPTY;
			StringBuffer sbLog = new StringBuffer(100);


			/*if (GlobalConst.DB_ORACLE.equals(getDBType())) {
				// gary 23/07/03 - get the first 1 record only
				straSQL = ORACLE_DatabaseUtil.selectTopN(straSQL, 1);
			} else if (GlobalConst.DB_MSSQL.equals(getDBType())) {
				straSQL = MSSQL_DatabaseUtil.selectTopN(straSQL, 1);
			}*/
			
			if (aParameters == null) {
				stmStat = conDB.prepareStatement(straSQL);
				rsData = ((PreparedStatement) stmStat).executeQuery();
			} else {
				stmStat = conDB.prepareStatement(straSQL);

				for (int i = 0, intSize = aParameters.size(); i < intSize; i++) {
					setParameter((PreparedStatement) stmStat, i + 1,
							aParameters.get(i));
				}
				lEnd = System.currentTimeMillis();
				lStart = System.currentTimeMillis();
				rsData = ((PreparedStatement) stmStat).executeQuery();
			}

			lEnd = System.currentTimeMillis();
			if (rsData.next()) {
				FillHashTable(hmData, rsData, baRtnNull);
			}

			lEnd = System.currentTimeMillis();
			long lTimeConsumed = lEnd - lStart;

			return hmData;
		} catch (SQLException e) {
			logger.error("SQLUtil (getFirstRecordinMap:SQLException) - "
					+ e.getMessage() + straSQL + aParameters);
			throw new SQLException( "Cannot execute SQL. executeSQL : " + e.getMessage() );
		} catch (Exception e) {
			logger.error("SQLUtil (getFirstRecordinMap:Exception) - "
					+ e.getMessage());
			throw new Exception("Cannot execute SQL. executeSQL : "
					+ e.getMessage());
		} finally {
			try {
				rsData.close();
				stmStat.close();
				rsData = null;
				stmStat = null;
			} catch (Exception e) {
				logger.error(e.toString());
			}
		}
	}
	
    /**
     * Description : Execute a select sql statement and retrieve Data in vector
     * 
     * @param conDB
     *            Connection
     * @param aParameters
     *            List
     * @param straSQL
     *            string
     * @return Vector
     */
    public static Vector selectSQL( Connection conDB , List aParameters , String straSQL ) throws Exception
    {
        return selectSQL( conDB , aParameters , straSQL , false , null );
    }

    /**
     * Description : Execute a select sql statement and retrieve Data in vector
     * 
     * @param conDB
     *            Connection
     * @param aParameters
     *            List
     * @param straSQL
     *            string
     * @param baRtnNull
     *            boolean - if true, put(xxx, GlobalConst.CONST_STRING_EMPTY)
     *            for those null value
     * @return Vector
     */
    public static Vector selectSQL( Connection conDB , List aParameters , String straSQL , boolean baRtnNull ) throws Exception
    {
        return selectSQL( conDB , aParameters , straSQL , baRtnNull , null );
    }

    /**
     * Description : Execute a select sql statement and retrieve Data in vector
     * 
     * @param conDB
     *            Connection
     * @param aParameters
     *            List
     * @param straSQL
     *            string
     * @param baRtnNull
     *            boolean - if true, put(xxx, GlobalConst.CONST_STRING_EMPTY)
     *            for those null value
     * @return Vector
     */
    
    public static Vector selectSQL( Connection conDB , List aParameters , String straSQL , boolean baRtnNull , Map htaDataAccCtl ) throws Exception
    {
			List lst = selectSQLInList(conDB , aParameters , straSQL , baRtnNull , htaDataAccCtl);
			return new Vector(lst);
    }
 
    /**
     * Description : Execute a select sql statement and retrieve Data in vector
     * 
     * @param conDB
     *            Connection
     * @param aParameters
     *            List
     * @param straSQL
     *            string
     * @return Vector
     */
    public static List selectSQLInList( Connection conDB , List aParameters , String straSQL , Map htaDataAccCtl ) throws Exception
    {
        return selectSQLInList( conDB , aParameters , straSQL , false , htaDataAccCtl );
    }
    
    public static List selectSQLInList( Connection conDB , String straSQL ) throws Exception
    {
        return selectSQLInList( conDB , null , straSQL );
    }

    public static List selectSQLInList( Connection conDB , Object param0 , String straSQL ) throws Exception
    {
        List aParameters = new ArrayList( 2 );
        aParameters.add( param0 );
        return selectSQLInList( conDB , aParameters , straSQL );
    }

    public static List selectSQLInList( Connection conDB , Object param0 , Object param1 , String straSQL ) throws Exception
    {
    	List aParameters = new ArrayList( 3 );
        aParameters.add( param0 );
        aParameters.add( param1 );
        return selectSQLInList( conDB , aParameters , straSQL );
    }

    public static List selectSQLInList( Connection conDB , Object param0 , Object param1 , Object param2 , String straSQL ) throws Exception
    {
    		List aParameters = new ArrayList( 4 );
        aParameters.add( param0 );
        aParameters.add( param1 );
        aParameters.add( param2 );
        return selectSQLInList( conDB , aParameters , straSQL );
    }

    public static List selectSQLInList( Connection conDB , Object param0 , Object param1 , Object param2 , Object param3 , String straSQL ) throws Exception
    {
    		List aParameters = new ArrayList( 5 );
        aParameters.add( param0 );
        aParameters.add( param1 );
        aParameters.add( param2 );
        aParameters.add( param3 );
        return selectSQLInList( conDB , aParameters , straSQL );
    }

    public static List selectSQLInList( Connection conDB , Object param0 , Object param1 , Object param2 , Object param3 , Object param4 , String straSQL ) throws Exception
    {
    		List aParameters = new ArrayList( 6 );
        aParameters.add( param0 );
        aParameters.add( param1 );
        aParameters.add( param2 );
        aParameters.add( param3 );
        aParameters.add( param4 );
        return selectSQLInList( conDB , aParameters , straSQL );
    }

    
    /**
     * Description : Execute a select sql statement and retrieve Data in List
     * 
     * @param conDB Connection
     * @param aParameters List
     * @param straSQL string
     * @return List
     */
    
    public static List selectSQLInList( Connection conDB , List aParameters , String straSQL ) throws Exception
    {
			return selectSQLInList(conDB , aParameters , straSQL , false , null);
    }   
    
    /**
     * Description : Execute a select sql statement and retrieve Data in List
     * 
     * @param conDB Connection
     * @param aParameters List
     * @param straSQL string
     * @param straSQL boolean - if true, put(xxx, GlobalConst.CONST_STRING_EMPTY)
     *            for those null value
     * @return List
     */
    
    public static List selectSQLInList( Connection conDB , List aParameters , String straSQL ,boolean baRtnNull ) throws Exception
    {
			return selectSQLInList(conDB , aParameters , straSQL , baRtnNull , null);
    } 
    
    public static List selectSQLInList( Connection conDB , List aParameters , String straSQL , boolean baRtnNull , Map htaDataAccCtl ) throws Exception
    {
        Statement stmStat = null;
        ResultSet rsData = null;
        Map htData = new Hashtable();
				List lstData = new ArrayList();
        try
        {
            long lStart = System.currentTimeMillis();
            long lEnd = 0;
            long lFlagId = lStart;

            String strLogDefault = GlobalConst.CONST_STRING_EMPTY;
            StringBuffer sbLog = new StringBuffer( 100 );

            if ( aParameters == null )
            {
                stmStat = conDB.createStatement();
                rsData = stmStat.executeQuery( straSQL );
            }
            else
            {
                stmStat = conDB.prepareStatement( straSQL );
                for ( int i = 0, intSize = aParameters.size(); i < intSize; i++ )
                {
                    setParameter( (PreparedStatement) stmStat , i + 1 , aParameters.get( i ) );
                }
                rsData = ( (PreparedStatement) stmStat ).executeQuery();
            }

            while( rsData.next() )
            {
                if ( baRtnNull )
                {
                    FillHashTable( htData , rsData , true );
                }
                else
                {
                    FillHashTable( htData , rsData );
                }
								lstData.add( htData );
                htData = new Hashtable();
            }

            lEnd = System.currentTimeMillis();
            long lTimeConsumed = lEnd - lStart;

            return lstData;
        }
        catch( SQLException e )
        {
            e.printStackTrace();
            logger.error( "SQLUtil (selectSQL:SQLException) - " + straSQL + " | " + aParameters + " | " + e.getMessage() );
        }
        catch( Exception e )
        {
            e.printStackTrace();
            logger.error( "SQLUtil (selectSQL:Exception) - " + straSQL + e.getMessage() );
            throw new Exception( "Cannot execute SQL. executeSQL : " + e.getMessage() );
        }
        finally
        {
            try
            {
                rsData.close();
                stmStat.close();
                rsData = null;
                stmStat = null;
            }
            catch( Exception e )
            {
                logger.error( e.toString() );
            }
        }
		return lstData;
    }
    
    

    /**
     * Description : Execute a select sql statement and retrieve Data in vector
     * 
     * @param conDB
     *            Connection
     * @param straSQL
     *            string
     * @return Vector
     */
    public static Vector selectSQL( Connection conDB , String straSQL ) throws Exception
    {
        return selectSQL( conDB , null , straSQL );
    }
    
    /**
     * Description : Common route to add integer object into a Map
     * 
     * @param aHashtable
     *            Map
     * @param objKey
     *            Object - key field name
     * @param intaInputParam
     *            int - field value
     */
    public static void FillHashTable( Map aHashtable , Object objKey , int intaInputParam )
    {
        aHashtable.put( objKey , new Integer( intaInputParam ) );
    }

    /**
     * Description : Common route to add double object into a Map
     * 
     * @param aHashtable
     *            Map
     * @param objKey
     *            Object - key field name
     * @param dbaInputParam
     *            double - field value
     */
    public static void FillHashTable( Map aHashtable , Object objKey , double dbaInputParam )
    {
        aHashtable.put( objKey , new Double( dbaInputParam ) );
    }

    /**
     * Description : Common route to add string object into a Map
     * 
     * @param aHashtable
     *            Map
     * @param objKey
     *            Object - key field name
     * @param htaData
     *            Map
     * @param straField
     *            String
     */
    public static void FillHashTable( Map aHashtable , Object objKey , Map htaData , String straField )
    {
        FillHashTable( aHashtable , objKey , htaData , straField , false );
    }

    /**
     * Description : Common route to add string object into a Map
     * 
     * @param aHashtable
     *            Map
     * @param objKey
     *            Object - key field name
     * @param htaData
     *            Map
     * @param straField
     *            String
     */
    public static void FillHashTable_double( Map aHashtable , Object objKey , Map htaData , String straField )
    {
        Object aParameter = htaData.get( straField );
        if ( aParameter != null && !aParameter.equals( GlobalConst.CONST_STRING_EMPTY ) )
        {
            String strParamter = aParameter.toString();
            if ( strParamter.equalsIgnoreCase( strcSQL_NaN ) )
            {
                aHashtable.put( objKey , new BigDecimal(0) );
            }
            else
            {
                aHashtable.put( objKey , new BigDecimal( strParamter ) );
            }
        }
    }

    /**
     * Description : Common route to add string object into a Map
     * 
     * @param aHashtable
     *            Map
     * @param objKey
     *            Object - key field name
     * @param htaData
     *            Map
     * @param straField
     *            String
     * @param baIgnorlEmpty
     *            boolean
     */
    public static void FillHashTable( Map aHashtable , Object objKey , Map htaData , String straField , boolean baIgnorlEmpty )
    {
        Object aParameter = htaData.get( straField );
        if ( aParameter != null )
        {
            if ( baIgnorlEmpty )
            {
                String strParameter = aParameter.toString();
                if ( !strParameter.equals( GlobalConst.CONST_STRING_EMPTY ) )
                {
                    if ( aParameter.getClass() == java.lang.Double.class )
                    {
                        if ( strParameter.equalsIgnoreCase( strcSQL_NaN ) )
                        {
                            aHashtable.put( objKey , new BigDecimal(0) );
                        }
                        else
                        {
                            if ( aParameter.toString().indexOf( 'E' ) > 0 )
                            {
                                aHashtable.put( objKey , new BigDecimal( strParameter ) );
                            }
                            else
                            {
                                aHashtable.put( objKey , new BigDecimal( strParameter + strc6Zero ) );
                            }
                        }
                    }
                    else
                    {
                        aHashtable.put( objKey , aParameter );
                    }
                }
            }
            else
            {
                aHashtable.put( objKey , aParameter );
            }
        }
    }

    /**
     * Description : Common route to add object into a Map. If the data type of
     * the object is String, it would ignore the empty string when adding object
     * into the Map.
     * 
     * @param aHashtable
     *            Map
     * @param objKey
     *            Object - key field name
     * @param htaData
     *            Map
     * @param straField
     *            String
     * @param :
     *            aClass Class - Data type of the objKey. if Null, default data
     *            type is string. if double, return 0 when null. if integer,
     *            return 0 when null. if Date, return null when null.
     */
    public static void FillHashTable( Map aHashtable , Object objKey , Map htaData , String straField , Class aClass )
    {
        FillHashTable( aHashtable , objKey , htaData , straField , true , aClass );
    }

    /**
     * Description : Common route to add object into a Map
     * 
     * @param aHashtable
     *            Map
     * @param objKey
     *            Object - key field name
     * @param htaData
     *            Map
     * @param straField
     *            String
     * @param baIgnorlEmpty
     *            boolean
     * @param :
     *            aClass Class - Data type of the objKey. if Null, default data
     *            type is string. if double, return 0 when null. if integer,
     *            return 0 when null. if Date, return null when null.
     */
    public static void FillHashTable( Map aHashtable , Object objKey , Map htaData , String straField , boolean baIgnorlEmpty , Class aClass )
    {
        if ( aClass == null )
        {
            FillHashTable( aHashtable , objKey , htaData , straField , baIgnorlEmpty );
        }
        else if ( ( aClass == java.sql.Date.class ) || ( aClass == java.util.Date.class ) )
        {
            Date objDate = returnDate( htaData , straField );
            if ( objDate != null )
                aHashtable.put( objKey , objDate );
        }
        else if ( aClass == java.lang.Double.class )
        {
            aHashtable.put( objKey , new Double( returnDouble( htaData , straField ) ) );
        }
        else if ( aClass == java.lang.Integer.class )
        {
            if ( baIgnorlEmpty == true )
            {
                aHashtable.put( objKey , new Integer( returnInt( htaData , straField ) ) );
            }
        }
        else
        {
            FillHashTable( aHashtable , objKey , htaData , straField , baIgnorlEmpty );
        }
    }

    /**
     * Description : Common route to add resultset object into a Map
     * 
     * @param aHashtable
     *            Map
     * @param aResultSet
     *            ResultSet
     * @return boolean
     */
    public static boolean FillHashTable( Map aHashtable , ResultSet aResultSet )
    {
        return FillHashTable( aHashtable , aResultSet , false );
    }

    /**
     * Description : Common route to add resultset object into a Map
     * 
     * @param aHashtable
     *            Map
     * @param aResultSet
     *            ResultSet
     * @param baIgnorlEmpty
     *            boolean
     * @return boolean
     */
    public static boolean FillHashTable( Map aHashtable , ResultSet aResultSet , boolean baFillNull )
    {
        Object tmpObj;
        String str = GlobalConst.CONST_STRING_EMPTY;
        //    boolean bCheckUpperCase = true;
        //		boolean bisUpperCase = false;

        try
        {
            ResultSetMetaData MetaData = aResultSet.getMetaData();
            //gary 20/07/03 - it affects performance if multiple callers calls
            // this method
            //Thread.sleep(2);

            for ( int i = 1, intCount = MetaData.getColumnCount(); i <= intCount; i++ )
            {
                str = MetaData.getColumnName( i );
                tmpObj = aResultSet.getObject( i );
                if ( tmpObj != null )
                {
                    try
                    {
                        Object tmp = DataUtil.convertValue( tmpObj.getClass() , tmpObj );
                        if ( tmp != null )
                            tmpObj = tmp;
                    }
                    catch( Exception e )
                    {
                        logger.error( "SQLUtil FillHashTable error " + e );
                    }
                    aHashtable.put( str , tmpObj );
                }
                else
                {
                    if ( baFillNull )
                    {
                        aHashtable.put( str , GlobalConst.CONST_STRING_EMPTY );
                    }
                }
            }
        }
        catch( SQLException e )
        {
            logger.error( "SQLUtil (FillHashTable:SQLException) - " + e.getMessage() );
            return false;
        }
        catch( Exception e )
        {
            logger.error( "SQLUtil (FillHashTable:Exception) - " + e.getMessage() );
            return false;
        }
        return true;
    }
    
    /**
     * Description : Return a string object from a hashtable
     * 
     * @param htData
     *            Map
     * @param strFieldName
     *            String
     * @return String
     */
    public static String returnStr( Map htData , String strFieldName )
    {
        return DataUtil.formatToString( htData.get( strFieldName ) );
    }
    
    public static String returnNullOrStr( Map htData , String strFieldName )
    {
        return DataUtil.formatToNullOrString( htData.get( strFieldName ) );
    }
    
    public static String returnNullOrStr( String strFieldName )
    {
        if( strFieldName == null || strFieldName.length() == 0){
            return null;
        }
        else{
            return strFieldName;
        }
    }
    

    public static BigDecimal returnBigDecimal( Map htaData , String straFieldName )
    {
        return GlobalMethod.ifNullBigDec( htaData.get( straFieldName ) );
    }

    /**
     * Description : Return a double object from a hashtable
     * 
     * @param htData
     *            Map
     * @param strFieldName
     *            String
     * @return double
     */
    public static double returnDouble( Map htData , String strFieldName )
    {
        return returnDouble( htData.get( strFieldName ) );

    }

    /**
     * Description : Convert a object into a double object
     * 
     * @param obj
     *            object
     * @return double
     */
    public static double returnDouble( Object obj )
    {
        if ( obj != null )
        {
            String strTemp = obj.toString();
            if ( strTemp.length() > 0 )
            {
                return Double.parseDouble( strTemp );
            }
        }
        return 0;
    }

    /**
     * Description : Return a int object from a hashtable
     * 
     * @param htData
     *            Map
     * @param strFieldName
     *            String
     * @return int
     */
    public static int returnInt( Map htData , String strFieldName )
    {
        return returnInt( htData.get( strFieldName ) );
    }

    /**
     * Description : Convert a integer object into a integer object
     * 
     * @param obj
     *            object
     * @return int
     */
    public static int returnInt( Object obj )
    {
        if ( obj != null )
        {
            String strTemp = obj.toString();
            if ( strTemp.length() > 0 )
            {
                return Integer.parseInt( strTemp );
            }
        }
        return 0;
    }

    /**
     * Description : Return a date object from a hashtable
     * 
     * @param htData
     *            Map
     * @param strFieldName
     *            String
     * @return Date
     */
    public static Date returnDate( Map htData , String strFieldName )
    {
        try
        {
            //Object objDate = DataUtil.convertValue(java.sql.Date.class,
            // null);
            return (java.sql.Date) DataUtil.convertValue( java.sql.Date.class , htData.get( strFieldName ) );
        }
        catch( Exception e )
        {
            logger.error( "SQLUtil (returnDate:Exception) - " + e.getMessage() );
            return null;
        }
    }

    /**
     * Description : Return year of date object from a hashtable
     * 
     * @param htData
     *            Map
     * @param strFieldName
     *            String
     * @return String
     */
    public static String returnYear( Map htData , String strFieldName )
    {
        try
        {
            Object obj = htData.get( strFieldName );
            if ( obj != null )
            {
                Date dDate = (java.sql.Date) DataUtil.convertValue( java.sql.Date.class , obj );
                return SyncSimpleDateFormat.format( GlobalConst.FORMAT_YEAR , dDate );
                //GlobalConst.DATEFORMAT_YEAR.format(dDate);
            }
            else
                return strc1900;
        }
        catch( Exception e )
        {
            logger.error( "SQLUtil (returnYear:Exception) - " + e.getMessage() );
            return null;
        }
    }
}
