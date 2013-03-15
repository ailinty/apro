package com.joeysoft.kc868.xsd.login;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import com.joeysoft.kc868.xsd.login.impl.LoginPackageImpl;

public interface LoginPackage extends EPackage{
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "login";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://kc868.joeysoft.com/xsd/login.xsd";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "login";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	LoginPackage eINSTANCE = LoginPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.joeysoft.kc868.xsd.login.impl.LoginImpl <em>Login</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.joeysoft.kc868.xsd.login.impl.LoginImpl
	 * @see com.joeysoft.kc868.xsd.login.impl.LoginPackageImpl#getLogin()
	 * @generated
	 */
	int LOGIN = 0;

	/**
	 * The feature id for the '<em><b>Auto Login</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGIN__AUTO_LOGIN = 0;

	/**
	 * The feature id for the '<em><b>Login Sync</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGIN__LOGIN_SYNC = 1;

	/**
	 * The feature id for the '<em><b>Password</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGIN__PASSWORD = 2;

	/**
	 * The feature id for the '<em><b>IP</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGIN__IP = 3;

	/**
	 * The feature id for the '<em><b>Remember Password</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGIN__REMEMBER_PASSWORD = 4;

	/**
	 * The feature id for the '<em><b>USER</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGIN__USER = 5;
	
	/**
	 * The feature id for the '<em><b>PORT</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGIN__PORT = 6;
	
	/**
	 * The number of structural features of the the '<em>Login</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGIN_FEATURE_COUNT = 7;

	/**
	 * The meta object id for the '{@link com.joeysoft.kc868.xsd.login.impl.LoginsImpl <em>Logins</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.joeysoft.kc868.xsd.login.impl.LoginsImpl
	 * @see com.joeysoft.kc868.xsd.login.impl.LoginPackageImpl#getLogins()
	 * @generated
	 */
	int LOGINS = 1;

	/**
	 * The feature id for the '<em><b>Login</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGINS__LOGIN = 0;

	/**
	 * The feature id for the '<em><b>Last Login</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGINS__LAST_LOGIN = 1;

	/**
	 * The number of structural features of the the '<em>Logins</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGINS_FEATURE_COUNT = 2;


	/**
	 * Returns the meta object for class '{@link com.joeysoft.kc868.xsd.login.Login <em>Login</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Login</em>'.
	 * @see com.joeysoft.kc868.xsd.login.Login
	 * @generated
	 */
	EClass getLogin();

	/**
	 * Returns the meta object for the attribute '{@link com.joeysoft.kc868.xsd.login.Login#isAutoLogin <em>Auto Login</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Auto Login</em>'.
	 * @see com.joeysoft.kc868.xsd.login.Login#isAutoLogin()
	 * @see #getLogin()
	 * @generated
	 */
	EAttribute getLogin_AutoLogin();

	/**
	 * Returns the meta object for the attribute '{@link com.joeysoft.kc868.xsd.login.Login#isLoginSync <em>Login Sync</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Login Sync</em>'.
	 * @see com.joeysoft.kc868.xsd.login.Login#isLoginSync()
	 * @see #getLogin()
	 * @generated
	 */
	EAttribute getLogin_LoginSync();

	/**
	 * Returns the meta object for the attribute '{@link com.joeysoft.kc868.xsd.login.Login#getPassword <em>Password</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Password</em>'.
	 * @see com.joeysoft.kc868.xsd.login.Login#getPassword()
	 * @see #getLogin()
	 * @generated
	 */
	EAttribute getLogin_Password();

	/**
	 * Returns the meta object for the attribute '{@link com.joeysoft.kc868.xsd.login.Login#getIP <em>IP</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>IP</em>'.
	 * @see com.joeysoft.kc868.xsd.login.Login#getIP()
	 * @see #getLogin()
	 * @generated
	 */
	EAttribute getLogin_IP();
	
	/**
	 * Returns the meta object for the attribute '{@link com.joeysoft.kc868.xsd.login.Login#getUser <em>User</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>User</em>'.
	 * @see com.joeysoft.kc868.xsd.login.Login#getUser()
	 * @see #getLogin()
	 * @generated
	 */
	EAttribute getLogin_User();
	
	/**
	 * Returns the meta object for the attribute '{@link com.joeysoft.kc868.xsd.login.Login#getPort <em>Port</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Port</em>'.
	 * @see com.joeysoft.kc868.xsd.login.Login#getPort()
	 * @see #getLogin()
	 * @generated
	 */
	EAttribute getLogin_Port();

	/**
	 * Returns the meta object for the attribute '{@link com.joeysoft.kc868.xsd.login.Login#isRememberPassword <em>Remember Password</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Remember Password</em>'.
	 * @see com.joeysoft.kc868.xsd.login.Login#isRememberPassword()
	 * @see #getLogin()
	 * @generated
	 */
	EAttribute getLogin_RememberPassword();

	/**
	 * Returns the meta object for class '{@link com.joeysoft.kc868.xsd.login.Logins <em>Logins</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Logins</em>'.
	 * @see com.joeysoft.kc868.xsd.login.Logins
	 * @generated
	 */
	EClass getLogins();

	/**
	 * Returns the meta object for the containment reference list '{@link com.joeysoft.kc868.xsd.login.Logins#getLogin <em>Login</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Login</em>'.
	 * @see com.joeysoft.kc868.xsd.login.Logins#getLogin()
	 * @see #getLogins()
	 * @generated
	 */
	EReference getLogins_Login();

	/**
	 * Returns the meta object for the attribute '{@link com.joeysoft.kc868.xsd.login.Logins#getLastLogin <em>Last Login</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Last Login</em>'.
	 * @see com.joeysoft.kc868.xsd.login.Logins#getLastLogin()
	 * @see #getLogins()
	 * @generated
	 */
	EAttribute getLogins_LastLogin();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	LoginFactory getLoginFactory();
}
