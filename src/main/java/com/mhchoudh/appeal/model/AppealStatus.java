package com.mhchoudh.appeal.model;

import javax.xml.bind.annotation.XmlEnumValue;


public enum AppealStatus {
    @XmlEnumValue(value="NotSent")
    NotSent,
    @XmlEnumValue(value="Reviewing")
    Reviewing, 
    @XmlEnumValue(value="Ready")
    Ready, 
    @XmlEnumValue(value="Sent")
    Sent
}
