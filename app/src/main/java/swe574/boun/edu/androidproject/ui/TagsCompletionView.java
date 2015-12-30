package swe574.boun.edu.androidproject.ui;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tokenautocomplete.TokenCompleteTextView;

import swe574.boun.edu.androidproject.R;

public class TagsCompletionView extends TokenCompleteTextView<TagData> {
    public TagsCompletionView(Context context) {
        super(context);
    }

    public TagsCompletionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TagsCompletionView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected View getViewForObject(TagData object) {
        LayoutInflater l = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        LinearLayout view = (LinearLayout) l.inflate(R.layout.tag_token, (ViewGroup) TagsCompletionView.this.getParent(), false);
        ((TextView) view.findViewById(R.id.name)).setText(object.getmLabel());

        return view;
    }

    @Override
    protected TagData defaultObject(String completionText) {
        int index = completionText.indexOf(',');
        if (index == -1) {
            return new TagData(completionText, completionText, completionText);
        } else {
            return new TagData(completionText, completionText, completionText);
        }
    }

    public void configurate(TagsArrayAdapter adapter, TokenListener listener, final TokenTextWatcher watcher) {
        if (adapter != null)
            setAdapter(adapter);
        if (listener != null)
            setTokenListener(listener);
        if (watcher != null)
            addTextChangedListener(watcher);
        setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TagData data = (TagData) parent.getAdapter().getItem(position);
                if (watcher != null)
                    watcher.addTag(data);
            }
        });
        setTokenClickStyle(TokenClickStyle.Select);
    }
}
