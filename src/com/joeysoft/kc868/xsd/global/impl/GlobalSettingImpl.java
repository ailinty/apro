/**
 * <copyright>
 * </copyright>
 *
 * $Id: GlobalSettingImpl.java,v 1.1 2012/08/21 07:17:45 joey Exp $
 */
package com.joeysoft.kc868.xsd.global.impl;

import com.joeysoft.kc868.xsd.global.GlobalPackage;
import com.joeysoft.kc868.xsd.global.GlobalSetting;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Setting</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.joeysoft.kc868.xsd.global.impl.GlobalSettingImpl#getLanguage <em>Language</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class GlobalSettingImpl extends EObjectImpl implements GlobalSetting {
	/**
	 * The default value of the '{@link #getLanguage() <em>Language</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLanguage()
	 * @generated
	 * @ordered
	 */
	protected static final String DB_VERSION_EDEFAULT = "000";

	/**
	 * The cached value of the '{@link #getLanguage() <em>Language</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLanguage()
	 * @generated
	 * @ordered
	 */
	protected String DBVersion = DB_VERSION_EDEFAULT;

	/**
	 * This is true if the Language attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean DBVersionESet = false;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GlobalSettingImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return GlobalPackage.eINSTANCE.getGlobalSetting();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDBVersion() {
		return DBVersion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDBVersion(String newDBVersion) {
		String oldDBVersion = DBVersion;
		DBVersion = newDBVersion == null ? DB_VERSION_EDEFAULT : newDBVersion;
		boolean oldLanguageESet = DBVersionESet;
		DBVersionESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GlobalPackage.GLOBAL_SETTING__DB_VERSION, oldDBVersion, DBVersion, !oldLanguageESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetDBVersion() {
		String oldDBVersion = DBVersion;
		boolean oldDBVersionESet = DBVersionESet;
		DBVersion = DB_VERSION_EDEFAULT;
		DBVersionESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, GlobalPackage.GLOBAL_SETTING__DB_VERSION, oldDBVersion, DB_VERSION_EDEFAULT, oldDBVersionESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetDBVersion() {
		return DBVersionESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(EStructuralFeature eFeature, boolean resolve) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case GlobalPackage.GLOBAL_SETTING__DB_VERSION:
				return getDBVersion();
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
			case GlobalPackage.GLOBAL_SETTING__DB_VERSION:
				setDBVersion((String)newValue);
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
			case GlobalPackage.GLOBAL_SETTING__DB_VERSION:
				unsetDBVersion();
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
			case GlobalPackage.GLOBAL_SETTING__DB_VERSION:
				return isSetDBVersion();
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
		result.append(" (DBVersion: ");
		if (DBVersionESet) result.append(DBVersion); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //GlobalSettingImpl
