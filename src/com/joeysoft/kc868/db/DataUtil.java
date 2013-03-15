/**
 * Package	: infrastructure.datautility
 * File	: DataUtil.java
 *
 * Company 	: Excel Technology International (Hong Kong) Limited
 * Team    	: UTS
 * Description 	: Utility class for data type conversion
 *
 * The contents of this file are confidential and proprietary to Excel.
 * Copying is explicitly prohibited without the express permission of Excel.
 *
 * Create Date	:
 * Create By	:
 *
 * $Revision: 1.1 $
 * $History: DataUtil.java $
 * 
 * *****************  Version 1  *****************
 * User: Aleung       Date: 11/04/06   Time: 14:48
 * Created in $/UTS3.5/CORE/Source/src/infrastructure/datautility
 * 
 * *****************  Version 9  *****************
 * User: Brianmy      Date: 11/15/05   Time: 6:20p
 * Updated in $/COREMERGE_DEV/Source/src/infrastructure/datautility
 * 
 * *****************  Version 8  *****************
 * User: Annamy       Date: 28/08/05   Time: 14:34
 * Updated in $/COREMERGE_DEV/Source/src/infrastructure/datautility
 * 
 * *****************  Version 7  *****************
 * User: Terence      Date: 3/05/05    Time: 16:24
 * Updated in $/COREMERGE_DEV/Source/src/infrastructure/datautility
 * 
 * *****************  Version 6  *****************
 * User: Thomasli     Date: 05/04/27   Time: 7:13p
 * Updated in $/COREMERGE_DEV/Source/src/infrastructure/datautility
 * 
 * *****************  Version 5  *****************
 * User: Terence      Date: 4/01/05    Time: 12:26
 * Updated in $/COREMERGE_DEV/Source/src/infrastructure/datautility
 * 
 * *****************  Version 4  *****************
 * User: Terence      Date: 23/12/04   Time: 12:08
 * Updated in $/COREMERGE_DEV/Source/src/infrastructure/datautility
 * 
 * *****************  Version 3  *****************
 * User: Terence      Date: 23/12/04   Time: 9:06
 * Updated in $/COREMERGE_DEV/Source/src/infrastructure/datautility
 * 
 * *****************  Version 2  *****************
 * User: Terence      Date: 29/11/04   Time: 19:17
 * Updated in $/COREMERGE_DEV/Source/src/infrastructure/datautility
 * Project : Core
 * CR / SIR No : NIL
 * Desc : Uppercase, Constant String, Using Map/List for common method
 * parameters, etc.
 *
 * *****************  Version 1  *****************
 * User: Bwu          Date: 11/04/04   Time: 4:57p
 * Created in $/COREMERGE_DEV/Source/SRC/infrastructure/datautility
 *
 * *****************  Version 12  *****************
 * User: Bwu          Date: 5/28/04    Time: 10:33a
 * Updated in $/OCBC/Source/src/infrastructure/datautility
 * Project : Core
 * CR / SIR No : N/A
 * Desc : Remove unused code
 *
 * *****************  Version 11  *****************
 * User: Bwu          Date: 5/28/04    Time: 10:28a
 * Updated in $/OCBC/Source/src/infrastructure/datautility
 * Project : Core
 * CR / SIR No : N/A
 * Desc : Throw exception for datatime ParseException
 *
 * *****************  Version 10  *****************
 * User: Bwu          Date: 3/18/04    Time: 1:48p
 * Updated in $/OCBC/Source/src/infrastructure/datautility
 * Project : Core
 * CR / SIR No : N/A
 * Desc : Add description for previous version
 *
 * *****************  Version 9  *****************
 * User: Bwu          Date: 3/18/04    Time: 12:02p
 * Updated in $/OCBC/Source/src/infrastructure/datautility
 * Project : Core
 * CR / SIR No : N/A
 * Desc : Base on the Code Review:
 * 1. Minimize the concate string
 * 2. Remove unused code
 * 3. Add method descirption and comment
 * 4. Revisit the coding is efficient or not and change if needed
 * 5. Reduce unnecessary type convertion
 * 6. Assign the .Size() into integer for looping purpose
**/
package com.joeysoft.kc868.db;

import java.text.ParseException;

import com.joeysoft.kc868.common.GlobalConst;
import com.joeysoft.kc868.common.SyncDecimalFormat;
import com.joeysoft.kc868.common.SyncSimpleDateFormat;

public class DataUtil {

