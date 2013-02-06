package co.uk.baconi.keylogger.framework.impl.x11;

final class X11KeyResult {
    private final byte[] keyMap;

    X11KeyResult(final byte[] keyMap) {
        this.keyMap = keyMap;
    }

    public byte[] getKeyMap() {
        return keyMap;
    }
}