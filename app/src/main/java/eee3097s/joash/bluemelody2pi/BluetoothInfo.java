package eee3097s.joash.bluemelody2pi;

import java.util.UUID;

public final class BluetoothInfo {
    public final static int REQUEST_ENABLE_BT = 1;
    private final static String NAME = "raspberrypi";
    private final static UUID MY_UUID = UUID.fromString("7be1fcb3-5776-42fb-91fd-2ee7b5bbb86d");

    public static String getNAME() {
        return NAME;
    }

    public static UUID getMyUuid() {
        return MY_UUID;
    }
}
