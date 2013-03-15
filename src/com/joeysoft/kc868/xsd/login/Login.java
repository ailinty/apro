package com.joeysoft.kc868.xsd.login;

import org.eclipse.emf.ecore.EObject;

public interface Login extends EObject{
	/**
	 * Returns the value of the '<em><b>Auto Login</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Auto Login</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Auto Login</em>' attribute.
	 * @see #isSetAutoLogin()
	 * @see #unsetAutoLogin()
	 * @see #setAutoLogin(boolean)
	 * @see com.joeysoft.kc868.xsd.login.LoginPackage#getLogin_AutoLogin()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
	 *        extendedMetaData="kind='attribute' name='autoLogin'"
	 * @generated
	 */
	boolean isAutoLogin();

	/**
	 * Sets the value of the '{@link com.joeysoft.kc868.xsd.login.Login#isAutoLogin <em>Auto Login</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Auto Login</em>' attribute.
	 * @see #isSetAutoLogin()
	 * @see #unsetAutoLogin()
	 * @see #isAutoLogin()
	 * @generated
	 */
	void setAutoLogin(boolean value);

	/**
	 * Unsets the value of the '{@link com.joeysoft.kc868.xsd.login.Login#isAutoLogin <em>Auto Login</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetAutoLogin()
	 * @see #isAutoLogin()
	 * @see #setAutoLogin(boolean)
	 * @generated
	 */
	void unsetAutoLogin();

	/**
	 * Returns whether the value of the '{@link com.joeysoft.kc868.xsd.login.Login#isAutoLogin <em>Auto Login</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Auto Login</em>' attribute is set.
	 * @see #unsetAutoLogin()
	 * @see #isAutoLogin()
	 * @see #setAutoLogin(boolean)
	 * @generated
	 */
	boolean isSetAutoLogin();

	/**
	 * Returns the value of the '<em><b>Login Sync</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Login Sync</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Login Sync</em>' attribute.
	 * @see #isSetLoginSync()
	 * @see #unsetLoginSync()
	 * @see #setLoginSync(boolean)
	 * @see com.joeysoft.kc868.xsd.login.LoginPackage#getLogin_LoginSync()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
	 *        extendedMetaData="kind='attribute' name='loginSync'"
	 * @generated
	 */
	boolean isLoginSync();

	/**
	 * Sets the value of the '{@link com.joeysoft.kc868.xsd.login.Login#isLoginSync <em>Login Sync</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Login Sync</em>' attribute.
	 * @see #isSetLoginSync()
	 * @see #unsetLoginSync()
	 * @see #isLoginSync()
	 * @generated
	 */
	void setLoginSync(boolean value);

	/**
	 * Unsets the value of the '{@link com.joeysoft.kc868.xsd.login.Login#isLoginSync <em>Login Sync</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetLoginSync()
	 * @see #isLoginSync()
	 * @see #setLoginSync(boolean)
	 * @generated
	 */
	void unsetLoginSync();

	/**
	 * Returns whether the value of the '{@link com.joeysoft.kc868.xsd.login.Login#isLoginSync <em>Login Sync</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Login Sync</em>' attribute is set.
	 * @see #unsetLoginSync()
	 * @see #isLoginSync()
	 * @see #setLoginSync(boolean)
	 * @generated
	 */
	boolean isSetLoginSync();

	/**
	 * Returns the value of the '<em><b>Password</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Password</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Password</em>' attribute.
	 * @see #setPassword(String)
	 * @see com.joeysoft.kc868.xsd.login.LoginPackage#getLogin_Password()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='password'"
	 * @generated
	 */
	String getPassword();

	/**
	 * Sets the value of the '{@link com.joeysoft.kc868.xsd.login.Login#getPassword <em>Password</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Password</em>' attribute.
	 * @see #getPassword()
	 * @generated
	 */
	void setPassword(String value);

	/**
	 * Returns the value of the '<em><b>User</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>User</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>User</em>' attribute.
	 * @see #setUser(String)
	 * @see com.joeysoft.kc868.xsd.login.LoginPackage#getLogin_User()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='qq'"
	 * @generated
	 */
	String getUser();

	/**
	 * Sets the value of the '{@link com.joeysoft.kc868.xsd.login.Login#getUser <em>User</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>User</em>' attribute.
	 * @see #getUser()
	 * @generated
	 */
	void setUser(String value);
	
	/**
	 * Returns the value of the '<em><b>IP</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>IP</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>IP</em>' attribute.
	 * @see #setIP(String)
	 * @see com.joeysoft.kc868.xsd.login.LoginPackage#getLogin_IP()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='qq'"
	 * @generated
	 */
	String getIP();

	/**
	 * Sets the value of the '{@link com.joeysoft.kc868.xsd.login.Login#getIP <em>IP</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>IP</em>' attribute.
	 * @see #getIP()
	 * @generated
	 */
	void setIP(String value);

	/**
	 * Returns the value of the '<em><b>Port</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Port</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Port</em>' attribute.
	 * @see #setPort(String)
	 * @see com.joeysoft.kc868.xsd.login.LoginPackage#getLogin_Port()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='qq'"
	 * @generated
	 */
	String getPort();

	/**
	 * Sets the value of the '{@link com.joeysoft.kc868.xsd.login.Login#getPort <em>Port</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Port</em>' attribute.
	 * @see #getPort()
	 * @generated
	 */
	void setPort(String value);
	
	/**
	 * Returns the value of the '<em><b>Remember Password</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Remember Password</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Remember Password</em>' attribute.
	 * @see #isSetRememberPassword()
	 * @see #unsetRememberPassword()
	 * @see #setRememberPassword(boolean)
	 * @see com.joeysoft.kc868.xsd.login.LoginPackage#getLogin_RememberPassword()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
	 *        extendedMetaData="kind='attribute' name='rememberPassword'"
	 * @generated
	 */
	boolean isRememberPassword();

	/**
	 * Sets the value of the '{@link com.joeysoft.kc868.xsd.login.Login#isRememberPassword <em>Remember Password</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Remember Password</em>' attribute.
	 * @see #isSetRememberPassword()
	 * @see #unsetRememberPassword()
	 * @see #isRememberPassword()
	 * @generated
	 */
	void setRememberPassword(boolean value);

	/**
	 * Unsets the value of the '{@link com.joeysoft.kc868.xsd.login.Login#isRememberPassword <em>Remember Password</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetRememberPassword()
	 * @see #isRememberPassword()
	 * @see #setRememberPassword(boolean)
	 * @generated
	 */
	void unsetRememberPassword();

	/**
	 * Returns whether the value of the '{@link com.joeysoft.kc868.xsd.login.Login#isRememberPassword <em>Remember Password</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Remember Password</em>' attribute is set.
	 * @see #unsetRememberPassword()
	 * @see #isRememberPassword()
	 * @see #setRememberPassword(boolean)
	 * @generated
	 */
	boolean isSetRememberPassword();

}
