package com.joeysoft.kc868.ui.dnd;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.eclipse.swt.dnd.ByteArrayTransfer;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.TransferData;

import com.joeysoft.kc868.db.bean.SceneAction;

public class SceneActionTransfer extends ByteArrayTransfer {

	private static final String MYTYPENAME = "name_for_my_type";

	private static final int MYTYPEID = registerType(MYTYPENAME);

	private static SceneActionTransfer _instance = new SceneActionTransfer();

	public static SceneActionTransfer getInstance() {
		return _instance;
	}

	public void javaToNative(Object object, TransferData transferData) {
		if (!checkSceneAction(object) || !isSupportedType(transferData)) {
			DND.error(DND.ERROR_INVALID_DATA);
		}
		SceneAction sceneAction = (SceneAction) object;
		try {
			// write data to a byte array and then ask super to convert to
			// pMedium
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			DataOutputStream writeOut = new DataOutputStream(out);
			writeOut.writeInt(sceneAction.getActionId());
			writeOut.writeInt(sceneAction.getSceneId());
			writeOut.writeInt(sceneAction.getFloor());
			writeOut.writeInt(sceneAction.getRoomId());
			writeOut.writeInt(sceneAction.getDeviceId());
			writeOut.writeInt(sceneAction.getDeviceKeyId());
			
			byte[] buffer = sceneAction.getSensorId().getBytes();
			writeOut.writeInt(buffer.length);
			writeOut.write(buffer);
			
			buffer = sceneAction.getSensorTable().getBytes();
			writeOut.writeInt(buffer.length);
			writeOut.write(buffer);
			
			buffer = sceneAction.getText().getBytes();
			writeOut.writeInt(buffer.length);
			writeOut.write(buffer);
			
			buffer = out.toByteArray();
			writeOut.close();
			super.javaToNative(buffer, transferData);
		} catch (IOException e) {
		}
	}

	public Object nativeToJava(TransferData transferData) {
		if (isSupportedType(transferData)) {
			byte[] buffer = (byte[]) super.nativeToJava(transferData);
			if (buffer == null)
				return null;

			SceneAction sceneAction = new SceneAction();
			try {
				ByteArrayInputStream in = new ByteArrayInputStream(buffer);
				DataInputStream readIn = new DataInputStream(in);
				while (readIn.available() > 0) {
					sceneAction.setActionId(readIn.readInt());
					sceneAction.setSceneId(readIn.readInt());
					sceneAction.setFloor(readIn.readInt());
					sceneAction.setRoomId(readIn.readInt());
					sceneAction.setDeviceId(readIn.readInt());
					sceneAction.setDeviceKeyId(readIn.readInt());
					
					int size = readIn.readInt();
					byte[] name = new byte[size];
					readIn.read(name);
					sceneAction.setSensorId(new String(name));
					
					size = readIn.readInt();
					name = new byte[size];
					readIn.read(name);
					sceneAction.setSensorTable(new String(name));
					
					size = readIn.readInt();
					name = new byte[size];
					readIn.read(name);
					sceneAction.setText(new String(name));
				}
				readIn.close();
			} catch (IOException ex) {
				return null;
			}
			return sceneAction;
		}

		return null;
	}

	protected String[] getTypeNames() {
		return new String[] { MYTYPENAME };
	}

	protected int[] getTypeIds() {
		return new int[] { MYTYPEID };
	}

	boolean checkSceneAction(Object object) {
		if (object == null || !(object instanceof SceneAction)) {
			return false;
		}
		return true;
	}

	protected boolean validate(Object object) {
		return checkSceneAction(object);
	}
}
