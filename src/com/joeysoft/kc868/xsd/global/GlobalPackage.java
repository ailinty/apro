/**
 * <copyright>
 * </copyright>
 *
 * $Id: GlobalPackage.java,v 1.1 2012/08/21 07:17:45 joey Exp $
 */
package com.joeysoft.kc868.xsd.global;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;

import com.joeysoft.kc868.xsd.global.impl.GlobalPackageImpl;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see com.joeysoft.kc868.xsd.global.GlobalFactory
 * @model kind="package"
 * @generated
 */
public interface GlobalPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "global";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://kc868.joeysoft.com/xsd/global.xsd";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "global";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	GlobalPackage eINSTANCE = GlobalPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.joeysoft.kc868.xsd.global.impl.GlobalSettingImpl <em>Setting</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.joeysoft.kc868.xsd.global.impl.GlobalSettingImpl
	 * @see com.joeysoft.kc868.xsd.global.impl.GlobalPackageImpl#getGlobalSetting()
	 * @generated
	 */
	int GLOBAL_SETTING = 0;

	/**
	 * The feature id for the '<em><b>Language</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_SETTING__DB_VERSION = 0;

	/**
	 * The number of structural features of the the '<em>Setting</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_SETTING_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.joeysoft.kc868.xsd.global.DBVersion <em>Language Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.joeysoft.kc868.xsd.global.DBVersion
	 * @see com.joeysoft.kc868.xsd.global.impl.GlobalPackageImpl#getLanguageType()
	 * @generated
	 */
	int DB_VERSION = 1;

	/**
	 * The meta object id for the '<em>Language Type Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.joeysoft.kc868.xsd.global.DBVersion
	 * @see com.joeysoft.kc868.xsd.global.impl.GlobalPackageImpl#getLanguageTypeObject()
	 * @generated
	 */
	int DB_VERSION_OBJECT = 2;


	/**
	 * Returns the meta object for class '{@link com.joeysoft.kc868.xsd.global.GlobalSetting <em>Setting</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Setting</em>'.
	 * @see com.joeysoft.kc868.xsd.global.GlobalSetting
	 * @generated
	 */
	EClass getGlobalSetting();

	/**
	 * Returns the meta object for the attribute '{@link com.joeysoft.kc868.xsd.global.GlobalSetting#getLanguage <em>Language</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Language</em>'.
	 * @see com.joeysoft.kc868.xsd.global.GlobalSetting#getLanguage()
	 * @see #getGlobalSetting()
	 * @generated
	 */
	EAttribute getGlobalSetting_DBVersion();

	/**
	 * Returns the meta object for enum '{@link com.joeysoft.kc868.xsd.global.DBVersion <em>Language Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Language Type</em>'.
	 * @see com.joeysoft.kc868.xsd.global.DBVersion
	 * @generated
	 */
	EEnum getDBVersion();

	/**
	 * Returns the meta object for data type '{@link com.joeysoft.kc868.xsd.global.DBVersion <em>Language Type Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Language Type Object</em>'.
	 * @see com.joeysoft.kc868.xsd.global.DBVersion
	 * @model instanceClass="com.joeysoft.kc868.xsd.global.LanguageType"
	 *        extendedMetaData="name='LanguageType:Object' baseType='LanguageType'" 
	 * @generated
	 */
	EDataType getDBVersionObject();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	GlobalFactory getGlobalFactory();

} //GlobalPackage
