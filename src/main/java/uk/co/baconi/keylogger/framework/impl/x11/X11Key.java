package uk.co.baconi.keylogger.framework.impl.x11;

final class X11Key {
    private static final String CAPS_LOCK = "Caps_Lock";
    private static final String NAV = "Nav";
    private static final String META_L = "Meta_L";
    private static final String META_R = "Meta_R";
    private static final String ALT_R = "Alt_R";
    private static final String ALT_L = "Alt_L";
    private static final String CONTROL_R = "Control_R";
    private static final String CONTROL_L = "Control_L";
    private static final String SHIFT_R = "Shift_R";
    private static final String SHIFT_L = "Shift_L";

    private final int keyCode;
    private final String keyNameLowerCase;
    private final String keyNameUpperCase;
    private final boolean modifierKey;
    private final boolean shift;
    private final boolean ctrl;
    private final boolean alt;
    private final boolean meta;
    private final boolean navigation;
    private final boolean capsLock;

    X11Key(final int keyCode, final String keyNameLowerCase, final String keyNameUpperCase) {
        super();
        this.keyCode = keyCode;
        this.keyNameLowerCase = keyNameLowerCase;
        this.keyNameUpperCase = keyNameUpperCase;

        shift = (SHIFT_L.equals(keyNameLowerCase) || SHIFT_R.equals(keyNameLowerCase));
        ctrl = (CONTROL_L.equals(keyNameLowerCase) || CONTROL_R.equals(keyNameLowerCase));
        alt = (ALT_L.equals(keyNameLowerCase) || ALT_R.equals(keyNameLowerCase));
        meta = (META_L.equals(keyNameLowerCase) || META_R.equals(keyNameLowerCase));
        navigation = NAV.equals(keyNameLowerCase);
        capsLock = CAPS_LOCK.equals(keyNameLowerCase);

        modifierKey = shift || ctrl || alt || meta;
    }

    public int getKeyCode() {
        return keyCode;
    }

    public String getKeyNameLowerCase() {
        return keyNameLowerCase;
    }

    public String getKeyNameUpperCase() {
        return keyNameUpperCase;
    }

    public boolean isShift() {
        return shift;
    }

    public boolean isCtrl() {
        return ctrl;
    }

    public boolean isAlt() {
        return alt;
    }

    public boolean isMeta() {
        return meta;
    }

    public boolean isNavigation() {
        return navigation;
    }

    public boolean isCapsLock() {
        return capsLock;
    }

    public boolean isModifierKey() {
        return modifierKey;
    }
}