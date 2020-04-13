# Jigsaw
This library is a simple Jigsaw for Android.


![Watch the video](https://github.com/dariobrux/Jigsaw/blob/master/preview.gif)


Every tiles are randomly generated, and they match with the adjacent else.

## How to use via XML   
* `app:jv_pieces="16"` to set how many tiles you need.    
* `app:jv_spreadBoardCols="3"` to set how many columns the spread board has.  
* `app:jv_tileBorderColor="#ff0000"` to set the border color of the tile.  
* `app:jv_tileBorderWidth="4dp"` to set the border width of the tile.  
* `app:jv_tileSize="@dimen/tileSize"` to set te size of the tile.  
* `app:jv_borderBoard="@drawable/border"` to set a border around the board.  
\
This is a simple example with the entire widget in a ConstraintLayout.  
~~~~
<com.dariobrux.jigsaw.widgets.JigsawView  
    android:id="@+id/jigsawView"  
    android:layout_width="match_parent"  
    android:layout_height="match_parent"  
    android:clipChildren="false"  
    android:clipToPadding="false"  
    app:jv_borderBoard="@drawable/border"  
    app:jv_pieces="16"  
    app:jv_spreadBoardCols="3"  
    app:jv_tileBorderColor="#ff0000"  
    app:jv_tileBorderWidth="4dp"  
    app:jv_tileSize="@dimen/tileSize"  
    app:layout_constraintBottom_toBottomOf="parent"  
    app:layout_constraintStart_toStartOf="parent"  
    app:layout_constraintTop_toTopOf="parent" />
~~~~
        
