package com.joeysoft.kc868.xsd.login.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.joeysoft.kc868.xsd.login.Login;
import com.joeysoft.kc868.xsd.login.LoginPackage;

public class LoginImpl extends EObjectImpl implements Login {
	/**
	 * The default value of the '{@link #isAutoLogin() <em>Auto Login</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAutoLogin()
	 * @generated
	 * @ordered
	 */
	protected static final boolean AUTO_LOGIN_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isAutoLogin() <em>Auto Login</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAutoLogin()
	 * @generated
	 * @ordered
	 */
	protected boolean autoLogin = AUTO_LOGIN_EDEFAULT;

	/**
	 * This is true if the Auto Login attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean autoLoginESet = false;

	/**
	 * The default value of the '{@link #isLoginSync() <em>Login Sync</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isLoginSync()
	 * @generated
	 * @ordered
	 */
	protected static final boolean LOGIN_SYNC_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isLoginSync() <em>Login Sync</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isLoginSync()
	 * @generated
	 * @ordered
	 */
	protected boolean loginSync = LOGIN_SYNC_EDEFAULT;

	/**
	 * This is true if the Login Sync attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean loginSyncESet = false;

	/**
	 * The default value of the '{@link #getPassword() <em>Password</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPassword()
	 * @generated
	 * @ordered
	 */
	protected static final String PASSWORD_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPassword() <em>Password</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPassword()
	 * @generated
	 * @ordered
	 */
	protected String password = PASSWORD_EDEFAULT;

	/**
	 * The default value of the '{@link #getIP() <em>IP</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIP()
	 * @generated
	 * @ordered
	 */
	protected static final String IP_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getIP() <em>IP</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIP()
	 * @generated
	 * @ordered
	 */
	protected String IP = IP_EDEFAULT;
	
	/**
	 * The default value of the '{@link #getUser() <em>USER</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUser()
	 * @generated
	 * @ordered
	 */
	protected static final String USER_EDEFAULT = null;
	
	/**
	 * The cached value of the '{@link #getUser() <em>User</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUser()
	 * @generated
	 * @ordered
	 */
	protected String user = USER_EDEFAULT;
	
	/**
	 * The default value of the '{@link #getPort() <em>Port</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPort()
	 * @generated
	 * @ordered
	 */
	protected static final String PORT_EDEFAULT = null;
	
	/**
	 * The cached value of the '{@link #getPort() <em>IP</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPort()
	 * @generated
	 * @ordered
	 */
	protected String port = PORT_EDEFAULT;

	/**
	 * The default value of the '{@link #isRememberPassword() <em>Remember Password</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isRememberPassword()
	 * @generated
	 * @ordered
	 */
	protected static final boolean REMEMBER_PASSWORD_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isRememberPassword() <em>Remember Password</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isRememberPassword()
	 * @generated
	 * @ordered
	 */
	protected boolean rememberPassword = REMEMBER_PASSWORD_EDEFAULT;

