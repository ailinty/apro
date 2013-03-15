package com.joeysoft.kc868.xsd.login.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.joeysoft.kc868.xsd.login.Login;
import com.joeysoft.kc868.xsd.login.LoginPackage;
import com.joeysoft.kc868.xsd.login.Logins;

public class LoginsImpl extends EObjectImpl implements Logins {
	/**
	 * The cached value of the '{@link #getLogin() <em>Login</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLogin()
	 * @generated
	 * @ordered
	 */
	protected EList login = null;

	/**
	 * The default value of the '{@link #getLastLogin() <em>Last Login</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLastLogin()
	 * @generated
	 * @ordered
	 */
	protected static final String LAST_LOGIN_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLastLogin() <em>Last Login</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLastLogin()
	 * @generated
	 * @ordered
	 */
	protected String lastLogin = LAST_LOGIN_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LoginsImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return LoginPackage.eINSTANCE.getLogins();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getLogin() {
		if (login == null) {
			login = new EObjectContainmentEList(Login.class, this, LoginPackage.LOGINS__LOGIN);
		}
		return login;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLastLogin() {
		return lastLogin;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLastLogin(String newLastLogin) {
		String oldLastLogin = lastLogin;
		lastLogin = newLastLogin;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LoginPackage.LOGINS__LAST_LOGIN, oldLastLogin, lastLogin));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case LoginPackage.LOGINS__LOGIN:
					return ((InternalEList)getLogin()).basicRemove(otherEnd, msgs);
				default:
					return eDynamicInverseRemove(otherEnd, featureID, baseClass, msgs);
			}
		}
		return eBasicSetContainer(null, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(EStructuralFeature eFeature, boolean resolve) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case LoginPackage.LOGINS__LOGIN:
				return getLogin();
			case LoginPackage.LOGINS__LAST_LOGIN:
				return getLastLogin();
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
			case LoginPackage.LOGINS__LOGIN:
				getLogin().clear();
				getLogin().addAll((Collection)newValue);
				return;
			case LoginPackage.LOGINS__LAST_LOGIN:
				setLastLogin((String)newValue);
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
			case LoginPackage.LOGINS__LOGIN:
				getLogin().clear();
				return;
			case LoginPackage.LOGINS__LAST_LOGIN:
				setLastLogin(LAST_LOGIN_EDEFAULT);
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
			case LoginPackage.LOGINS__LOGIN:
				return login != null && !login.isEmpty();
			case LoginPackage.LOGINS__LAST_LOGIN:
				return LAST_LOGIN_EDEFAULT == null ? lastLogin != null : !LAST_LOGIN_EDEFAULT.equals(lastLogin);
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
		result.append(" (lastLogin: ");
		result.append(lastLogin);
		result.append(')');
		return result.toString();
	}

}