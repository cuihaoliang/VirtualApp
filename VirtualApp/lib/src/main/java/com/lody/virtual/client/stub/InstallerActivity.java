package com.lody.virtual.client.stub;

import java.io.IOException;
import java.net.URLDecoder;

import com.lody.virtual.Common;
import com.lody.virtual.client.core.InstallStrategy;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.env.Constants;
import com.lody.virtual.helper.proto.InstallResult;
import com.lody.virtual.helper.utils.VLog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import dalvik.system.DexFile;

public class InstallerActivity extends Activity {
	public static String installScheme = "file://";
	public static String uninstallScheme = "package:";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		if (intent == null || intent.getAction() == null || intent.getDataString() == null) {
			finish();
			return;
		}
		String action = intent.getAction();
		String data = intent.getDataString();
		if (Constants.ACTION_INSTALL_PACKAGE.equals(action)) {
			try {
				final String apkPath = URLDecoder.decode(data.substring(installScheme.length()), "utf-8");
				Toast.makeText(this, "Installing " + apkPath, Toast.LENGTH_SHORT).show();
				installVirtualApp(apkPath);
			} catch (Throwable e) {
				e.printStackTrace();
			}
		} else if (Constants.ACTION_UNINSTALL_PACKAGE.equals(action)) {
			final String packageName = intent.getDataString().substring(uninstallScheme.length());
			Toast.makeText(this, "Uninstalling " + packageName, Toast.LENGTH_SHORT).show();
			unInstallVirtualApp(packageName);
		}

	}

	public void installVirtualApp(String path) throws Throwable {
		int flags = InstallStrategy.UPDATE_IF_EXIST | InstallStrategy.DEPEND_SYSTEM_IF_EXIST;

		Common.printCallStatck();
		VLog.e("installApp","installApp  +"+flags);
		final InstallResult result = VirtualCore.getCore().installApp(path, flags);




			/*final Handler handler = new Handler(Looper.getMainLooper());
			handler.post(new Runnable() {
				@Override
				public void run() {
					try {

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});*/
		VirtualCore.getCore().preOpt(result.packageName);
		Toast.makeText(InstallerActivity.this, "Install finish!", Toast.LENGTH_SHORT).show();
		finish();


	}

	public void unInstallVirtualApp(String packageName) {
		VirtualCore.getCore().uninstallApp(packageName);
		Toast.makeText(this, "Uninstall finish!", Toast.LENGTH_SHORT).show();
		finish();
	}
}
