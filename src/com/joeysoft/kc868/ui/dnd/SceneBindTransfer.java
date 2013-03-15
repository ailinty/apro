package com.joeysoft.kc868.ui.dnd;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.eclipse.swt.dnd.ByteArrayTransfer;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.TransferData;

import com.joeysoft.kc868.db.bean.SceneBind;

public class SceneBindTransfer extends ByteArrayTransfer {

	private static final String MYTYPENAME = "name_for_my_type";

	private static final int MYTYPEID = registerType(MYTYPENAME);

	private static SceneBindTransfer _instance = new SceneBindTransfer();

	public static SceneBindTransfer getInstance() {
		return _instance;
	}

	public void javaToNative(Object object, TransferData transferData) {
		if (!checkSceneBind(object) || !isSupportedType(transferData)) {
			DND.error(DND.ERROR_INVALID_DATA);
		}
		SceneBind bind = (SceneBind) object;
		try {
			// write data to a byte array and then ask super to convert to
			// pMedium
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			DataOutputStream writeOut = new DataOutputStream(out);
			writeOut.writeInt(bind.getSceneBindId());
			writeOut.writeInt(bind.getSceneId());
			byte[] buffer = bind.getSourceTable().getBytes();
			writeOut.writeInt(buffer.length);
			writeOut.write(buffer);
			
			buffer = bind.getSourceId().getBytes();
			writeOut.writeInt(buffer.length);
			writeOut.write(buffer);
			
			buffer = bind.getSourceCmd().getBytes();
			writeOut.writeInt(buffer.length);
			writeOut.write(buffer);
			
			buffer = bind.getSourceText().getBytes();
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

			SceneBind bind = new SceneBind();
			try {
				ByteArrayInputStream in = new ByteArrayInputStream(buffer);
				DataInputStream readIn = new DataInputStream(in);
				while (readIn.available() > 0) {
					bind.setSceneBindId(readIn.readInt());
					bind.setSceneId(readIn.readInt());
					
					int size = readIn.readInt();
					byte[] name = new byte[size];
					readIn.read(name);
					bind.setSourceTable(new String(name));
					
					size = readIn.readInt();
					name = new byte[size];
					readIn.read(name);
					bind.setSourceId(new String(name));
					
					size = readIn.readInt();
					name = new byte[size];
					readIn.read(name);
					bind.setSourceCmd(new String(name));
					
					size = readIn.readInt();
					name = new byte[size];
					readIn.read(name);
					bind.setSourceText(new String(name));
				}
				readIn.close();
			} catch (IOException ex) {
				return null;
			}
			return bind;
		}

		return null;
	}

	protected String[] getTypeNames() {
		return new String[] { MYTYPENAME };
	}

	protected int[] getTypeIds() {
		return new int[] { MYTYPEID };
	}

	boolean checkSceneBind(Object object) {
		if (object == null || !(object instanceof SceneBind)) {
			return false;
		}
		return true;
	}

	protected boolean validate(Object object) {
		return checkSceneBind(object);
	}
}
