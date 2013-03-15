package com.joeysoft.kc868.xsd.login.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.EFactoryImpl;

import com.joeysoft.kc868.xsd.login.Login;
import com.joeysoft.kc868.xsd.login.LoginFactory;
import com.joeysoft.kc868.xsd.login.LoginPackage;
import com.joeysoft.kc868.xsd.login.Logins;

public class LoginFactoryImpl extends EFactoryImpl implements LoginFactory {
	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LoginFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case LoginPackage.LOGIN: return createLogin();
			case LoginPackage.LOGINS: return createLogins();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Login createLogin() {
		LoginImpl login = new LoginImpl();
		return login;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Logins createLogins() {
		LoginsImpl logins = new LoginsImpl();
		return logins;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LoginPackage getLoginPackage() {
		return (LoginPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	public static LoginPackage getPackage() {
		return LoginPackage.eINSTANCE;
	}

}
