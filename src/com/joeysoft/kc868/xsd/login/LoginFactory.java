package com.joeysoft.kc868.xsd.login;

import org.eclipse.emf.ecore.EFactory;

import com.joeysoft.kc868.xsd.login.impl.LoginFactoryImpl;

public interface LoginFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	LoginFactory eINSTANCE = new LoginFactoryImpl();

	/**
	 * Returns a new object of class '<em>Login</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Login</em>'.
	 * @generated
	 */
	Login createLogin();

	/**
	 * Returns a new object of class '<em>Logins</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Logins</em>'.
	 * @generated
	 */
	Logins createLogins();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	LoginPackage getLoginPackage();

}
