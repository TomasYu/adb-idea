package com.developerphil.adbidea.adb.command;

public class DoubleClearDataCommand extends CommandList {
    public DoubleClearDataCommand() {
        super(new ClearDataCommand(), new ClearSDcardDataCommand());
    }
}
