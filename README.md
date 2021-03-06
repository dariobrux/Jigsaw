# Jigsaw
This library is a simple Jigsaw for Android.


![Watch the video](https://github.com/dariobrux/Jigsaw/blob/master/preview.gif)

It consists of two boards. The first on the left is the *jigsaw board* and it contains the jigsaw to complete. The second, on the right, is the *spread container*, and it contains all the tiles in a random position.

> Every tile is randomly generated, and they match with all the adjacent tiles.

You can set as many tiles as you like. 

## Import using Gradle
Step 1. Add it in your root build.gradle at the end of repositories:
~~~~ gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
~~~~
Step 2. Add the dependency
~~~~ gradle
dependencies {
    implementation 'com.github.dariobrux:Jigsaw:1.0.0'
}
~~~~

## How to use via XML   
* `app:jv_pieces="9"` to set how many tiles you need.    
* `app:jv_spreadBoardCols="2"` to set how many columns the spread board has.  
* `app:jv_tileBorderColor="#ff0000"` to set the border color of the tile.  
* `app:jv_tileBorderWidth="4dp"` to set the border width of the tile.  
* `app:jv_tileSize="@dimen/tileSize"` to set te size of the tile.  
* `app:jv_borderBoard="@drawable/border"` to set a border around the board.  
\
This is a simple example with the widget built in a ConstraintLayout.  
~~~~ xml
<com.dariobrux.jigsaw.widgets.JigsawView  
    android:id="@+id/jigsawView"  
    android:layout_width="match_parent"  
    android:layout_height="match_parent"  
    android:clipChildren="false"  
    android:clipToPadding="false"  
    app:jv_borderBoard="@drawable/border"  
    app:jv_pieces="9"  
    app:jv_spreadBoardCols="2"  
    app:jv_tileBorderColor="#000000"  
    app:jv_tileBorderWidth="2dp"  
    app:jv_tileSize="@dimen/tileSize"  
    app:layout_constraintBottom_toBottomOf="parent"  
    app:layout_constraintStart_toStartOf="parent"  
    app:layout_constraintTop_toTopOf="parent" />
~~~~

## How to use via code (Kotlin)
~~~~ java
jigsawView.pieces = 9
jigsawView.spreadCols = 2
jigsawView.tileDecorator?.apply {
    this.borderColor = Color.BLACK
    this.borderWidth = 2f
}
jigsawView.tileSize = 150
jigsawView.bitmap = BitmapFactory.decodeResource(resources, R.drawable.genoa)
jigsawView.setOnJigsawListener(this)
~~~~

You can invoke the callbacks by `OnJigsawListenerAdapter` or the `OnJigsawListener`, via `jigsawView.setOnJigsawListener()`
* `onCompleted()` invoked at the end, when the jigsaw is completed having all the tiles at the right position.
* `onTileSelected(view: View)` invoked when tap on a tile.
* `onTileDeselected(view: View, isInBoard: Boolean)` invoked when the previously tapped tile is deselected. The parameter `isInBoard` check if the tile is deselected on the jigsaw board or on the spread container.
* `onTileGenerated(view: View)` invoked the first time, when the tiles are disposed on the spread container.
* `onTileSettled(view: View, isCorrectPosition: Boolean)` invoked when the tile is put on the jigsaw board, and `isCorrectPosition` tells if this tile is in the correct position or not.
* `onTileUnsettled(view: View)` invoked when the tile is removed from the board view and put on the spread container.

~~~~ kotlin
jigsawView.setOnJigsawListener(object : OnJigsawListenerAdapter() {

    override fun onCompleted() {}   
    override fun onTileDeselected(view: View, isInBoard: Boolean) {}
    override fun onTileGenerated(view: View) {}
    override fun onTileSelected(view: View) {}
    override fun onTileSettled(view: View, isCorrectPosition: Boolean) {}
    override fun onTileUnsettled(view: View) {}
})
~~~~
## Don't forget to...
Put in the manifest `android:hardwareAccelerated="false"`.

## Limitations (but I'm working on them)
At the current version of the library:
* Drag and drop is not available.  
* The spread container is always at the right of the board view.
* Tile customization only for the border.
* The tile sides are the same size.

**I appreciate every kind of pull requests to improve this library.**

## License
~~~~
Copyright 2020 Dario Bruzzese

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
~~~~
