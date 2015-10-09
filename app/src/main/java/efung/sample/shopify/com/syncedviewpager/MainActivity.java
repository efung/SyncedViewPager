package efung.sample.shopify.com.syncedviewpager;

import android.support.annotation.DrawableRes;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Data cards[] = new Data[]{
            new Data("Coffee", R.drawable.ornament_coffee),
            new Data("Cranberry", R.drawable.ornament_cranberry),
            new Data("Cyan Rust", R.drawable.ornament_cyan_rust),
            new Data("Green Blob", R.drawable.ornament_green_blob),
            new Data("Striped Purple", R.drawable.ornament_stripe_purple),
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ViewPager pagerText = (ViewPager) findViewById(R.id.pager_text);
        final ViewPager pagerImages = (ViewPager) findViewById(R.id.pager_images);

        pagerText.setAdapter(new TextPagerAdapter());
        // The approach below is from https://stackoverflow.com/a/26513243/369480
        pagerText.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            private int mScrollState = ViewPager.SCROLL_STATE_IDLE;

            @Override
            public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {
                if (mScrollState == ViewPager.SCROLL_STATE_IDLE) {
                    return;
                }
                pagerImages.scrollTo(pagerText.getScrollX(), pagerImages.getScrollY());
            }

            @Override
            public void onPageSelected(final int position) {
            }

            @Override
            public void onPageScrollStateChanged(final int state) {
                mScrollState = state;
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    pagerImages.setCurrentItem(pagerText.getCurrentItem(), false);
                }
            }
        });
        pagerImages.setAdapter(new ImagePagerAdapter());
        pagerImages.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            private int mScrollState = ViewPager.SCROLL_STATE_IDLE;

            @Override
            public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {
                if (mScrollState == ViewPager.SCROLL_STATE_IDLE) {
                    return;
                }
                pagerText.scrollTo(pagerImages.getScrollX(), pagerText.getScrollY());
            }

            @Override
            public void onPageSelected(final int position) {
            }

            @Override
            public void onPageScrollStateChanged(final int state) {
                mScrollState = state;
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    pagerText.setCurrentItem(pagerImages.getCurrentItem(), false);
                }
            }
        });

        TabLayout pageIndicator = (TabLayout) findViewById(R.id.paging_indicator);
        pageIndicator.setupWithViewPager(pagerImages);
    }

    private class TextPagerAdapter extends ViewPagerAdapter {
        @Override
        public View getView(int position, ViewPager pager) {
            ViewGroup parent = (ViewGroup) getLayoutInflater().inflate(R.layout.view_text, null);
            TextView v = (TextView) parent.findViewById(R.id.view_text);
            v.setText(cards[position].title);
            return parent;
        }

        @Override
        public int getCount() {
            return cards.length;
        }
    }

    private class ImagePagerAdapter extends ViewPagerAdapter {
        @Override
        public View getView(int position, ViewPager pager) {
            ViewGroup parent = (ViewGroup) getLayoutInflater().inflate(R.layout.view_image, null);
            ImageView v = (ImageView) parent.findViewById(R.id.view_image);
            v.setImageResource(cards[position].drawableRes);
            return parent;
        }

        @Override
        public int getCount() {
            return cards.length;
        }
    }

    private static class Data {
        public String title;
        public @DrawableRes int drawableRes;

        public Data(String title, int drawableRes) {
            this.title = title;
            this.drawableRes = drawableRes;
        }
    }
}