	/**
	* Description : Convert the value object type into the corresponding object type
	* @param	      aClass Class
	* @param	      aValue Object
	* @return	      Object
	*/
	public static Object convertValue(Class aClass, Object aValue) throws Exception {
		if (aValue == null || aClass == null) {
			return null;
		}
		if (aValue.getClass().equals(aClass)
			&& aClass != java.sql.Timestamp.class
			&& aClass != java.sql.Date.class
			&& aClass != java.util.Date.class) {
			return aValue;
		}

		String strValue = aValue.toString();
		int iValueLen = strValue.length();
		if (iValueLen == 0) {
			return null;
		}
		
		try {
			
			// Convert To String
			if (aClass == java.lang.String.class) {
				if (aValue instanceof byte[]) {
					return new String((byte[]) aValue);
				} else if (aValue instanceof char[]) {
					return new String((char[]) aValue);
				} else {
					return formatToString(aValue);
				}
			}
			// Convert To Boolean
			else if (aClass == java.lang.Boolean.class) {
				strValue = removeNumStrComma(strValue);
				if (strValue.lastIndexOf(GlobalConst.CONST_STRING_DOT) != -1){
					strValue = strValue.substring(0, strValue.lastIndexOf(GlobalConst.CONST_STRING_DOT));
				}
				if ((strValue.equals(GlobalConst.CONST_STRING_true)) || (strValue.equals(GlobalConst.CONST_STRING_false))) {
					return Boolean.valueOf(strValue);
				} else if (Long.valueOf(strValue).longValue() == 0) {
					return Boolean.valueOf(GlobalConst.CONST_STRING_false);
				}
				return Boolean.valueOf(GlobalConst.CONST_STRING_true);
			}
			// Convert To Double
			else if (aClass == java.lang.Double.class) {
				strValue = removeNumStrComma(strValue);
				return Double.valueOf(SyncDecimalFormat.format("#.########", Double.valueOf(strValue)));
			}
			// Convert To Float
			else if (aClass == java.lang.Float.class) {
				strValue = removeNumStrComma(strValue);
				return Float.valueOf(strValue);
			}
			// Convert To Byte
			else if (aClass == java.lang.Byte.class) {
				strValue = removeNumStrComma(strValue);
				int iValueLastIndex = strValue.lastIndexOf(GlobalConst.CONST_STRING_DOT);
				if (iValueLastIndex != -1) {
					return Byte.valueOf(strValue.substring(0, iValueLastIndex));
				} else {
					return Byte.valueOf(strValue);
				}
			}
			// Convert To Short
			else if (aClass == java.lang.Short.class) {
				strValue = removeNumStrComma(strValue);
				if (strValue.lastIndexOf(GlobalConst.CONST_STRING_DOT) != -1) {
					return Short.valueOf(strValue.substring(0, strValue.lastIndexOf(GlobalConst.CONST_STRING_DOT)));
				} else {
					return Short.valueOf(strValue);
				}
			}
			// Convert To Integer
			else if (aClass == java.lang.Integer.class) {
				strValue = removeNumStrComma(strValue);
				if (strValue.lastIndexOf(GlobalConst.CONST_STRING_DOT) != -1) {
					return Integer.valueOf(strValue.substring(0, strValue.lastIndexOf(GlobalConst.CONST_STRING_DOT)));
				} else {
					return Integer.valueOf(strValue);
				}
			}
			// Convert To Long
			else if (aClass == java.lang.Long.class) {
				strValue = removeNumStrComma(strValue);
				if (strValue.lastIndexOf(GlobalConst.CONST_STRING_DOT) != -1) {
					return Long.valueOf(
						strValue.substring(0, strValue.lastIndexOf(GlobalConst.CONST_STRING_DOT)));
				} else {
					return Long.valueOf(strValue);
				}
			}
			// Convert To java.math.BigDecimal
			else if (aClass == java.math.BigDecimal.class) {
				strValue = removeNumStrComma(strValue);
				return (new java.math.BigDecimal(strValue));
			}
			// Convert To java.math.BigInteger
			else if (aClass == java.math.BigInteger.class) {
				strValue = removeNumStrComma(strValue);
				if (strValue.lastIndexOf(GlobalConst.CONST_STRING_DOT) != -1)
					return (new java.math.BigInteger(strValue.substring(0, strValue.lastIndexOf(GlobalConst.CONST_STRING_DOT))));
				else
					return (new java.math.BigInteger(strValue));
			}
			// Convert To java.util.Date
			else if (aClass == java.util.Date.class) {
				if (aValue instanceof java.util.Date) {
					return new java.sql.Date(((java.util.Date) aValue).getTime());
				}
				if (aValue.getClass() == java.lang.String.class) {
					return new java.sql.Date(SyncSimpleDateFormat.parse(GlobalConst.FORMAT_DATE, strValue).getTime());
				}
			}
			// Convert To java.sql.Date
			else if (aClass == java.sql.Date.class) {
				if (aValue instanceof java.util.Date) {
					return new java.sql.Date(((java.util.Date) aValue).getTime());
				}
				if (aValue.getClass() == java.lang.String.class) {
					return new java.sql.Date(SyncSimpleDateFormat.parse(GlobalConst.FORMAT_DATE, strValue).getTime());
				}
			}
			// Convert To java.sql.Timestamp
			else if (aClass == java.sql.Timestamp.class) {
				if (aValue instanceof java.util.Date) {
					return new java.sql.Timestamp(((java.util.Date) aValue).getTime());
				} else if (aValue.getClass() == java.lang.String.class) {
					//DateFormat objDateFormat = null;
					if (aValue.toString().length() >= GlobalConst.FORMAT_DATETIME.length()) {
						return new java.sql.Timestamp(SyncSimpleDateFormat.parse(GlobalConst.FORMAT_DATETIME, strValue).getTime());
					} else {
						//return new java.sql.Timestamp(SyncSimpleDateFormat.parse(GlobalConst.FORMAT_DATE, strValue).getTime());
						if (aValue.toString().length() >= GlobalConst.FORMAT_DATETIME.length()) {
							
							return new java.sql.Timestamp(SyncSimpleDateFormat.parse(GlobalConst.FORMAT_DATETIME, strValue).getTime());
						} else {
							
							try{
							return new java.sql.Timestamp(SyncSimpleDateFormat.parse(GlobalConst.FORMAT_DATE, strValue).getTime());
							}catch (Exception e){   
							}
						}
					}
				}
			}
			// Convert To Others
			else {
			}
		} catch (ParseException e) {
			String strMsg = GlobalConst.CONST_STRING_EMPTY;
			if (aClass == java.util.Date.class || aClass == java.sql.Date.class){
			  strMsg = "Incorrect data format. Date value required.";
			}
			else if (aClass == java.sql.Timestamp.class){
			  strMsg = "Incorrect data format. Datetime value required.";
			}
		} catch (NumberFormatException e) {
			String strMsg = GlobalConst.CONST_STRING_EMPTY;
			if (aClass == java.lang.Boolean.class) {
				strMsg = "Incorrect data format. 0 or 1 required.";
			} else {
				strMsg = "Incorrect data format. Numeric value required.";
			}
		}
		return aValue;
	}

