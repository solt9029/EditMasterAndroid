@EpoxyDataBindingLayouts(
        {R.layout.score_item, R.layout.progress_bar_item, R.layout.error_item, R.layout.validation_error_item})
@PackageModelViewConfig(rClass = R.class)
@PackageEpoxyConfig(
        requireAbstractModels = true,
        requireHashCode = true,
        implicitlyAddAutoModels = true
)
package com.solt9029.editmasterandroid;

import com.airbnb.epoxy.EpoxyDataBindingLayouts;
import com.airbnb.epoxy.PackageEpoxyConfig;
import com.airbnb.epoxy.PackageModelViewConfig;