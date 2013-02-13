package co.uk.baconi.keylogger.framework.impl.x11;

final class X11KeyResult {
    private final short[] keyMap;

    X11KeyResult(final byte[] keyMap) {
        this.keyMap = new short[keyMap.length];
        for (int i = 0; i < this.keyMap.length; i++) {
            final byte entry = keyMap[i];
            if (entry < 0) {
                this.keyMap[i] = (short) (entry + 256);
            } else {
                this.keyMap[i] = entry;
            }
        }
    }

    public short[] getKeyMap() {
        return keyMap;
    }
}