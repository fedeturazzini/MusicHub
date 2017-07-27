package digitalhouse.android.Util;

import android.annotation.TargetApi;
import android.os.Build;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.TransitionSet;

/**
 * Created by fedet on 11/7/2017.
 */

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class ImageTransition extends TransitionSet {
    public ImageTransition() {
        setOrdering(ORDERING_TOGETHER);
        addTransition(new ChangeBounds()).
                addTransition(new ChangeTransform()).
                addTransition(new ChangeImageTransform());
    }
}
