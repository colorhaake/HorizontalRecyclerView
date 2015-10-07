# HorizontalRecyclerView
Horizontal RecyclerView + wrap_content support + click event and effect support + fix column number + fragment

# App structure
## Horizontal RecyclerView
1. To use "android.support.v7.widget.RecyclerView" library
	+ Add "compile 'com.android.support:recyclerview-v7:+'" in "build.gradle" file
	+ Android Studio -> Build -> Make Project
2. Add RecyclerView layout in xml (Check activity_main.xml)
	```xml
    <android.support.v7.widget.RecyclerView
    	android:id="@+id/horizontal_recycler_view"
    	android:layout_width="match_parent"
    	android:layout_height="wrap_content" />
	```
3. Usage: (Check MainActivity.java)
	+ Conponents:
		+ Adapter (Check DateListAdapter.java)
		+ LayoutManager (Check WCLinearLayoutManager.java)
		+ RecyclerView
    ```java
    DateListAdapter adapter = new DateListAdapter(date_list);

        WCLinearLayoutManager layoutManager = new WCLinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.horizontal_recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, onDateClickListener));
    ```
## wrap_content support
LayoutManager (Check WCLinearLayoutManager.java)
## click event and effect support
+ OnItemClickListener registers in RecyclerView
	```
	recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, onDateClickListener));
    ```
+ OnItemClickListener
	```
	private RecyclerItemClickListener.OnItemClickListener onDateClickListener = new RecyclerItemClickListener.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            
        }
    };
    ```
## fix column number
+ Check DateListAdapter.java
+ In DateListAdapter.java -> class DateViewHolder  -> DateViewHolder constructor
```java
public static class DateViewHolder extends RecyclerView.ViewHolder {
        TextView date_textview;
        public DateViewHolder(final View parent, final View view) {
            super(view);

            date_textview = (TextView)view.findViewById(R.id.date_textview);

            if (parent.getWidth() <= 0) {
                parent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) date_textview.getLayoutParams();
                        params.width = (parent.getWidth() > 0) ? parent.getWidth()/ITEM_NUMBER_PER_PAGE : params.width;
                    }
                });
                return;
            }

            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)date_textview.getLayoutParams();
            params.width = parent.getWidth()/ITEM_NUMBER_PER_PAGE;
        }
    }
```

+ change view width dynamically by
```java
LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)date_textview.getLayoutParams();
params.width = parent.getWidth()/ITEM_NUMBER_PER_PAGE;
        }
```
+ Most of time, View is not rendered during view creation, so getWidth() will equal to 0
+ At this time, we register a listener for view rendered finsihed event
```java
parent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) date_textview.getLayoutParams();
                        params.width = (parent.getWidth() > 0) ? parent.getWidth()/ITEM_NUMBER_PER_PAGE : params.width;
                    }
                });
```
## fragment
+ Check FragmentView.java and MainActivity.java
+ Click item in RecyclerView will trigger to show corresponding fragment
```java
    private void onClickDate(Date date) {
        FragmentView fragmentView = (FragmentView)getFragmentManager().findFragmentById(R.id.fragment_view);
        if (fragmentView == null || fragmentView.getDate() == null || !fragmentView.getDate().equals(date)) {
            FragmentView cache = findFragmentCache(date);

            fragmentView = (cache != null)? cache : FragmentView.newInstance(date);
        }

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_view, fragmentView);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }
```
# Screenshots
Main page

![Alt text](images/HorizontalRecycleView_Main.png?raw=true "Main page")