	/**
	* Description : Change the value input into a String
	* @param	      Value Object
	* @return	      Object
	*/
	public static String formatToString(Object Value) {
		if (Value == null) {
			return GlobalConst.CONST_STRING_EMPTY;
		}
		//if (Value instanceof java.lang.String) {
		/*if (Value.getClass() == java.lang.String.class) {
			return ((String) Value).replace('\0', ' ').trim();
		}*/
		if (Value.getClass() == java.lang.String.class) {
			return ((String) Value);
		}
		if (Value instanceof java.lang.Number) {
			return Value.toString();
		}
		if ((Value.getClass() == java.util.Date.class) || (Value.getClass() == java.sql.Date.class)) {
			return SyncSimpleDateFormat.format(GlobalConst.FORMAT_DATE, (java.util.Date) Value);
		}
		if (Value.getClass() == java.sql.Timestamp.class) {
			return SyncSimpleDateFormat.format(GlobalConst.FORMAT_DATETIME, (java.util.Date) Value);
		}
		return Value.toString().trim();
	}

	/**
	* Description : Change the value input into a String
	* @param	      Value Object
	* @return	      Object
	*/
	public static String formatToNullOrString(Object Value) {
		if (Value == null) {
			return null;
		}
		//if (Value instanceof java.lang.String) {
		if (Value.getClass() == java.lang.String.class) {
			//return ((String) Value).replace('\0', ' ').trim();
		    if(((String) Value).length() == 0 ){
		        return null;
		    }
		    else{
		        return ((String) Value).replace('\0', ' ').trim();
		    }
		}
		if (Value instanceof java.lang.Number) {
			return Value.toString();
		}
		if ((Value.getClass() == java.util.Date.class) || (Value.getClass() == java.sql.Date.class)) {
			return SyncSimpleDateFormat.format(GlobalConst.FORMAT_DATE, (java.util.Date) Value);
		}
		if (Value.getClass() == java.sql.Timestamp.class) {
			return SyncSimpleDateFormat.format(GlobalConst.FORMAT_DATETIME, (java.util.Date) Value);
		}
		return Value.toString().trim();
	}

	/**
	* Description : Remove comma in number string b4 convert to number 
	* @param	      straValue String
	* @return	      String
	*/
	public static String removeNumStrComma(String straValue) {
		return straValue.replaceAll(",", "");
	}
	
//	public static void main(String[] argv) {
//		try {
//			java.util.HashMap hmtest = new java.util.HashMap();
//			hmtest.put("A", "1");
//			hmtest.put("B", "2");
//			System.out.println(hmtest.entrySet());
//			java.util.Set stest = hmtest.keySet();
//			System.out.println("stest="+stest);
//			Object[] objtest = hmtest.keySet().toArray();
//			int intlen = objtest.length;
//			for (int i=0; i< intlen; i++){
//				System.out.println(i + "=" + objtest[i]);
//				System.out.println(objtest[i] + "=" + hmtest.get(objtest[i]));
//			}
//		} catch (Exception e) {
//
//			System.out.println(e.toString());
//		}
//	}
}
