package InzenjeringZnanja.dto.enums;

public enum ComputerSymptoms {
    cantConnectToWeb,
    audioCorruption,
    audioFreezes,
    noAudio,
    hardwareDoesNotWork,
    videoFreezes,
    pixelsOnMonitor,
    isLoud,
    isHot,
    blankScreen,
    systemCrash,
    monitorFlickers,
    blueScreen,
    pcBeeps,
    turnOff,
    systemReboot,
    cantBootSystem,
    missingFiles,
    smoke,
    cantTurnOn,
    slowPerformance,
    filesCorrupted,
    cannotInstallSoftware;

    public static String getNodeName(ComputerSymptoms symptom){
        switch (symptom){
            case cantConnectToWeb:
                return "Cant_connect_to_web";
            case audioCorruption:
                return "Audio_corruption";
            case audioFreezes:
                return "Audio_freezes";
            case noAudio:
                return "No_audio";
            case hardwareDoesNotWork:
                return "Hardware_does_not_work";
            case videoFreezes:
                return "Video_freezes";
            case pixelsOnMonitor:
                return "Pixels_on_monitor";
            case isLoud:
                return "Is_loud";
            case isHot:
                return "Is_hot";
            case blankScreen:
                return "Blank_screen";
            case systemCrash:
                return "System_crash";
            case monitorFlickers:
                return "Monitor_flickers";
            case blueScreen:
                return "Blue_screen_of_death";
            case pcBeeps:
                return "PC_beeps";
            case turnOff:
                return "Turn_off";
            case systemReboot:
                return "System_reboot";
            case cantBootSystem:
                return "Cant_boot_system";
            case missingFiles:
                return "Missing_files";
            case smoke:
                return "Smoke";
            case cantTurnOn:
                return "Cant_turn_on";
            case slowPerformance:
                return "Slow_performance";
            case filesCorrupted:
                return "Files_corrupted";
            case cannotInstallSoftware:
                return "Cannot_install_software";
            default:
                return "NO NODE FUND";
        }
    }
}
