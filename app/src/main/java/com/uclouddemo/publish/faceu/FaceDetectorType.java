package com.uclouddemo.publish.faceu;

/**
 * @author kevinhuang
 * @since 2016-11-28
 */
public enum FaceDetectorType {
    FACEPP,
    SENSETIME,
    ULSEE;

    public int asInt() {
        switch (this) {
            case FACEPP:
                return 1;
            case SENSETIME:
                return 2;
            case ULSEE:
                return 3;
        }

        return 1;
    }

    public static FaceDetectorType fromInt(int type) {
        switch (type) {
            case 1:
                return FACEPP;
            case 2:
                return SENSETIME;
            case 3:
                return ULSEE;
            default:
                return null;
        }
    }

    public static FaceDetectorType nextType(FaceDetectorType type) {
        int nextType = type.asInt() + 1;
        if (fromInt(nextType) == null) {
            return fromInt(1);
        } else {
            return fromInt(nextType);
        }
    }

}
