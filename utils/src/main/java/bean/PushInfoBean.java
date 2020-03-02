package bean;

public class PushInfoBean {
    /**
     * gpsStatus : false
     * messageTypeId : 1
     * deviceAlarmId : 675899349
     * imei : 866551032760683
     * deviceName : 760683-有线 南汇工程 全顺
     * alarmTypeBean : {"alarmTypeId":12,"name":"超速报警"}
     * alarmTime : 1582793180000
     * isRead : false
     * reminderTime : 60
     * address: "南七里"
     */

    private boolean gpsStatus;
    private int messageTypeId;
    private int deviceAlarmId;
    private String imei;
    private String deviceName;
    private AlarmTypeBeanBean alarmTypeBean;
    private long alarmTime;
    private boolean isRead;
    private int reminderTime;
    private String address;

    public boolean isGpsStatus() {
        return gpsStatus;
    }

    public void setGpsStatus(boolean gpsStatus) {
        this.gpsStatus = gpsStatus;
    }

    public int getMessageTypeId() {
        return messageTypeId;
    }

    public void setMessageTypeId(int messageTypeId) {
        this.messageTypeId = messageTypeId;
    }

    public int getDeviceAlarmId() {
        return deviceAlarmId;
    }

    public void setDeviceAlarmId(int deviceAlarmId) {
        this.deviceAlarmId = deviceAlarmId;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public AlarmTypeBeanBean getAlarmTypeBean() {
        return alarmTypeBean;
    }

    public void setAlarmTypeBean(AlarmTypeBeanBean alarmTypeBean) {
        this.alarmTypeBean = alarmTypeBean;
    }

    public long getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(long alarmTime) {
        this.alarmTime = alarmTime;
    }

    public boolean isIsRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

    public int getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(int reminderTime) {
        this.reminderTime = reminderTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public static class AlarmTypeBeanBean {
        private int alarmTypeId;
        private String name;

        public int getAlarmTypeId() {
            return alarmTypeId;
        }

        public void setAlarmTypeId(int alarmTypeId) {
            this.alarmTypeId = alarmTypeId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
