# digiteq-entry-test

1.	Please implement LayoutManager for RecyclerView, which:
      o	Populates items in a grid horizontally, like:
      Item1  Item2  Item3  Item4  Item5        |     Item11  Item12  Item13  Item14  Item15
      Item6  Item7  Item8  Item9  Item10      |     Item16  Item17 ...
      o	Number of rows and columns are configurable in the constructor (in the case above: 2 rows, 5 columns)
      o	supports reversed (right to left) layout
      o	supports RecyclerView item animations
      o	supports smooth scrolling for changing page index in code (see implementation of SmoothScroller)

2.	Please implement SnapHelper for RecyclerView and beforementioned LayoutManager, which:
      o	supports reversed (right to left) layout
      o	when attached to RecyclerView, forces the RecyclerView to always scroll whole pages
      o	one "whole" page is a specific number of items, which is calculated by multiplying rows * cols
      o	if rows = 2 and cols = 5, then the RecyclerView should always scroll 10 items and align the 10th+1 item at the starting edge of the screen
