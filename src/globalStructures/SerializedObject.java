package globalStructures;

import java.io.Serializable;

public class SerializedObject implements Serializable {
    String message = null;
    String tag = null;
    int idx;

    public SerializedObject(String message, String tag, int idx) {
        this.message = message;
        this.tag = tag;
        this.idx = idx;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }
}
