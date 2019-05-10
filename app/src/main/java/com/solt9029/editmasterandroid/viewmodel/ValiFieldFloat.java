package com.solt9029.editmasterandroid.viewmodel;

import android.util.Property;

import com.mlykotom.valifi.ValiFieldBase;
import com.mlykotom.valifi.fields.number.ValiFieldNumber;

import androidx.annotation.Nullable;

public class ValiFieldFloat extends ValiFieldNumber<Float> {
    public ValiFieldFloat() {
        super();
    }

    public ValiFieldFloat(@Nullable Float defaultValue) {
        super(defaultValue);
    }

    @Override
    protected Float parse(@Nullable String value) throws NumberFormatException {
        return Float.parseFloat(value);
    }
}