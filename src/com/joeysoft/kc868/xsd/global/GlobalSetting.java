/**
 * <copyright>
 * </copyright>
 *
 * $Id: GlobalSetting.java,v 1.1 2012/08/21 07:17:45 joey Exp $
 */
package com.joeysoft.kc868.xsd.global;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Setting</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.ycmsoft.qqserver.xsd.global.GlobalSetting#getDBVersion <em>DBVersion</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.ycmsoft.qqserver.xsd.global.GlobalPackage#getGlobalSetting()
 * @model extendedMetaData="name='GlobalSetting' kind='elementOnly'"
 * @generated
 */
public interface GlobalSetting extends EObject {
	/**
	 * Returns the value of the '<em><b>DBVersion</b></em>' attribute.
	 * The default value is <code>"zh"</code>.
	 * The literals are from the enumeration {@link com.DBVersion.qqserver.xsd.global.DBVersionType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>DBVersion</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>DBVersion</em>' attribute.
	 * @see com.DBVersion.qqserver.xsd.global.DBVersionType
	 * @see #isSetDBVersion()
	 * @see #unsetDBVersion()
	 * @see #setDBVersion(DBVersion)
	 * @see com.ycmsoft.qqserver.xsd.global.GlobalPackage#getGlobalSetting_DBVersion()
	 * @model default="zh" unique="false" unsettable="true" required="true"
	 *        extendedMetaData="kind='element' name='language'"
	 * @generated
	 */
	String getDBVersion();

	/**
	 * Sets the value of the '{@link com.ycmsoft.qqserver.xsd.global.GlobalSetting#getDBVersion <em>DBVersion</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>DBVersion</em>' attribute.
	 * @see com.DBVersion.qqserver.xsd.global.DBVersionType
	 * @see #isSetDBVersion()
	 * @see #unsetDBVersion()
	 * @see #getDBVersion()
	 * @generated
	 */
	void setDBVersion(String value);

	/**
	 * Unsets the value of the '{@link com.ycmsoft.qqserver.xsd.global.GlobalSetting#getDBVersion <em>DBVersion</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetDBVersion()
	 * @see #getDBVersion()
	 * @see #setDBVersion(DBVersion)
	 * @generated
	 */
	void unsetDBVersion();

	/**
	 * Returns whether the value of the '{@link com.ycmsoft.qqserver.xsd.global.GlobalSetting#getDBVersion <em>DBVersion</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>DBVersion</em>' attribute is set.
	 * @see #unsetDBVersion()
	 * @see #getDBVersion()
	 * @see #setDBVersion(DBVersion)
	 * @generated
	 */
	boolean isSetDBVersion();

} // GlobalSetting
