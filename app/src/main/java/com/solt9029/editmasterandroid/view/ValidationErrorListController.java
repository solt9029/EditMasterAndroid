package com.solt9029.editmasterandroid.view;

import androidx.annotation.Nullable;
import com.airbnb.epoxy.TypedEpoxyController;
import com.solt9029.editmasterandroid.ValidationErrorItemBindingModel_;
import com.solt9029.editmasterandroid.entity.ValidationErrorBody;

import java.util.List;
import java.util.Map;

public class ValidationErrorListController extends TypedEpoxyController<ValidationErrorBody> {
    @Override
    public void buildModels(@Nullable ValidationErrorBody body) {
        if (body == null || body.getErrors() == null) {
            return;
        }

        long id = 0L;
        for (Map.Entry<String, List<String>> entry : body.getErrors().entrySet()) {
            for (String error : entry.getValue()) {
                new ValidationErrorItemBindingModel_().error(error).id(id++).addTo(this);
            }
        }
    }
}
