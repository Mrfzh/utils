## TabLayoutManager用法

```java
        try {
            //配置
            TabLayoutConfig config = new TabLayoutConfig.Builder()
                    .setTabNum(NUM)
                    .setTextColor(R.color.menu_text_before, R.color.menu_text_after)
                    .setIcons(beforePressedIcons, pressedIcons)
                    .setTabContents(tabContents)
                    .setFragmentContainer(R.id.fv_main_container)
                    .setFragments(fragments)
                    .create();
            //显示TabLayout
            TabLayoutManager.getInstance(mTabLayout, this).init(config);
        } catch (Exception e) {
            e.printStackTrace();
        }
```
