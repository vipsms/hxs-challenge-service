package com.eeduspace.challenge.enumeration;


public class SourceEnum {

    private EquipmentType equipmentType;
    private EnterpriseType enterpriseType;
    ;
    public enum EquipmentType  {
        Web (0),
        Android(1),
        Ios(2);
        private final int value;

        public int getValue() {
            return value;
        }

        EquipmentType(int value) {
            this.value = value;
        }
    }

    public enum EnterpriseType{
        QQ (0),
        WeChat (1),
        CIRCLEOFFRIENDS(2);
        private final int value;

        public int getValue() {
            return value;
        }

        EnterpriseType(int value) {
            this.value = value;
        }
    }
    

    public EquipmentType getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(EquipmentType equipmentType) {
        this.equipmentType = equipmentType;
    }

    public EnterpriseType getEnterpriseType() {
        return enterpriseType;
    }

    public void setEnterpriseType(EnterpriseType enterpriseType) {
        this.enterpriseType = enterpriseType;
    }

}
