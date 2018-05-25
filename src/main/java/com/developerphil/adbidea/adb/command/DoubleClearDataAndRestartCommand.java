package com.developerphil.adbidea.adb.command;

public class DoubleClearDataAndRestartCommand extends CommandList {
    public DoubleClearDataAndRestartCommand(boolean withDebug) {
        super(new ClearDataCommand(), new ClearSDcardDataCommand(),new StartDefaultActivityCommand(withDebug));
    }
}
