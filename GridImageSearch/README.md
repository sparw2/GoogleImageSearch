This is Google image search application which allows a user to select search filters and paginate results infinitely

Time spent: 15 hours 

Completed user stories:
<ol>
<li> Required: User can enter a search query that will display a grid of image results from the Google Image API. </li>
<li> Required: User can click on "settings" which allows selection of advanced search options to filter results</li>
<li> Required: User can configure advanced search filters</li>
<li> Required: Subsequent searches will have any filters applied to the search results</li>
<li> Required: User can tap on any image in results to see the image full-screen</li>
<li> Required: User can scroll down “infinitely” to continue loading more image results (up to 8 pages) </li>
<li> Advanced: Robust error handling, check if internet is available, handle error cases, network failures</li>
<li> Advanced: Use the ActionBar SearchView or custom layout as the query box instead of an EditText 	</li>
<li> Advanced: User can share an image to their friends or email it to themselves</li>
<li> Advanced: Replace Filter Settings Activity with a lightweight modal overlay</li>
<li> Advanced: Improve the user interface and experiment with image assets and/or styling and coloring</li>
<li> Bonus: Use the StaggeredGridView to display improve the grid of image results</li>
</ol>

3rd party libraries used: picasso, async-http, staggeredgridview

Walkthrough of all user stories:

 ![Alt text](viewer.gif)