## TabLayoutManager用法

```java
        TabLayoutManager.newInstance(mTabLayout, this)  
                .setTabNum(NUM)
                .setTextColor(R.color.menu_text_before, R.color.menu_text_after)
                .setIcons(beforePressedIcons, pressedIcons)
                .setTabContents(tabContents)
                .build(R.id.fv_main_container, fragments);
```
