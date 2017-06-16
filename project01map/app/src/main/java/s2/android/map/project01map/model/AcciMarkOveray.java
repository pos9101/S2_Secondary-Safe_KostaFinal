package s2.android.map.project01map.model;

import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class AcciMarkOveray extends ItemizedOverlay<OverlayItem> {

    public AcciMarkOveray(Drawable defaultMarker) {
        super(defaultMarker);

        boundCenterBottom(defaultMarker);
        populate();
    }

    @Override
    protected OverlayItem createItem(int i) {
        OverlayItem item = null;

        return item;
    }

    @Override
    public int size() {
        return 2;
    }
}
