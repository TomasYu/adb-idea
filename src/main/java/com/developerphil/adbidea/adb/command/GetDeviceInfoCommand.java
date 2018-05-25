package com.developerphil.adbidea.adb.command;

import android.text.TextUtils;
import com.android.ddmlib.AdbCommandRejectedException;
import com.android.ddmlib.IDevice;
import com.android.ddmlib.ShellCommandUnresponsiveException;
import com.android.ddmlib.TimeoutException;
import com.developerphil.adbidea.adb.command.receiver.GenericReceiver;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.BalloonBuilder;
import com.intellij.ui.awt.RelativePoint;
import com.intellij.ui.popup.BalloonPopupBuilderImpl;
import org.jetbrains.android.facet.AndroidFacet;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.developerphil.adbidea.ui.NotificationHelper.info;

public class GetDeviceInfoCommand implements Command {
    @Override
    public boolean run(Project project, IDevice device, AndroidFacet facet, String packageName) {
        StringBuffer sbuffer = new StringBuffer();
//        List<String> abis = device.getAbis();
//        if (abis != null && abis.size() >0 ) {
//            sbuffer.append("ABIS:\n");
//            for (String abi : abis) {
//                sbuffer.append(abi+"\n");
//            }
//        }

//        String avdName = device.getAvdName();
//        if (!TextUtils.isEmpty(avdName)) {
//            sbuffer.append("AVD Name:  "+ avdName + "\n");
//        }

//        sbuffer.append("Density: " + device.getDensity() + "\n");

        sbuffer.append("SerialNumber : "+ device.getSerialNumber() + "\n");
        sbuffer.append("brand : "+ device.getProperty("ro.product.brand")+ "\n");
//        sbuffer.append("model : "+ device.getProperty(IDevice.PROP_DEVICE_MODEL)+ "\n");
//        sbuffer.append("name : "+ device.getProperty("ro.product.name")+ "\n");
//        sbuffer.append("display height : "+ device.getProperty("vidc.dec.downscalar_height")+ "\n");
        sbuffer.append("sdk API level : "+ device.getProperty(IDevice.PROP_BUILD_API_LEVEL)+ "\n");
        sbuffer.append("sdk build version : "+ device.getProperty(IDevice.PROP_BUILD_VERSION)+ "\n");

        try {
            GenericReceiver iShellOutputReceiver = new GenericReceiver();
            device.executeShellCommand("dumpsys window displays |head -n 3", iShellOutputReceiver, 15L, TimeUnit.SECONDS);
            List<String> adbOutputLines1 = iShellOutputReceiver.getAdbOutputLines();
            sbuffer.append("display info :" + "\n");
            for (String s : adbOutputLines1) {
                sbuffer.append(s+ "\n");
            }

            device.executeShellCommand("dumpsys activity activities", iShellOutputReceiver, 15L, TimeUnit.SECONDS);
            List<String> adbOutputLines = iShellOutputReceiver.getAdbOutputLines();
            if (adbOutputLines != null) {
                sbuffer.append("dumpsys activity:" + "\n");
            }
            for (String adbOutputLine : adbOutputLines) {
                if (adbOutputLine.contains("#")) {
                    sbuffer.append(adbOutputLine + "\n");
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
//        sbuffer.append("display width : "+ device.getProperty("vidc.dec.downscalar_width")+ "\n");

//        JPanel jPanel = new JPanel();
//        jPanel.setToolTipText("phone info");
//        jPanel.setSize(500,800);
//        jPanel.setLocation(800,300);
//        JTextArea jTextArea = new JTextArea();
//        jTextArea.setText(sbuffer.toString());
//
//        jPanel.add(jTextArea);
//        JDialog jDialog = new JDialog();
//        jDialog.setContentPane(jPanel);
//        jDialog.show();

//        BalloonPopupBuilderImpl balloonPopupBuilder = new BalloonPopupBuilderImpl(null,project.getwi);
//        Balloon balloon = balloonPopupBuilder.createBalloon();
//        balloon.showInCenterOf(project.getComponent(""));
        info(sbuffer.toString());
        return false;
    }
}
