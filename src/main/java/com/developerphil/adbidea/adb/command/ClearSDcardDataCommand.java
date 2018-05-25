package com.developerphil.adbidea.adb.command;

import com.android.ddmlib.IDevice;
import com.developerphil.adbidea.adb.command.receiver.GenericReceiver;
import com.intellij.openapi.project.Project;
import org.jetbrains.android.facet.AndroidFacet;

import java.util.concurrent.TimeUnit;

import static com.developerphil.adbidea.adb.AdbUtil.isAppInstalled;
import static com.developerphil.adbidea.ui.NotificationHelper.error;
import static com.developerphil.adbidea.ui.NotificationHelper.info;

public class ClearSDcardDataCommand implements Command {

    @Override
    public boolean run(Project project, IDevice device, AndroidFacet facet, String packageName) {
        try {
            String cmdStr = "rm -rf /sdcard/";
            info("cur packageName: "+packageName);

            if (packageName.contains("com.chaozh.iReaderFree") || packageName.contains("com.chaozh.iReader")) {
                cmdStr = cmdStr.concat("iReader");
            }else if(packageName.contains("com.oppo.reader")){//oppo
                cmdStr = cmdStr.concat("Reader");
            }else if(packageName.contains("com.huawei.hwireader")){//华为
                cmdStr = cmdStr.concat("HWiReader");
            }else {//defult
                cmdStr = "are you kidding me ?";
            }

            info(cmdStr);
            device.executeShellCommand(cmdStr, new GenericReceiver(), 15L, TimeUnit.SECONDS);
            info(String.format("<b>%s</b> cleared sdcard：%s data for app on %s", packageName,cmdStr, device.getName()));
            return true;
        } catch (Exception e1) {
            error("Clear sdcard data failed... " + e1.getMessage());
        }

        return false;
    }

}