	/**
	 * This is true if the Remember Password attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean rememberPasswordESet = false;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LoginImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return LoginPackage.eINSTANCE.getLogin();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAutoLogin() {
		return autoLogin;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAutoLogin(boolean newAutoLogin) {
		boolean oldAutoLogin = autoLogin;
		autoLogin = newAutoLogin;
		boolean oldAutoLoginESet = autoLoginESet;
		autoLoginESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LoginPackage.LOGIN__AUTO_LOGIN, oldAutoLogin, autoLogin, !oldAutoLoginESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetAutoLogin() {
		boolean oldAutoLogin = autoLogin;
		boolean oldAutoLoginESet = autoLoginESet;
		autoLogin = AUTO_LOGIN_EDEFAULT;
		autoLoginESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, LoginPackage.LOGIN__AUTO_LOGIN, oldAutoLogin, AUTO_LOGIN_EDEFAULT, oldAutoLoginESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetAutoLogin() {
		return autoLoginESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isLoginSync() {
		return loginSync;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLoginSync(boolean newLoginSync) {
		boolean oldLoginSync = loginSync;
		loginSync = newLoginSync;
		boolean oldLoginSyncESet = loginSyncESet;
		loginSyncESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LoginPackage.LOGIN__LOGIN_SYNC, oldLoginSync, loginSync, !oldLoginSyncESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetLoginSync() {
		boolean oldLoginSync = loginSync;
		boolean oldLoginSyncESet = loginSyncESet;
		loginSync = LOGIN_SYNC_EDEFAULT;
		loginSyncESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, LoginPackage.LOGIN__LOGIN_SYNC, oldLoginSync, LOGIN_SYNC_EDEFAULT, oldLoginSyncESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetLoginSync() {
		return loginSyncESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPassword(String newPassword) {
		String oldPassword = password;
		password = newPassword;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LoginPackage.LOGIN__PASSWORD, oldPassword, password));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getIP() {
		return IP;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIP(String newIP) {
		String oldIP = IP;
		IP = newIP;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LoginPackage.LOGIN__IP, oldIP, IP));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getUser() {
		return user;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUser(String newUser) {
		String oldUser = user;
		user = newUser;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LoginPackage.LOGIN__USER, oldUser, user));
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPort() {
		return port;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPort(String newPort) {
		String oldPort = port;
		port = newPort;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LoginPackage.LOGIN__PORT, oldPort, port));
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isRememberPassword() {
		return rememberPassword;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRememberPassword(boolean newRememberPassword) {
		boolean oldRememberPassword = rememberPassword;
		rememberPassword = newRememberPassword;
		boolean oldRememberPasswordESet = rememberPasswordESet;
		rememberPasswordESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LoginPackage.LOGIN__REMEMBER_PASSWORD, oldRememberPassword, rememberPassword, !oldRememberPasswordESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetRememberPassword() {
		boolean oldRememberPassword = rememberPassword;
		boolean oldRememberPasswordESet = rememberPasswordESet;
		rememberPassword = REMEMBER_PASSWORD_EDEFAULT;
		rememberPasswordESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, LoginPackage.LOGIN__REMEMBER_PASSWORD, oldRememberPassword, REMEMBER_PASSWORD_EDEFAULT, oldRememberPasswordESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetRememberPassword() {
		return rememberPasswordESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(EStructuralFeature eFeature, boolean resolve) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case LoginPackage.LOGIN__AUTO_LOGIN:
				return isAutoLogin() ? Boolean.TRUE : Boolean.FALSE;
			case LoginPackage.LOGIN__LOGIN_SYNC:
				return isLoginSync() ? Boolean.TRUE : Boolean.FALSE;
			case LoginPackage.LOGIN__PASSWORD:
				return getPassword();
			case LoginPackage.LOGIN__IP:
				return getIP();
			case LoginPackage.LOGIN__USER:
				return getUser();
			case LoginPackage.LOGIN__PORT:
				return getPort();
			case LoginPackage.LOGIN__REMEMBER_PASSWORD:
				return isRememberPassword() ? Boolean.TRUE : Boolean.FALSE;
		}
		return eDynamicGet(eFeature, resolve);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eSet(EStructuralFeature eFeature, Object newValue) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case LoginPackage.LOGIN__AUTO_LOGIN:
				setAutoLogin(((Boolean)newValue).booleanValue());
				return;
			case LoginPackage.LOGIN__LOGIN_SYNC:
				setLoginSync(((Boolean)newValue).booleanValue());
				return;
			case LoginPackage.LOGIN__PASSWORD:
				setPassword((String)newValue);
				return;
			case LoginPackage.LOGIN__IP:
				setIP((String)newValue);
				return;
			case LoginPackage.LOGIN__USER:
				setUser((String)newValue);
				return;
			case LoginPackage.LOGIN__PORT:
				setPort((String)newValue);
				return;
			case LoginPackage.LOGIN__REMEMBER_PASSWORD:
				setRememberPassword(((Boolean)newValue).booleanValue());
				return;
		}
		eDynamicSet(eFeature, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eUnset(EStructuralFeature eFeature) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case LoginPackage.LOGIN__AUTO_LOGIN:
				unsetAutoLogin();
				return;
			case LoginPackage.LOGIN__LOGIN_SYNC:
				unsetLoginSync();
				return;
			case LoginPackage.LOGIN__PASSWORD:
				setPassword(PASSWORD_EDEFAULT);
				return;
			case LoginPackage.LOGIN__IP:
				setIP(IP_EDEFAULT);
				return;
			case LoginPackage.LOGIN__USER:
				setUser(USER_EDEFAULT);
				return;
			case LoginPackage.LOGIN__PORT:
				setPort(PORT_EDEFAULT);
				return;
			case LoginPackage.LOGIN__REMEMBER_PASSWORD:
				unsetRememberPassword();
				return;
		}
		eDynamicUnset(eFeature);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean eIsSet(EStructuralFeature eFeature) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case LoginPackage.LOGIN__AUTO_LOGIN:
				return isSetAutoLogin();
			case LoginPackage.LOGIN__LOGIN_SYNC:
				return isSetLoginSync();
			case LoginPackage.LOGIN__PASSWORD:
				return PASSWORD_EDEFAULT == null ? password != null : !PASSWORD_EDEFAULT.equals(password);
			case LoginPackage.LOGIN__IP:
				return IP_EDEFAULT == null ? IP != null : !IP_EDEFAULT.equals(IP);
			case LoginPackage.LOGIN__USER:
				return USER_EDEFAULT == null ? user != null : !USER_EDEFAULT.equals(user);
			case LoginPackage.LOGIN__PORT:
				return PORT_EDEFAULT == null ? port != null : !PORT_EDEFAULT.equals(port);
			case LoginPackage.LOGIN__REMEMBER_PASSWORD:
				return isSetRememberPassword();
		}
		return eDynamicIsSet(eFeature);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (autoLogin: ");
		if (autoLoginESet) result.append(autoLogin); else result.append("<unset>");
		result.append(", loginSync: ");
		if (loginSyncESet) result.append(loginSync); else result.append("<unset>");
		result.append(", password: ");
		result.append(password);
		result.append(", USER: ");
		result.append(user);
		result.append(", IP: ");
		result.append(IP);
		result.append(", PORT: ");
		result.append(port);
		result.append(", rememberPassword: ");
		if (rememberPasswordESet) result.append(rememberPassword); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

}