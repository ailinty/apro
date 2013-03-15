/**
 * <copyright>
 * </copyright>
 *
 * $Id: GlobalFactoryImpl.java,v 1.1 2012/08/21 07:17:45 joey Exp $
 */
package com.joeysoft.kc868.xsd.global.impl;

import com.joeysoft.kc868.xsd.global.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class GlobalFactoryImpl extends EFactoryImpl implements GlobalFactory {
	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GlobalFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case GlobalPackage.GLOBAL_SETTING: return createGlobalSetting();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case GlobalPackage.DB_VERSION: {
				String result = initialValue;
				if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
				return result;
			}
			case GlobalPackage.DB_VERSION_OBJECT:
				return createLanguageTypeObjectFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case GlobalPackage.DB_VERSION:
				return instanceValue == null ? null : instanceValue.toString();
			case GlobalPackage.DB_VERSION_OBJECT:
				return convertLanguageTypeObjectToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GlobalSetting createGlobalSetting() {
		GlobalSettingImpl globalSetting = new GlobalSettingImpl();
		return globalSetting;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String createLanguageTypeObjectFromString(EDataType eDataType, String initialValue) {
		return (String)GlobalFactory.eINSTANCE.createFromString(GlobalPackage.eINSTANCE.getDBVersion(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertLanguageTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return GlobalFactory.eINSTANCE.convertToString(GlobalPackage.eINSTANCE.getDBVersion(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GlobalPackage getGlobalPackage() {
		return (GlobalPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	public static GlobalPackage getPackage() {
		return GlobalPackage.eINSTANCE;
	}

} //GlobalFactoryImpl
