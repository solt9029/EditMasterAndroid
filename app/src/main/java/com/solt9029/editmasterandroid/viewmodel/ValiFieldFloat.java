package com.solt9029.editmasterandroid.viewmodel;

import androidx.annotation.Nullable;
import com.mlykotom.valifi.fields.number.ValiFieldNumber;

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