package com.ddtpt.friendsindiablo;

import java.util.List;

/**
 * Created by e228596 on 3/9/2015.
 */
public class ItemStats {

    int requiredLevel, itemLevel;
    String flavorText, icon;
    List<RolledAttributes> mRolledAttributesList;
    List<RawAttributes> mRawAttributesList;

    public ItemStats() {

    }

    public int getRequiredLevel() {
        return requiredLevel;
    }

    public void setRequiredLevel(int requiredLevel) {
        this.requiredLevel = requiredLevel;
    }

    public int getItemLevel() {
        return itemLevel;
    }

    public void setItemLevel(int itemLevel) {
        this.itemLevel = itemLevel;
    }

    public String getFlavorText() {
        return flavorText;
    }

    public void setFlavorText(String flavorText) {
        this.flavorText = flavorText;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setRolledAttributes(List<RolledAttributes> rolledAttribs) {
        mRolledAttributesList = rolledAttribs;
    }

    public void setRawAttributes(List<RawAttributes> rawAttribs) {
        mRawAttributesList = rawAttribs;
    }

    public List<RolledAttributes> getRolledAttributesList() {
        return mRolledAttributesList;
    }

    public List<RawAttributes> getRawAttributesList() {
        return mRawAttributesList;
    }
}

class RawAttributes {
    String attrib;
    double min, max;

    public RawAttributes() {
        attrib = "";
        min = max = 0;
    }

    public String getAttrib() {
        return attrib;
    }

    public void setAttrib(String attrib) {
        this.attrib = attrib;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }
}

class RolledAttributes {
    boolean mPrimary;
    String mText, mColor, mAffixType;

    public RolledAttributes() {
        mPrimary = false;
        mText = "";
        mColor = "";
        mAffixType = "";
    }

    public boolean isPrimary() {
        return mPrimary;
    }

    public void setPrimary(boolean primary) {
        mPrimary = primary;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public String getColor() {
        return mColor;
    }

    public void setColor(String color) {
        mColor = color;
    }

    public String getAffixType() {
        return mAffixType;
    }

    public void setAffixType(String affixType) {
        mAffixType = affixType;
    }
}
