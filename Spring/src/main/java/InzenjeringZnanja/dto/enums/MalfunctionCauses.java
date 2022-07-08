package InzenjeringZnanja.dto.enums;

public enum MalfunctionCauses {
    audioDriverTampering,
    didntClean,
    computerDropped,
    overclocking,
    surfing,
    spilledLiquid,
    hasAntivirus;

    public static String getNodeName(MalfunctionCauses cause){
        switch(cause){
            case audioDriverTampering:
                return "Audio_drivers_tampering";
            case didntClean:
                return "Didnt_clean";
            case computerDropped:
                return "Computer_dropped";
            case overclocking:
                return "Overclocking";
            case surfing:
                return "Surfing";
            case spilledLiquid:
                return "Spilled_liquid";
            case hasAntivirus:
                return "Has_antivirus";
            default:
                return "NO NODE FOUND";
        }
    }
}
