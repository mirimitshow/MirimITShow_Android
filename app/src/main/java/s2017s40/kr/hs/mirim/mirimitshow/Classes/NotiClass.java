package s2017s40.kr.hs.mirim.mirimitshow.Classes;

public class NotiClass {
    String NotifyGroup;
    String NotifyContent;
    boolean isNotice;

    public NotiClass(String notifyGroup, String notifyContent, boolean isNotice) {
        NotifyGroup = notifyGroup;
        NotifyContent = notifyContent;
        this.isNotice = isNotice;
    }

    public String getNotifyGroup() {
        return NotifyGroup;
    }

    public void setNotifyGroup(String notifyGroup) {
        NotifyGroup = notifyGroup;
    }

    public String getNotifyContent() {
        return NotifyContent;
    }

    public void setNotifyContent(String notifyContent) {
        NotifyContent = notifyContent;
    }

    public boolean isNotice() {
        return isNotice;
    }

    public void setNotice(boolean notice) {
        isNotice = notice;
    }
}
