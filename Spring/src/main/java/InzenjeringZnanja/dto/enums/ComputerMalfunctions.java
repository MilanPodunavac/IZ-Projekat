package InzenjeringZnanja.dto.enums;

public enum ComputerMalfunctions {
    modemDamaged,
    audioDriverNotWorking,
    cableDamaged,
    portDamaged,
    coolingSystemDamaged,
    coolingSystemDisconnected,
    processorDamaged,
    graphicsCardDamaged,
    hardDriveDisconnected,
    ramDamaged,
    dust,
    powerSupplyDisconnected,
    powerSupplyDamaged,
    hardDriveDamaged,
    malware,
    antivirusBlocking;

    public static String getNodeName(ComputerMalfunctions malfunction){
        switch(malfunction){
            case modemDamaged:
                return "Modem_damaged";
            case audioDriverNotWorking:
                return "Audio_drivers_not_working";
            case cableDamaged:
                return "Cable_damaged";
            case portDamaged:
                return "Port_damaged";
            case coolingSystemDamaged:
                return "Cooling_system_damaged";
            case coolingSystemDisconnected:
                return "Cooling_system_disconnected";
            case processorDamaged:
                return "Processor_damaged";
            case graphicsCardDamaged:
                return "Graphics_card_damaged";
            case hardDriveDisconnected:
                return "Hard_drive_disconnected";
            case ramDamaged:
                return "RAM_damaged";
            case dust:
                return "Dust";
            case powerSupplyDisconnected:
                return "Power_supply_disconnected";
            case powerSupplyDamaged:
                return "Power_supply_damaged";
            case hardDriveDamaged:
                return "Hard_drive_damaged";
            case malware:
                return "Malware";
            case antivirusBlocking:
                return "Antivirus_blocking";
            default:
                return "NO NODE FOUND";
        }
    }
    public static String toString(ComputerMalfunctions malfunction){
        switch(malfunction){
            case modemDamaged:
                return "Modem is damaged";
            case audioDriverNotWorking:
                return "Audio drivers are not working";
            case cableDamaged:
                return "Cable is damaged";
            case portDamaged:
                return "Port is damaged";
            case coolingSystemDamaged:
                return "Cooling system is damaged";
            case coolingSystemDisconnected:
                return "Cooling system is disconnected";
            case processorDamaged:
                return "Processor is damaged";
            case graphicsCardDamaged:
                return "Graphics card is damaged";
            case hardDriveDisconnected:
                return "Hard drive is disconnected";
            case ramDamaged:
                return "Ram is damaged";
            case dust:
                return "Computer is dusty";
            case powerSupplyDisconnected:
                return "Power supply is disconnected";
            case powerSupplyDamaged:
                return "Power supply is damaged";
            case hardDriveDamaged:
                return "Hard drive is damaged";
            case malware:
                return "Pc is infected with malware";
            case antivirusBlocking:
                return "Antivirus is blocking your software";
            default:
                return "";
        }
    }
}